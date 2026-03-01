import java.util.*;

public class MultiLevelCache {

    private LinkedHashMap<String, String> L1;
    private HashMap<String, String> L2;
    private HashMap<String, String> L3;

    public MultiLevelCache() {
        L1 = new LinkedHashMap<>(3, 0.75f, true);
        L2 = new HashMap<>();
        L3 = new HashMap<>();
    }

    public String get(String key) {

        if (L1.containsKey(key)) {
            return "L1 HIT: " + L1.get(key);
        }

        if (L2.containsKey(key)) {
            String val = L2.get(key);
            L1.put(key, val);
            return "L2 HIT → moved to L1";
        }

        if (L3.containsKey(key)) {
            String val = L3.get(key);
            L2.put(key, val);
            return "L3 HIT → moved to L2";
        }

        return "MISS";
    }

    public void put(String key, String value) {
        L3.put(key, value);
    }

    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();

        cache.put("video1", "data1");

        System.out.println(cache.get("video1"));
        System.out.println(cache.get("video1"));
    }
}
