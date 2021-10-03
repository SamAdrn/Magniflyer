package magniflyer.flight;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FlightPath {

    private List<String> destinations;

    private List<BigDecimal> prices;

    public FlightPath() {
        this.destinations = new ArrayList<>();
        this.prices = new ArrayList<>();
    }

    public void addDestinations(List<String> destinationList, List<BigDecimal> priceList) {
        for (int i = 0; i < destinationList.size(); i++) {
            if (!destinations.contains(destinationList.get(i))) {
                destinations.add(destinationList.get(i));
                if (i != 0) {
                    prices.add(priceList.get(i - 1));
                }
            }
        }
    }

    public boolean contains(String destination) {
        return destinations.contains(destination);
    }

}
