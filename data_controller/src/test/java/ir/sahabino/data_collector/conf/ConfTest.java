package ir.sahabino.data_collector.conf;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfTest {

    @Test
    public void load() {
        KafkaConf config = KafkaConf.load();
        assertEquals(config.getKafkaBrokers(), "localhost:9092");
        assertEquals(config.getKafkaOutputTopic(), "crypto");
    }
}