package ir.sahabino.data_collector.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@Setter
@Getter
public class Candle implements Serializer {

    private Long openTime;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    private Long closeTime;
    private String quoteAssetVolume;
    private Long numberOfTrades;
    private String takerBuyBaseAssetVolume;
    private String takerBuyQuoteAssetVolume;

    private Candle(String open, String high, String low, String close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Candle(){}

    public static Candle build(String open, String high, String low, String close) {
        return new Candle(open, high, low, close);
    }

    public Candle openTime(Long openTime) {
        this.openTime = openTime;
        return this;
    }

    public Candle volume(String volume) {
        this.volume = volume;
        return this;
    }

    public Candle closeTime(Long closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public Candle quoteAssetVolume(String quoteAssetVolume) {
        this.quoteAssetVolume = quoteAssetVolume;
        return this;
    }

    public Candle numberOfTrades(Long numberOfTrades) {
        this.numberOfTrades = numberOfTrades;
        return this;
    }

    public Candle takerBuyBaseAssetVolume(String takerBuyBaseAssetVolume) {
        this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
        return this;
    }

    public Candle takerBuyQuoteAssetVolume(String takerBuyQuoteAssetVolume) {
        this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
        return this;
    }

    @Override
    public byte[] serialize(String s, Object o) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);
        ) {
            oos.writeObject(o);
            return baos.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }
}

