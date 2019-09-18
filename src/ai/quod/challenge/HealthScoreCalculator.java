package ai.quod.challenge;
import ai.quod.challenge.gharchive.Session;

public class HealthScoreCalculator {
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            throw new IllegalArgumentException(String.format("Expected 1 or 2 argument(s). Received %d.", args.length));
        }
        String startDateUTC = args[0];
        String endDateUTC = args.length > 1 ? args[1] : args[0];
        Session session = new Session();
        session.fetchArchiveData(startDateUTC, endDateUTC);
    }
}
