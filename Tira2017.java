/**
 * Data structures 2017, autumn
 * Course work
 *
 * Edited by
 * Eetu Rinta-Jaskari (Rinta-Jaskari.Eetu.M@student.uta.fi)
 * 424525
 *
 * The running class for the program containing necessary functions.
 * Skeleton for this class was provided in the course webpage.
 */

import java.io.*;
import java.lang.Integer;
import java.util.LinkedList;
import java.util.Arrays;
import datastructure.*;

public class Tira2017 {
	public static void main(String[] args) {
		Tira2017 ht = new Tira2017();
		PrintStream strm = System.out;

		//Print info text
		strm.println("This program reads two input files containing numbers separated by line breaks, and then performs logical OR, AND and XOR -functions from them and writes them to corresponding files.");
		strm.println("The input files may be any size and there is no restriction on how big an individual number can be, besides the min and max values for integers in Java.");
		strm.println("The hashtables use a loadfactor of 0.75 and capacity of 8 on default. The hashtables rehash themselves automatically in order to increase their capacity if needed.\n");

		//Read filenames and then files.
		strm.print("Give the first file to read: ");
		Integer[] setA = ht.readInput(ht.readUserInput());
		strm.print("Give the second file to read: ");
		Integer[] setB = ht.readInput(ht.readUserInput());

		//Print more info
		strm.println("\nThe two inputs files contain " + (setA.length + setB.length) + " items in total.");
		strm.println("You will be asked if you want to remove any items from the output tables before writing to a file.");
		strm.println("An empty input will stop the removal.\n");

		//Perform logical functions, prompt user for removal of items and then write into file.
		Hashtable t;
		t = ht.OR(setA, setB);
		ht.promptUserRemove(t, "OR");
		ht.writeOutput(t, "or.txt");

		t = ht.AND(setA, setB);
		ht.promptUserRemove(t, "AND");
		ht.writeOutput(t, "and.txt");
		
		t = ht.XOR(setA, setB);
		ht.promptUserRemove(t, "XOR");
		ht.writeOutput(t, "xor.txt");
	}

	/**
	 * Asks if the user wants to remove items from the given array.
	 * Removes if input is given.
	 * @param t is the hashtable to remove from.
	 * @param name is the "name" to be printed with the prompts.
	 */
	private void promptUserRemove(Hashtable t, String name) {
		boolean input = true;
		//Keep asking until a empty input is given.
		while(input) {
			//If hashtable is empty
			if(t.isEmpty()) {
				input = false;
				System.out.println("The " + name + "-table has no items to remove.");
			} else {
				boolean inputOK = false;
				String in = "";
				int index = 0;
				//Keep asking until a valid input (integer) is given.
				while(!inputOK) {
					System.out.print("Enter key to remove from " + name + "-table: ");
					in = readUserInput();
					if(!in.isEmpty()) {
						//Parse the string to a integer
						try {
							index = Integer.parseInt(in);
							inputOK = true;
						} catch(NumberFormatException e) {
							System.out.println("Invalid number. Try again.");
						}
					} else {
						inputOK = true;
						input = false;
					}
				}

				if(!in.isEmpty()) {
					t.remove(index);
				}
			}
		}
	}

	/**
	 * Performs the union for the two parameter arrays.
	 * The value in the key-value pairs of the hashtable is the amount of occurrences for each unique number.
	 * @param a is the first Integer-array.
	 * @param b is the second Integer-array.
	 * @return is the hashtable with the union of the arrays a and b.
	 */
	private Hashtable OR(Integer a[], Integer b[]) {
		int x;
		Hashtable or = new Hashtable();
		for(int i =  0; i < a.length; i++) {
			x = a[i].intValue();
			if(or.exists(x)) {
				or.put(x, or.remove(x) + 1);
			} else {
				or.put(x, 1);
			}
		}

		for(int i =  0; i < b.length; i++) {
			x = b[i].intValue();
			if(or.exists(x)) {
				or.put(x, or.remove(x) + 1);
			} else {
				or.put(x, 1);
			}
		}

		return or;
	}

