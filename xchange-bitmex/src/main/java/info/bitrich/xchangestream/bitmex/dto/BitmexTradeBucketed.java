package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.math.BigDecimal;

public class BitmexTradeBucketed extends BitmexMarketDataEvent {
    private final String timestamp;
    private final String symbol;
    private final BigDecimal open;
    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal close;
    private final BigDecimal trades;
    private final BigDecimal volume;

    public BitmexTradeBucketed(@JsonProperty("timestamp") String timestamp,
                        @JsonProperty("symbol") String symbol,
                        @JsonProperty("open") BigDecimal open,
                        @JsonProperty("high") BigDecimal high,
                        @JsonProperty("low") BigDecimal low,
                        @JsonProperty("close") BigDecimal close,
                        @JsonProperty("trades") BigDecimal trades,
                        @JsonProperty("volume") BigDecimal volume) {
        super(symbol, timestamp);
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.trades = trades;
        this.volume = volume;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getTrades() {
        return trades;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public Ticker toTicker() {
        return new Ticker.Builder()
                .open(open).high(high).low(low).last(close).volume(volume).quoteVolume(trades)
                .timestamp(getDate()).currencyPair(getCurrencyPair())
                .build();
    }
}