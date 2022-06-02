package ir.sahabino.data_collector.conf;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CollectorConfig {
    private static CollectorConfig config;
    @Getter
    private String markets;
    public static CollectorConfig load(){
        if (config != null)
            return config;
        config = new CollectorConfig();
        InputStream inputStream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("collector.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            config.markets = properties.getProperty("markets");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

}
