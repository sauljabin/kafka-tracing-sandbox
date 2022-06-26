package kafka.sandbox;

import kafka.sandbox.avro.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class App {

    public static final String TOPIC_FROM = "kafka-clients.suppliers";

    public static void main(String[] args) throws IOException, InterruptedException {
        Properties consumerProps = getProperties("consumer.properties");

        KafkaConsumer<String, Supplier> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singleton(TOPIC_FROM));

        // attach shutdown handler to catch control-c and creating a latch
        CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("consumer-shutdown-hook") {
            @Override
            public void run() {
                consumer.wakeup();
                latch.countDown();
            }
        });

        // infinite loop
        Thread infiniteLoop = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, Supplier> records = consumer.poll(Duration.ofMillis(500));
                    for (ConsumerRecord<String, Supplier> record : records) {
                        log.info("Consumed message: topic = {}, partition = {}, offset = {}, key = {}, value = {}",
                                record.topic(),
                                record.partition(),
                                record.offset(),
                                record.key(),
                                record.value()
                        );
                    }
                    consumer.commitSync();
                }
            } catch (WakeupException e) {
                log.info("Shutdown gracefully");
            } finally {
                consumer.close();
            }
        }, "consumer-thread");

        infiniteLoop.start();
        latch.await();
    }

    private static Properties getProperties(String fileName) throws IOException {
        Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream(fileName));
        return props;
    }

}
