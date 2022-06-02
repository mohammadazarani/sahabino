package ir.sahabino.data_collector.collector;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import ir.sahabino.data_collector.conf.CollectorConfigValues;
import ir.sahabino.data_collector.entity.Candle;

import java.util.List;
import java.util.Properties;

public class Collector {
    private final Properties properties;


    public Collector(Properties properties) {
        this.properties = properties;
    }

    public List<Candle> collect() {
        BinanceApiRestClient client = BinanceApiClientFactory.newInstance().newRestClient();
        List<Candlestick> candlestickBars = client.
                getCandlestickBars(properties.getProperty(CollectorConfigValues.BINACE_MARKETS), CandlestickInterval.ONE_MINUTE);

//        candlestickBars.forEach(System.out::println);
        for (Candlestick candlestickBar : candlestickBars) {
            System.out.println(candlestickBar);
            kafkaCandleProducer.produce(
                    Candle.build(candlestickBar.getOpen(),
                            candlestickBar.getHigh(),
                            candlestickBar.getLow(),
                            candlestickBar.getClose())
            );
        }
    }
}

