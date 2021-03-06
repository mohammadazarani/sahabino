package ir.sahabino.data_collector.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
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

    private Date data;

    private Candle(String open, String high, String low, String close, Long openTime, Long closeTime) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.closeTime = closeTime;
        this.openTime = openTime;
    }

    public Candle(){}

    public static Candle build(String open, String high, String low, String close, Long openTime, Long closeTime) {
        return new Candle(open, high, low, close, openTime, closeTime);
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

