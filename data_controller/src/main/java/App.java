import ir.sahabino.data_collector.collector.Collector;
import ir.sahabino.data_collector.conf.CollectorConfig;
import ir.sahabino.data_collector.factory.BinanceCollectorFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class App {
    public static void main(String[] args) {

        CollectorConfig config = CollectorConfig.load();
        Collector binanceCollector = BinanceCollectorFactory.createBinanceCollector(config);
        while (true) {
            binanceCollector.collectAndProduce();
            binanceCollector.waitOn();
        }


    }
}
