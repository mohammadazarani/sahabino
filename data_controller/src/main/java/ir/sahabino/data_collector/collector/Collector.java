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
import java.util.concurrent.TimeUnit;

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


    //This Method Converted Collect and produce to decrease Candle object creation;
    public void collectAndProduce() {
        Long serverTime = client.getServerTime();
        int request_period = Integer.parseInt(properties.getProperty(CollectorConfigValues.REQUEST_PERIOD));
        int limit = request_period / (60 * 1000);
        if (limit == 0) {
            throw new PeriodException();
        }

        String[] markets = properties.getProperty(CollectorConfigValues.BINACE_MARKETS).
                trim().
                split("\\s*,\\s*");
        for (String market : markets) {

            candlestickBars = client.
                    getCandlestickBars(market, CandlestickInterval.ONE_MINUTE,
                            (int) limit, serverTime - request_period, serverTime);

            for (Candlestick candlestickBar : candlestickBars) {
                kafkaCandleProducer.produce(market,
                        Candle.build(candlestickBar.getOpen(),
                                candlestickBar.getHigh(),
                                candlestickBar.getLow(),
                                candlestickBar.getClose(),
                                candlestickBar.getOpenTime(),
                                candlestickBar.getCloseTime())
                );
            }
        }
    }

    public void waitOn() {
        long request_period = Long.parseLong(properties.getProperty(CollectorConfigValues.REQUEST_PERIOD));
//        Long limit = request_period;
        try {
//            TimeUnit.SECONDS.sleep();
            Thread.sleep(request_period);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

