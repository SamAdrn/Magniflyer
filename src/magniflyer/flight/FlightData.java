package magniflyer.flight;

import java.util.ArrayList;
import java.util.Collection;

public class FlightData {

    private final String ORIGIN;
    private final String DESTINATION;
    private final short DISTANCE;
    private final short PRICE;
    private final int FLIGHT_NUMBER;
    private final ArrayList<Integer> TRANSITS;
    private final int NUM_OF_FLIGHTS;

    public FlightData(String origin, String dest, short dist, short price, int flight_no, Collection<Integer> transits) {
        ORIGIN = origin;
        DESTINATION = dest;
        DISTANCE = dist;
        PRICE = price;
        FLIGHT_NUMBER = flight_no;
        TRANSITS = transits != null ? new ArrayList<>(transits) : null;
        NUM_OF_FLIGHTS = transits != null ? 1 + transits.size() : 1;
    }

    public String getOrigin() {
        return ORIGIN;
    }

    public String getDestination() {
        return DESTINATION;
    }

    public short getDistance() {
        return DISTANCE;
    }

    public short getPrice() {
        return PRICE;
    }

    public int getFlightNumber() {
        return FLIGHT_NUMBER;
    }

    public ArrayList<Integer> getTransits() {
        return TRANSITS;
    }

    public int getNumOfFlights() {
        return NUM_OF_FLIGHTS;
    }
}
