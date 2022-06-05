package ir.sahabino.data_collector.conf;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CollectorConfig {
    private static CollectorConfig config;
    @Getter
    private String markets;
    @Getter
    private String request_period;

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
            config.request_period = properties.getProperty("request_period");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

}
