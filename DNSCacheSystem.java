import java.util.*;

public class DNSCacheSystem {

    class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, long ttlMillis) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + ttlMillis;
        }
    }

    private LinkedHashMap<String, DNSEntry> cache;
    private int capacity;

    private int hits = 0;
    private int misses = 0;

    public DNSCacheSystem(int capacity) {
        this.capacity = capacity;

        this.cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > DNSCacheSystem.this.capacity;
            }
        };
    }

    public synchronized String resolve(String domain) {
        long currentTime = System.currentTimeMillis();

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);

            if (entry.expiryTime > currentTime) {
                hits++;
                return "Cache HIT → " + entry.ip;
            } else {
                cache.remove(domain);
                System.out.println("Cache EXPIRED");
            }
        }

        misses++;
        String newIP = fetchFromDNS(domain);
        cache.put(domain, new DNSEntry(newIP, 5000)); // TTL 5 sec

        return "Cache MISS → " + newIP;
    }

    private String fetchFromDNS(String domain) {
        Random rand = new Random();
        return "172.217.14." + rand.nextInt(255);
    }

    public void getStats() {
        int total = hits + misses;
        double hitRate = (total == 0) ? 0 : (hits * 100.0 / total);

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DNSCacheSystem dns = new DNSCacheSystem(3);

        while (true) {
            System.out.println("\n1. Resolve Domain");
            System.out.println("2. Show Stats");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter domain: ");
                    String domain = sc.next();
                    System.out.println(dns.resolve(domain));
                    break;

                case 2:
                    dns.getStats();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}