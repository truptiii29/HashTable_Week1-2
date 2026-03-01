import java.util.*;

public class UsernameSystem {

    private HashMap<String, Integer> userMap = new HashMap<>();
    private HashMap<String, Integer> attempts = new HashMap<>();
    private int userIdCounter = 1;

    public boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !userMap.containsKey(username);
    }

    public void registerUser(String username) {
        userMap.put(username, userIdCounter++);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String suggestion = username + i;
            if (!userMap.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        String modified = username.replace("_", ".");
        if (!userMap.containsKey(modified)) {
            suggestions.add(modified);
        }

        return suggestions;
    }

    public String getMostAttempted() {
        String maxUser = "";
        int maxCount = 0;

        for (String user : attempts.keySet()) {
            if (attempts.get(user) > maxCount) {
                maxCount = attempts.get(user);
                maxUser = user;
            }
        }

        return maxUser + " (" + maxCount + " attempts)";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UsernameSystem system = new UsernameSystem();

        while (true) {
            System.out.println("\n1. Check Username");
            System.out.println("2. Register Username");
            System.out.println("3. Suggest Alternatives");
            System.out.println("4. Most Attempted");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter username: ");
                    String check = sc.next();
                    if (system.checkAvailability(check)) {
                        System.out.println("Available");
                    } else {
                        System.out.println("Already taken");
                    }
                    break;

                case 2:
                    System.out.print("Enter username to register: ");
                    String reg = sc.next();
                    if (system.checkAvailability(reg)) {
                        system.registerUser(reg);
                        System.out.println("Registered successfully");
                    } else {
                        System.out.println("Username already taken");
                    }
                    break;

                case 3:
                    System.out.print("Enter username: ");
                    String sug = sc.next();
                    System.out.println("Suggestions: " + system.suggestAlternatives(sug));
                    break;

                case 4:
                    System.out.println("Most Attempted: " + system.getMostAttempted());
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}