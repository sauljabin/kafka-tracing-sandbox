package kafka.sandbox;

import com.github.javafaker.Faker;
import kafka.sandbox.avro.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

@Slf4j
public class App {

    public static final String TOPIC_TO = "kafka-clients.suppliers";
    private static final Faker faker = new Faker();

    public static void main(String[] args) throws IOException {
        int messages = 10;

        if (args.length > 0) {
            try {
                messages = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                log.error("Argument {} must be an integer.", args[0]);
                System.exit(1);
            }
        }

        Properties producerProps = getProperties("producer.properties");

        KafkaProducer<String, Supplier> producer = new KafkaProducer<>(producerProps);

        for (int i = 0; i < messages; i++) {
            Supplier supplier = createNewCustomer();
            ProducerRecord<String, Supplier> record = new ProducerRecord<>(TOPIC_TO, supplier.getId(), supplier);
            producer.send(record, (metadata, exception) -> log.info("Producing message: {}", supplier));
        }

        producer.flush();
        producer.close();
    }

    private static Properties getProperties(String fileName) throws IOException {
        Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream(fileName));
        return props;
    }

    private static Supplier createNewCustomer() {
        return Supplier.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(faker.name().fullName())
                .setAddress(faker.address().streetAddress())
                .setCountry(faker.country().name())
                .build();
    }

}
