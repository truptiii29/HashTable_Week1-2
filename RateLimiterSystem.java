import java.util.*;

public class RateLimiterSystem {

    class TokenBucket {
        int tokens;
        int maxTokens;
        long lastRefillTime;
        int refillRate; // tokens per second

        TokenBucket(int maxTokens, int refillRate) {
            this.maxTokens = maxTokens;
            this.tokens = maxTokens;
            this.refillRate = refillRate;
            this.lastRefillTime = System.currentTimeMillis();
        }

        synchronized boolean allowRequest() {
            refill();

            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long elapsed = (now - lastRefillTime) / 1000;

            if (elapsed > 0) {
                int newTokens = (int) (elapsed * refillRate);
                tokens = Math.min(maxTokens, tokens + newTokens);
                lastRefillTime = now;
            }
        }
    }

    private HashMap<String, TokenBucket> clients = new HashMap<>();

    public synchronized boolean checkRateLimit(String clientId) {
        clients.putIfAbsent(clientId, new TokenBucket(5, 1)); // max 5 req, refill 1/sec
        return clients.get(clientId).allowRequest();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RateLimiterSystem rl = new RateLimiterSystem();

        while (true) {
            System.out.println("\n1. Send Request");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Client ID: ");
                    String client = sc.next();

                    boolean allowed = rl.checkRateLimit(client);

                    if (allowed) {
                        System.out.println("Request Allowed");
                    } else {
                        System.out.println("Rate Limit Exceeded");
                    }
                    break;

                case 2:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}