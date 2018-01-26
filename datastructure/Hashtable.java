/**
 * Data structures 2017, autumn
 * Course work
 *
 * Eetu Rinta-Jaskari (Rinta-Jaskari.Eetu.M@student.uta.fi)
 * 424525
 *
 * This class implements a Hashtable for integers only.
 */
package datastructure;
public class Hashtable {

    //Attributes
    private Hashbucket table[];
    private int capacity;
    private int size;
    private double loadfactor;


    //Constructors
    public Hashtable() {
        capacity = 8;
        table = new Hashbucket[capacity];
        loadfactor = 0.75;
        size = 0;
    }

    public Hashtable(int capacity) {
        this.capacity = capacity;
        table = new Hashbucket[capacity];
        loadfactor = 0.75;
        size = 0;
    }

    public Hashtable(int capacity, double loadfactor) {
        this.capacity = capacity;
        this.loadfactor = loadfactor;
        table = new Hashbucket[capacity];
        size = 0;
    }

    //Functions

    /**
     * Processes a hash of the given key.
     * @param key is the key.
     * @return the hash of the key.
     */
    private int hash(int key) {
        if(key < 0)
            key *= -1;
        return key % capacity;
    }

    /**
     * Rehashes the hashtable doubling the capacity.
     */
    private void rehash(Hashtable n) {
        for(int i = 0; i < capacity; i++) {
            if(table[i] != null) {
                Hashbucket curr = table[i];
                while(curr != null) {
                    n.put(curr.key, curr.value);
                    curr = curr.next;
                }
            }
        }
        table = n.table;
        capacity = n.capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Inserts a key-value pair into the hashtable using the hash-function.
     * Rehashes the hashtable if needed based on loadfactor and capacity.
     * @param k is the key of the key-value pair.
     * @param v is the value of the key-value pair.
     * @return value of the pair added to the hashtable.
     */
    public int put(int k, int v) {
        //Check if rehashing is needed
        if(size >= capacity * loadfactor)
            rehash(new Hashtable(capacity * 2, loadfactor));

        //Check if the key exists and overwrite
        if(exists(k))
            remove(k);

        //Add new value
        int hash = hash(k);
        if(table[hash] == null) {
            table[hash] = new Hashbucket(k, v);
            size++;
            return v;
        } else {
            Hashbucket curr = table[hash];
            while(curr.next != null) {
                curr = curr.next;
            }
            Hashbucket n = new Hashbucket(k, v);
            curr.next = n;
            size++;
            return v;
        }
    }

    /**
     * Finds the value of a key-value pair with the given key.
     * @param key is the key for the key-value pair.
     * @return value if the pair was found, otherwise 0.
     */
    public int get(int key) {
        int hash = hash(key);
        if(table[hash] == null)
            return 0;
        else {
            Hashbucket curr = table[hash];
            while(curr != null && curr.key != key) {
                curr = curr.next;
            }
            //Check if found or the value doesn't exist
            if(curr == null)
                return 0;
            else
                return curr.value;
        }
    }

    /**
     * Finds if a given key exists in the hashtable.
     * @param key is the key to look for.
     * @return true if exists, false if not.
     */
    public boolean exists(int key) {
        //Returns the true if found, otherwise returns false
        int hash = hash(key);
        if(table[hash] == null)
            return false;
        else {
            Hashbucket curr = table[hash];
            while(curr != null && curr.key != key) {
                curr = curr.next;
            }
            //Check if found or the value doesn't exist
            if(curr == null)
                return false;
            else
                return true;
        }
    }

    /**
     * Removes a key-value pair from the hashtable based on the given key.
     * @param key is the key of the key-value pair to remove.
     * @return the value of the removed key-value pair.
     */
    public int remove(int key) {
        //Find and remove a value from the table if it exists.
        //Returns the value if removed, otherwise 0
        int hash = hash(key);
        if(table[hash] == null)
            return 0;
        else {
            Hashbucket curr = table[hash];
            Hashbucket prev = curr;
            boolean first = true;
            while(curr != null && curr.key != key) {
                curr = curr.next;

                if(first)
                    first = false;
                else
                    prev = prev.next;
            }
            //Check if found or the value doesn't exist
            if(curr == null)
                return 0;
            else {
                if(first) {
                    table[hash] = curr.next;
                    size--;
                    return curr.value;
                } else {
                    prev.next = curr.next;
                    size--;
                    return curr.value;
                }
            }
        }
    }

    /**
     * Collects all the keys from the hashtable into an array.
     * @return array with all the keys found in the hashtable.
     */
    public int[] keys() {
        int arr[] = new int[size];
        int i = 0;
        for(int j = 0; j < capacity; j++) {
            if(table[j] != null) {
                Hashbucket curr = table[j];
                while(curr != null) {
                    arr[i] = curr.key;
                    i++;
                    curr = curr.next;
                }
            }
        }

        return arr;
    }
}