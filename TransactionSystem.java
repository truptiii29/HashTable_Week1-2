import java.util.*;

public class TransactionSystem {

    static class Transaction {
        int id, amount;
        String merchant;

        Transaction(int id, int amount, String merchant) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
        }
    }

    public static void twoSum(List<Transaction> list, int target) {
        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : list) {
            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                System.out.println("Pair: " + map.get(complement).id + ", " + t.id);
            }

            map.put(t.amount, t);
        }
    }

    public static void main(String[] args) {
        List<Transaction> list = new ArrayList<>();

        list.add(new Transaction(1, 500, "A"));
        list.add(new Transaction(2, 300, "B"));
        list.add(new Transaction(3, 200, "C"));

        twoSum(list, 500);
    }
}