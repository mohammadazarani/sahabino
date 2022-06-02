package arani.abdollahzade.mohammad.backend.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Candle {
    private Long id;

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

}
