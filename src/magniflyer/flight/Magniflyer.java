package magniflyer.flight;

import java.util.HashMap;
import java.util.Map;

public class Magniflyer {

    private final Map<String, AirlineMap> AIRLINES;

    public Magniflyer() {
        AIRLINES = new HashMap<>();
    }

    public boolean addAirline(String name, String prefix, short costPerMile) {
        if (!AIRLINES.containsKey(prefix)) {
            AirlineMap am = new AirlineMap(costPerMile, name, prefix);
            AIRLINES.put(prefix, am);
            return true;
        }
        return false;
    }

    public boolean removeAirline(String prefix) {
        return AIRLINES.remove(prefix) != null;
    }

//    public boolean addRoute(String airlinePrefix, String origin, String dest, short distance) {
//
//    }
}
