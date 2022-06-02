package ir.sahabino.rules_evaluator.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table
public class Candle {

    @JsonProperty("open_time")
    private Long openTime;
    @JsonProperty("open")
    private String open;
    @JsonProperty("high")
    private String high;
    @JsonProperty("low")
    private String low;
    @JsonProperty("close")
    private String close;
    @JsonProperty("volume")
    private String volume;
    @JsonProperty("close_time")
    private Long closeTime;
    @JsonProperty("quote_asset_volume")
    private String quoteAssetVolume;
    @JsonProperty("number_of_trades")
    private Long numberOfTrades;
    @JsonProperty("taker_buy_base_asset_volume")
    private String takerBuyBaseAssetVolume;
    @JsonProperty("taker_buy_quote_asset_volume")
    private String takerBuyQuoteAssetVolume;

    private Candle(String open, String high, String low, String close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Candle() {
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

