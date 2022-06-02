package ir.sahabino.data_collector.collector;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import ir.sahabino.data_collector.conf.CollectorConfigValues;
import ir.sahabino.data_collector.conf.KafkaConf;
import ir.sahabino.data_collector.dao.KafkaCandleProducer;
import ir.sahabino.data_collector.entity.Candle;
import ir.sahabino.data_collector.factory.KafkaProducerFactory;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Collector {
    private final Properties properties;

    private final BinanceApiRestClient client;
    private List<Candlestick> candlestickBars;
    private final KafkaCandleProducer kafkaCandleProducer;

    public Collector(Properties properties) {
        client = BinanceApiClientFactory.newInstance().newRestClient();
        candlestickBars = new ArrayList<>();
        KafkaConf config = KafkaConf.load();
        KafkaProducer<String, Candle> kafkaProducer = KafkaProducerFactory.createKafkaProducer(config);
        kafkaCandleProducer = KafkaCandleProducer.of(config, kafkaProducer);
        this.properties = properties;
    }


    public List<Candle> collect() {
        candlestickBars = client.
                getCandlestickBars(properties.getProperty(CollectorConfigValues.BINACE_MARKETS), CandlestickInterval.ONE_MINUTE);


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

