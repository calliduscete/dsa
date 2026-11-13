/**
 * Hash table using OPEN ADDRESSING with LINEAR PROBING.
 *
 * Everything is stored directly in the arrays. If the target slot is taken
 * (a collision), we check the next slot, then the next, wrapping around until
 * we find a free one.
 *
 * Deleting is tricky: we mark the slot as DELETED (a "tombstone") instead of
 * emptying it, otherwise we would break the probe chain for other keys.
 */
public class HashTableLinearProbing {

    private static final int EMPTY = 0;
    private static final int OCCUPIED = 1;
    private static final int DELETED = 2;
    private static final double MAX_LOAD_FACTOR = 0.5;

    private String[] keys;
    private int[] values;
    private int[] states;
    private int size;       // live entries
    private int used;       // live entries + tombstones

    public HashTableLinearProbing() {
        allocate(8);
    }

    private void allocate(int capacity) {
        keys = new String[capacity];
        values = new int[capacity];
        states = new int[capacity];
        size = 0;
        used = 0;
    }

    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % keys.length;
    }

    // Insert or update: O(1) average
    public void put(String key, int value) {
        if ((double) (used + 1) / keys.length > MAX_LOAD_FACTOR) {
            resize();
        }
        int index = hash(key);
        int firstDeleted = -1;
        while (states[index] != EMPTY) {
            if (states[index] == OCCUPIED && keys[index].equals(key)) {
                values[index] = value;           // key exists: update it
                return;
            }
            if (states[index] == DELETED && firstDeleted == -1) {
                firstDeleted = index;
            }
            index = (index + 1) % keys.length;   // probe the next slot
        }
        int target = (firstDeleted != -1) ? firstDeleted : index;
        if (states[target] == EMPTY) {
            used++;
        }
        keys[target] = key;
        values[target] = value;
        states[target] = OCCUPIED;
        size++;
    }

    // Lookup: O(1) average. Returns null if the key is missing.
    public Integer get(String key) {
        int index = hash(key);
        while (states[index] != EMPTY) {
            if (states[index] == OCCUPIED && keys[index].equals(key)) {
                return values[index];
            }
            index = (index + 1) % keys.length;
        }
        return null;
    }

    // Delete by placing a tombstone: O(1) average
    public boolean remove(String key) {
        int index = hash(key);
        while (states[index] != EMPTY) {
            if (states[index] == OCCUPIED && keys[index].equals(key)) {
                states[index] = DELETED;
                keys[index] = null;
                size--;
                return true;
            }
            index = (index + 1) % keys.length;
        }
        return false;
    }

    private void resize() {
        String[] oldKeys = keys;
        int[] oldValues = values;
        int[] oldStates = states;
        allocate(oldKeys.length * 2);
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldStates[i] == OCCUPIED) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        HashTableLinearProbing table = new HashTableLinearProbing();
        table.put("red", 1);
        table.put("green", 2);
        table.put("blue", 3);
        System.out.println("green: " + table.get("green"));        // 2
        System.out.println("yellow: " + table.get("yellow"));      // null

        table.remove("green");
        System.out.println("green after remove: " + table.get("green"));
        System.out.println("blue still found: " + table.get("blue"));

        table.put("green", 20);                                    // reuse the tombstone
        System.out.println("green re-added: " + table.get("green"));

        for (int i = 0; i < 30; i++) {
            table.put("item" + i, i);
        }
        System.out.println("size: " + table.size());
        System.out.println("item29: " + table.get("item29"));
    }
}
