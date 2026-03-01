import java.util.*;

public class PlagiarismDetector {

    private HashMap<String, Set<String>> index = new HashMap<>();
    private int n = 3; // n-gram size

    private List<String> generateNGrams(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - n; i++) {
            StringBuilder gram = new StringBuilder();
            for (int j = 0; j < n; j++) {
                gram.append(words[i + j]).append(" ");
            }
            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    public void addDocument(String docId, String text) {
        List<String> ngrams = generateNGrams(text);

        for (String gram : ngrams) {
            index.putIfAbsent(gram, new HashSet<>());
            index.get(gram).add(docId);
        }
    }

    public void analyzeDocument(String docId, String text) {
        List<String> ngrams = generateNGrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {
            if (index.containsKey(gram)) {
                for (String existingDoc : index.get(gram)) {
                    if (!existingDoc.equals(docId)) {
                        matchCount.put(existingDoc,
                                matchCount.getOrDefault(existingDoc, 0) + 1);
                    }
                }
            }
        }

        System.out.println("Total n-grams: " + ngrams.size());

        for (String doc : matchCount.keySet()) {
            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Matched with: " + doc);
            System.out.println("Matches: " + matches);
            System.out.println("Similarity: " + similarity + "%");

            if (similarity > 50) {
                System.out.println("PLAGIARISM DETECTED");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PlagiarismDetector pd = new PlagiarismDetector();

        while (true) {
            System.out.println("\n1. Add Document");
            System.out.println("2. Analyze Document");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Document ID: ");
                    String id = sc.nextLine();

                    System.out.println("Enter text:");
                    String text = sc.nextLine();

                    pd.addDocument(id, text);
                    System.out.println("Document added");
                    break;

                case 2:
                    System.out.print("Enter Document ID: ");
                    String aid = sc.nextLine();

                    System.out.println("Enter text:");
                    String atext = sc.nextLine();

                    pd.analyzeDocument(aid, atext);
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}