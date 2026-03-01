import java.util.*;

public class AnalyticsDashboard {

    private HashMap<String, Integer> pageViews = new HashMap<>();
    private HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    private HashMap<String, Integer> trafficSources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    public void showDashboard() {

        System.out.println("\n--- Top Pages ---");

        List<Map.Entry<String, Integer>> list = new ArrayList<>(pageViews.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());

        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(url + " → " + views + " views (" + unique + " unique)");

            count++;
            if (count == 10) break;
        }

        System.out.println("\n--- Traffic Sources ---");

        int total = 0;
        for (int val : trafficSources.values()) {
            total += val;
        }

        for (String src : trafficSources.keySet()) {
            int countSrc = trafficSources.get(src);
            double percent = (countSrc * 100.0) / total;

            System.out.println(src + ": " + percent + "%");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AnalyticsDashboard ad = new AnalyticsDashboard();

        while (true) {
            System.out.println("\n1. Add Page View");
            System.out.println("2. Show Dashboard");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter URL: ");
                    String url = sc.next();

                    System.out.print("Enter User ID: ");
                    String user = sc.next();

                    System.out.print("Enter Source (google/facebook/direct): ");
                    String source = sc.next();

                    ad.processEvent(url, user, source);
                    break;

                case 2:
                    ad.showDashboard();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}