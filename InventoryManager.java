import java.util.*;

public class InventoryManager {

    private HashMap<String, Integer> stock = new HashMap<>();
    private HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    public synchronized void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
        waitingList.put(productId, new LinkedList<>());
    }

    public synchronized String checkStock(String productId) {
        int available = stock.getOrDefault(productId, 0);
        return available + " units available";
    }

    public synchronized String purchaseItem(String productId, int userId) {
        int available = stock.getOrDefault(productId, 0);

        if (available > 0) {
            stock.put(productId, available - 1);
            return "Success! Remaining: " + (available - 1);
        } else {
            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);
            return "Out of stock. Added to waiting list. Position: " + queue.size();
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InventoryManager im = new InventoryManager();

        im.addProduct("IPHONE15_256GB", 3);

        while (true) {
            System.out.println("\n1. Check Stock");
            System.out.println("2. Purchase Item");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter product ID: ");
                    String pid = sc.next();
                    System.out.println(im.checkStock(pid));
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    String product = sc.next();
                    System.out.print("Enter user ID: ");
                    int userId = sc.nextInt();

                    System.out.println(im.purchaseItem(product, userId));
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}