import java.util.*;

public class AutocompleteSystem {

    private HashMap<String, Integer> freq = new HashMap<>();

    public void addQuery(String query) {
        freq.put(query, freq.getOrDefault(query, 0) + 1);
    }

    public List<String> search(String prefix) {
        List<String> result = new ArrayList<>();

        for (String q : freq.keySet()) {
            if (q.startsWith(prefix)) {
                result.add(q);
            }
        }

        result.sort((a, b) -> freq.get(b) - freq.get(a));

        return result.size() > 5 ? result.subList(0, 5) : result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AutocompleteSystem ac = new AutocompleteSystem();

        while (true) {
            System.out.println("\n1. Add Query\n2. Search\n3. Exit");
            int ch = sc.nextInt(); sc.nextLine();

            if (ch == 1) {
                System.out.print("Enter query: ");
                ac.addQuery(sc.nextLine());
            } else if (ch == 2) {
                System.out.print("Enter prefix: ");
                System.out.println(ac.search(sc.nextLine()));
            } else return;
        }
    }
}