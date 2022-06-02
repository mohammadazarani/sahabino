package ir.sahabino.data_collector.factory;

import ir.sahabino.data_collector.collector.Collector;
import ir.sahabino.data_collector.conf.CollectorConfig;
import ir.sahabino.data_collector.conf.CollectorConfigValues;

import java.util.Properties;

public class BinanceCollectorFactory {
    public static Collector createBinanceCollector(CollectorConfig conf){
        Properties properties = new Properties();
        properties.put(CollectorConfigValues.BINACE_MARKETS, conf.getMarkets());
        return new Collector(properties);
    }
}
