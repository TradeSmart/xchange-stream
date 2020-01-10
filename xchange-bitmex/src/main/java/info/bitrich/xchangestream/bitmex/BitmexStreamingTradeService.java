package info.bitrich.xchangestream.bitmex;


import com.fasterxml.jackson.core.JsonProcessingException;
import info.bitrich.xchangestream.bitmex.dto.BitmexExecution;
import info.bitrich.xchangestream.bitmex.dto.BitmexOrder;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * Created by Declan
 */
public class BitmexStreamingTradeService implements StreamingTradeService {

    private final BitmexStreamingService streamingService;

    public BitmexStreamingTradeService(BitmexStreamingService streamingService) {
        this.streamingService = streamingService;
    }

    public Observable<Order> getOrders(CurrencyPair currencyPair, Object... args) throws JsonProcessingException {
        streamingService.checkAuth();
        String channelName = "order";
        String instrument = currencyPair.base.toString() + currencyPair.counter.toString();
        return streamingService.subscribeBitmexChannel(channelName).flatMapIterable(s -> {
            BitmexOrder[] bitmexOrders = s.toBitmexOrders();
            return Arrays.stream(bitmexOrders)
                    .filter(bitmexOrder -> bitmexOrder.getSymbol().equals(instrument))
                    .filter(BitmexOrder::isNotWorkingIndicator)
                    .map(BitmexOrder::toOrder).collect(Collectors.toList());
        });
    }

    public Observable<BitmexExecution> getExecutions() throws JsonProcessingException {
        streamingService.checkAuth();
        String channelName = "execution";
        return streamingService.subscribeBitmexChannel(channelName).flatMapIterable(s -> {
            BitmexExecution[] bitmexOrders = s.toBitmexExecutions();
            return Arrays.asList(bitmexOrders);
        });
    }
}