	/**
	 * Performs the intersection for the two parameter arrays.
	 * The value in the key-value pairs of the hashtable is the line number the key occurred in the input file for the first time.
	 * @param a is the first array.
	 * @param b is the second array.
	 * @return is the hashtable that is the intersection of the arrays a and b.
	 */
	private Hashtable AND(Integer a[], Integer b[]) {
		int x;
		Hashtable aH = new Hashtable();
		Hashtable and = new Hashtable();

		//Convert a[] into a Hashtable, saving the position in a[] as the value.
		for(int i = 0; i < a.length; i++) {
			x = a[i].intValue();
			if(!aH.exists(x)) {
				aH.put(x, i + 1);
			}
		}

		//Now if aH has the value from b[], add it to the and-Hashtable
		for(int i = 0; i < b.length; i++) {
			x = b[i].intValue();
			if(aH.exists(x)) {
				and.put(x, aH.get(x));
			}
		}
		return and;
	}

	/**
	 * Performs the "exclusive or" (XOR) for the two parameter arrays.
	 * The value in the key-value pairs of the hashtable represents which of the two arrays the key is from.
	 * 1 = the first, 2 = the second.
	 * @param a is the first array.
	 * @param b is the second array.
	 * @return is the hashtable that is the "exclusive or" of the two arrays.
	 */
	private Hashtable XOR(Integer a[], Integer b[]) {
		int x;
		Hashtable xor = new Hashtable();
		//Convert both tables into hashtables
		Hashtable aH = new Hashtable();
		for(int i = 0; i < a.length; i++) {
			x = a[i].intValue();
			if(!aH.exists(x)) {
				aH.put(x, 1);
			}
		}

		Hashtable bH = new Hashtable();
		for(int i = 0; i < b.length; i++) {
			x = b[i].intValue();
			if(!bH.exists(x)) {
				bH.put(x, 2);
			}
		}

		//Go through both aH and bH Hashtables, and add the keys to the xor-Hashtable that exist only either in aH or bH.
		int[] aKeys = aH.keys();
		int[] bKeys = bH.keys();

		for(int i = 0; i < aKeys.length; i++) {
			if(!bH.exists(aKeys[i]))
				xor.put(aKeys[i], aH.get(aKeys[i]));
		}

		for(int i = 0; i < bKeys.length; i++) {
			if(!aH.exists(bKeys[i]))
				xor.put(bKeys[i], bH.get(bKeys[i]));
		}

		return xor;
	}

	/**
	 * Reads the numbers in the given file. Numbers are assumed to be separated by line breaks.
	 * @param filename is the name of the file to read.
	 * @return is a Integer-type array with the numbers found in the file.
	 */
	private Integer[] readInput(String filename) {
		LinkedList<Integer> n = new LinkedList<Integer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			while(line != null) {
				n.add(Integer.parseInt(line.trim()));
				line = br.readLine();
			}
    	} catch(IOException e) {
	    	System.out.println("File not found.");;
		}
		Integer[] a = new Integer[n.size()];
		n.toArray(a);
		return a;
	}

	/**
	 * Writes the given hashtable into a given file.
	 * One key-value pair is on one line, the first number on a line is the key and the second is the value.
	 * @param n is the hashtable to write.
	 * @param filename is the name of the file to write into.
	 */
	private void writeOutput(Hashtable n, String filename) {
		int keys[] = n.keys();
		Arrays.sort(keys);

		System.out.println("Writing file " + filename + " with " + n.size() + " items...\n");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename)); 
			for(int i = 0; i < keys.length; i++) {
				//Using automatic padding, number length is 4.
				String outputrow = String.format("%4d %4d", keys[i], n.get(keys[i]));
				bw.write(outputrow);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}

	/**
	 * Reads the user's input from the console to a String.
	 * @return a String with the input text from the user.
	 */
	private String readUserInput() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String s = "";
		try {
			s = input.readLine();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		return s;
	}
}