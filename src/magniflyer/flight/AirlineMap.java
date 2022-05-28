package magniflyer.flight;

import java.util.*;

public class AirlineMap {

    private final short COST_PER_MILE;
    private final Map<String, HashMap<String, Integer>> FLIGHT_MAP;
    private final Map<Integer, FlightData> FLIGHT_DATABASE;
    private final String NAME;
    private final String PREFIX;

    public AirlineMap(short costPerMile, String name, String prefix) {
        COST_PER_MILE = costPerMile;
        FLIGHT_MAP = new HashMap<>();
        FLIGHT_DATABASE = new HashMap<>();
        NAME = name;
        PREFIX = prefix;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPrefix() {
        return PREFIX;
    }

    private boolean verify(String dest) {
        if (dest == null) {
            throw new IllegalArgumentException();
        }
        return !dest.isEmpty() || FLIGHT_MAP.containsKey(dest);
    }

    private boolean verify(int flight_no) {
        return !(flight_no / 1000 < 0) || FLIGHT_DATABASE.containsKey(flight_no);
    }


    public boolean addDestination(String dest) {
        if (!verify(dest)) {
            FLIGHT_MAP.put(dest, new HashMap<>());
            return true;
        }
        return false;
    }

    public boolean removeDestination(String dest) {
        if (verify(dest)) {
            for (String s : FLIGHT_MAP.keySet()) {
                removeRoute(s, dest);
            }
            FLIGHT_MAP.remove(dest);
            return true;
        }
        return false;
    }

    public boolean checkDestination(String dest) {
        return FLIGHT_MAP.containsKey(dest);
    }

    public Collection<String> getDestinations() {
        return new TreeSet<>(FLIGHT_MAP.keySet());
    }

    public boolean addRoute(String origin, String dest, short distance, int flight_no) {
        if (distance > 0 && !verify(flight_no)) {
            if (verify(origin)) {
                addDestination(origin);
            }
            if (verify(dest)) {
                addDestination(dest);
            }
            FlightData fd = new FlightData(origin, dest, distance, (short) (distance * COST_PER_MILE),
                    flight_no, null);
            FLIGHT_MAP.get(origin).put(dest, flight_no);
            FLIGHT_DATABASE.put(flight_no, fd);
            return true;
        }
        return false;
    }

    public boolean removeRoute(String origin, String dest) {
        if (verify(origin) && verify(dest)) {
            int flightNum = getFlightNum(origin, dest);
            if (flightNum != -1) {
                FLIGHT_MAP.get(origin).remove(dest);
                FLIGHT_DATABASE.remove(flightNum);
                return true;
            }
        }
        return false;
    }

    public FlightData getRoute(String origin, String dest) {
        if (verify(origin) && verify(dest)) {
            return FLIGHT_DATABASE.getOrDefault(getFlightNum(origin, dest), null);
        }
        return null;
    }

    public int getFlightNum(String origin, String dest) {
        if (verify(origin) && verify(dest)) {
            return FLIGHT_MAP.get(origin).getOrDefault(dest, -1);
        }
        return -1;
    }

    public FlightData getFlightData(int flightNum) {
        return FLIGHT_DATABASE.get(flightNum);
    }

    // DFS
    public ArrayList<FlightData> getFlights(String origin, String dest) {
        ArrayList<String> isVisited = new ArrayList<>();
        ArrayList<String> pathList = new ArrayList<>();
        ArrayList<FlightData> flights = new ArrayList<>();

        pathList.add(origin);

        getFlights(origin, dest, isVisited, pathList, flights);

        return flights;
    }

    private void getFlights(String origin, String dest, ArrayList<String> vis, ArrayList<String> pathList,
                            ArrayList<FlightData> flights) {

        if (origin.equals(dest)) {
            FlightData fd;

            if (pathList.size() > 2) {
                ArrayList<Integer> transits = new ArrayList<>();
                int flightNum = -1;
                short distance = 0;
                short price = 0;

                for (int i = 1; i < pathList.size(); i++) {
                    int tempNum = FLIGHT_MAP.get(pathList.get(i - 1)).get(pathList.get(i));
                    flightNum = i == 1 ? tempNum : flightNum;
                    transits.add(tempNum);
                    fd = getFlightData(flightNum);
                    distance += fd.getDistance();
                    price += fd.getPrice();
                }
                fd = new FlightData(pathList.get(0), dest, distance, price, flightNum, transits);
            } else {
                fd = getFlightData(getFlightNum(pathList.get(0), dest));
            }
            flights.add(fd);
            return;
        }

        vis.add(origin);
        for (String s : FLIGHT_MAP.get(origin).keySet()) {
            if (!vis.contains(s)) {
                pathList.add(s);
                getFlights(origin, dest, vis, pathList, flights);
                pathList.remove(s);
            }
        }
        vis.remove(origin);
    }

}
