/**
 * Data structures 2017, autumn
 * Course work
 *
 * Eetu Rinta-Jaskari (Rinta-Jaskari.Eetu.M@student.uta.fi)
 * 424525
 *
 * This class implements a "bucket" to be used in a Hashtable for integers.
 */
package datastructure;
public class Hashbucket {

    //Attributes

    public int key;
    public int value;
    public Hashbucket next;


    //Constructors

    public Hashbucket() {
        key = 0;
        value = 0;
        next = null;
    }

    public Hashbucket(int k) {
        key = k;
        value = 0;
        next = null;
    }

    public Hashbucket(int k, int val) {
        key = k;
        value = val;
        next = null;
    }
}