import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import ir.sahabino.data_collector.conf.Conf;
import ir.sahabino.data_collector.dao.KafkaCandleProducer;
import ir.sahabino.data_collector.entity.Candle;
import ir.sahabino.data_collector.factory.KafkaProducerFactory;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.List;

public class App {
    public static void main(String[] args) {

        Conf config = Conf.load();
        KafkaProducer<String, Candle> kafkaProducer = KafkaProducerFactory.createKafkaProducer(config);
        KafkaCandleProducer kafkaCandleProducer = KafkaCandleProducer.of(config, kafkaProducer);






//        https://api.binance.com/api/v3/klines?symbol=BNBBTC&interval=1m&startTime=1000000


//        List<ir.sahabino.data_collector.entity.Candle> candles = new ArrayList<>();
//        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
//        client.onCandlestickEvent("BNBBTC", CandlestickInterval.ONE_MINUTE, response -> {
//            if (response.getBarFinal()) {
//                ir.sahabino.data_collector.entity.Candle candle = new ir.sahabino.data_collector.entity.Candle();
//                candles.add(candle);
//                System.err.println("asdfddddddddddddddddddad");
//
//            }else{
//                System.err.println("asdfadfadf");
////                log.info("kline is not final open_time={}",response.getOpenTime());
//            }
//        });
//        System.out.println("asdfad");
//        System.out.println(candles.size());

        BinanceApiRestClient client = BinanceApiClientFactory.newInstance().newRestClient();
        List<Candlestick> candlestickBars = client.getCandlestickBars("BNBBTC", CandlestickInterval.ONE_MINUTE);
//        candlestickBars.forEach(System.out::println);
        for (Candlestick candlestickBar : candlestickBars) {
            System.out.println(candlestickBar);
            kafkaCandleProducer.produce(Candle.build("open", "high", "low", "close"));
        }

    }
}
