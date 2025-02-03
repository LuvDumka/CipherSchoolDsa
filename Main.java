import java.util.*;

class HashTable {
    private int size;
    private String method;
    private List<LinkedList<Integer>> chainingTable;
    private Integer[] openAddressingTable;
    
    public HashTable(int size, String method) {
        this.size = size;
        this.method = method;
        
        if (method.equals("CHAINING")) {
            chainingTable = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                chainingTable.add(new LinkedList<>());
            }
        } else if (method.equals("OPEN_ADDRESSING")) {
            openAddressingTable = new Integer[size];
            Arrays.fill(openAddressingTable, null);
        }
    }
    
    private int hashFunction(int key) {
        return key % size;
    }
    
    public void insert(int key) {
        int index = hashFunction(key);
        if (method.equals("CHAINING")) {
            if (!chainingTable.get(index).contains(key)) {
                chainingTable.get(index).add(key);
            }
        } else if (method.equals("OPEN_ADDRESSING")) {
            int i = index;
            while (openAddressingTable[i] != null) {
                i = (i + 1) % size;
                if (i == index) return; // Table is full
            }
            openAddressingTable[i] = key;
        }
    }
    
    public String search(int key) {
        int index = hashFunction(key);
        if (method.equals("CHAINING")) {
            return chainingTable.get(index).contains(key) ? "FOUND" : "NOT FOUND";
        } else if (method.equals("OPEN_ADDRESSING")) {
            int i = index;
            while (openAddressingTable[i] != null) {
                if (openAddressingTable[i].equals(key)) {
                    return "FOUND";
                }
                i = (i + 1) % size;
                if (i == index) break;
            }
        }
        return "NOT FOUND";
    }
    
    public void delete(int key) {
        int index = hashFunction(key);
        if (method.equals("CHAINING")) {
            chainingTable.get(index).remove((Integer) key);
        } else if (method.equals("OPEN_ADDRESSING")) {
            int i = index;
            while (openAddressingTable[i] != null) {
                if (openAddressingTable[i].equals(key)) {
                    openAddressingTable[i] = null;
                    return;
                }
                i = (i + 1) % size;
                if (i == index) break;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());
        String method = scanner.nextLine().trim();
        int q = Integer.parseInt(scanner.nextLine());
        
        HashTable hashTable = new HashTable(m, method);
        
        for (int i = 0; i < q; i++) {
            String[] query = scanner.nextLine().split(" ");
            String command = query[0];
            int key = Integer.parseInt(query[1]);
            
            switch (command) {
                case "INSERT":
                    hashTable.insert(key);
                    break;
                case "SEARCH":
                    System.out.println(hashTable.search(key));
                    break;
                case "DELETE":
                    hashTable.delete(key);
                    break;
            }
        }
        
        scanner.close();
    }
}
