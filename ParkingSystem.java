
public class ParkingSystem {

    private String[] table;
    private int size;

    public ParkingSystem(int size) {
        this.size = size;
        table = new String[size];
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % size;
    }

    public void park(String plate) {
        int idx = hash(plate);
        int start = idx;

        while (table[idx] != null) {
            idx = (idx + 1) % size;
            if (idx == start) {
                System.out.println("Parking Full");
                return;
            }
        }

        table[idx] = plate;
        System.out.println("Parked at spot " + idx);
    }

    public void exit(String plate) {
        for (int i = 0; i < size; i++) {
            if (plate.equals(table[i])) {
                table[i] = null;
                System.out.println("Vehicle exited from " + i);
                return;
            }
        }
        System.out.println("Not found");
    }

    public static void main(String[] args) {
        ParkingSystem ps = new ParkingSystem(10);
        ps.park("ABC123");
        ps.park("XYZ999");
        ps.exit("ABC123");
    }
}