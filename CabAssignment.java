import java.util.*;
import java.util.concurrent.TimeUnit;

class Location {
    double latitude;
    double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double distanceTo(Location other) {
        return Math.sqrt(Math.pow(this.latitude - other.latitude, 2) +
                         Math.pow(this.longitude - other.longitude, 2));
    }
}

class User {
    int id;
    Location location;

    public User(int id, Location location) {
        this.id = id;
        this.location = location;
    }
}

class Taxi {
    int id;
    Location location;

    public Taxi(int id, Location location) {
        this.id = id;
        this.location = location;
    }
}

public class CabAssignment {

    public static void main(String[] args) throws InterruptedException {
        // Simulate 4 users and 6 taxis with random locations in New Delhi.
        List<User> users = new ArrayList<>();
        List<Taxi> taxis = new ArrayList<>();

        // Initialize random locations for demonstration
        for (int i = 1; i <= 4; i++) {
            users.add(new User(i, getRandomLocation()));
        }

        for (int i = 1; i <= 6; i++) {
            taxis.add(new Taxi(i, getRandomLocation()));
        }

        while (true) {
            // Assign taxis to users to minimize average waiting time
            Map<User, Taxi> assignments = assignTaxis(users, taxis);
            printAssignments(assignments);

            TimeUnit.SECONDS.sleep(5);

            // Update user locations (simulate moving users)
            for (User user : users) {
                user.location = getRandomLocation();
            }
        }
    }

    private static Map<User, Taxi> assignTaxis(List<User> users, List<Taxi> taxis) {
        Map<User, Taxi> assignments = new HashMap<>();
        List<Taxi> availableTaxis = new ArrayList<>(taxis);

        // Sort users by the minimum distance to any available taxi
        users.sort(Comparator.comparingDouble(user -> closestTaxiDistance(user, availableTaxis)));

        // Assign each user the optimal taxi to minimize the average waiting time
        for (User user : users) {
            Taxi closestTaxi = null;
            double minDistance = Double.MAX_VALUE;

            // Find the nearest available taxi for the user
            for (Taxi taxi : availableTaxis) {
                double distance = user.location.distanceTo(taxi.location);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestTaxi = taxi;
                }
            }

            if (closestTaxi != null) {
                assignments.put(user, closestTaxi);
                availableTaxis.remove(closestTaxi); // Remove assigned taxi from availability
            }
        }

        return assignments;
    }

    private static double closestTaxiDistance(User user, List<Taxi> taxis) {
        double minDistance = Double.MAX_VALUE;
        for (Taxi taxi : taxis) {
            double distance = user.location.distanceTo(taxi.location);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }

    private static Location getRandomLocation() {
        // Generate a random location within New Delhi coordinates
        double latitude = 28.50 + new Random().nextDouble() * (28.90 - 28.50);
        double longitude = 77.10 + new Random().nextDouble() * (77.30 - 77.10);
        return new Location(latitude, longitude);
    }

    private static void printAssignments(Map<User, Taxi> assignments) {
        System.out.println("Taxi Assignments:");
        for (Map.Entry<User, Taxi> entry : assignments.entrySet()) {
            User user = entry.getKey();
            Taxi taxi = entry.getValue();
            System.out.printf("User %d assigned to Taxi %d\n", user.id, taxi.id);
        }
        System.out.println();
    }
}
