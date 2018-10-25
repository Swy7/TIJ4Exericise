// containers/Ex32.java
// TIJ4 Chapter Containers, Exercise 32, page 871
/* Repeat the previous exercise for a container of int, and compare the
* performance to an ArrayList<Integer>. In your performance comparison,
* include the process of incrementing each object in the container.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* Compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* December, 2007
*/

import java.util.*;
import static net.mindview.util.Print.*;

class IntsHolder {
	private int size = 0;
	private int[] iArray = new int[size];
	public int size() { return size; }
	// Can add only intss:
	// (Resizing and copying will degrade performance)
	public void add(int x) { 
		size += 1;
		int[] temp = new int[size];
		for(int i = 0; i < iArray.length; i++) temp[i] = iArray[i];
		temp[size - 1] = x;
		iArray = temp;
	}
	// get() returns only ints:
	public int get(int i) {
		if(-1 < i && i < size) return iArray[i];
		else throw new ArrayIndexOutOfBoundsException(i);
	}
	public void incrementAll() {
		for(int i = 0; i < size; i++) iArray[i]++;
	}	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < iArray.length; i++) sb.append((iArray[i] + " "));
		return sb.toString();		
	}	
}

// Alternate to eliminate resizing time for fixed size IntsHolder2:
class IntsHolder2 { 
	private final int SIZE = 1000;
	private int index = 0;
	private int[] iArray = new int[SIZE];
	public int size() { return SIZE; }
	// Can add only ints:
	public void add(int x) {
		if(index < SIZE) iArray[index++] = x;
	}
	// get() returns only ints:
	public int get(int i) {
		if(-1 < i && i < SIZE) return iArray[i];
		else throw new ArrayIndexOutOfBoundsException(i);
	}
	public void incrementAll() {
		for(int i = 0; i < SIZE; i++) iArray[i]++;
	}	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < iArray.length; i++) sb.append((iArray[i] + " "));
		return sb.toString();		
	}	
}

public class Ex32 {
	static long addTimeTest(IntsHolder ih, int reps) {
		long start = System.nanoTime();
		for(int i = 0; i < reps; i++) ih.add(1);
		long stop = System.nanoTime();
		return (stop - start)/reps;		
	}
	static long addTimeTest(ArrayList<Integer> list, int reps) {
		long start = System.nanoTime();
		for(int i = 0; i < reps; i++) list.add(1);
		long stop = System.nanoTime();
		return (stop - start)/reps;
	}
	static long getTimeTest(IntsHolder ih, int reps) {
		long start = System.nanoTime();
		for(int i = 0; i < reps; i++) ih.get(i);
		long stop = System.nanoTime();
		return (stop - start)/reps;		
	}
	static long getTimeTest(ArrayList<Integer> list, int reps) {
		long start = System.nanoTime();
		for(int i = 0; i < reps; i++) list.get(i);
		long stop = System.nanoTime();
		return (stop - start)/reps;
	}
	static long addTimeTest(IntsHolder2 ih, int reps) {
		long start = System.nanoTime();
		for(int i = 0; i < reps; i++) ih.add(1);
		long stop = System.nanoTime();
		return (stop - start)/reps;		
	}
	static long getTimeTest(IntsHolder2 ih, int reps) {
		long start = System.nanoTime();
		for(int i = 0; i < reps; i++) ih.get(i);
		long stop = System.nanoTime();
		return (stop - start)/reps;		
	}
	static long incrementTimeTest(IntsHolder ih) {
		long start = System.nanoTime();
		ih.incrementAll();
		long stop = System.nanoTime();
		return (stop - start)/ih.size();
	}
	static long incrementTimeTest(ArrayList<Integer> list) {
		long start = System.nanoTime();
		for(Integer i : list) i++;
		long stop = System.nanoTime();
		return (stop - start)/list.size();
	}
	static long incrementTimeTest(IntsHolder2 ih) {
		long start = System.nanoTime();
		ih.incrementAll();
		long stop = System.nanoTime();
		return (stop - start)/ih.size();
	}
	public static void main(String[] args) {		
		ArrayList<Integer> list = new ArrayList<Integer>();
		IntsHolder ih = new IntsHolder();
		print("Add times (nanoseconds):");
		print("Mean of 1000:");	
		print("ArrayList add(): " + addTimeTest(list, 1000));
		print("IntsHolder add(): " + addTimeTest(ih, 1000));
		print("Mean of 10000:");	
		print("ArrayList add(): " + addTimeTest(list, 10000));
		print("IntsHolder add(): " + addTimeTest(ih, 10000));
		print();
		print("Get times (nanoseconds):");
		print("Mean of 10000:");
		print("ArrayList get(): " + getTimeTest(list, 10000));
		print("IntsHolder get(): " + getTimeTest(ih, 10000));
		print();
		print("Using alternate fixed size (1000) StringsHolder2");
		print("eliminates resizing and copying:");
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		IntsHolder2 ih2 = new IntsHolder2();
		print("Add times (nanoseconds):");
		print("Mean of 1000:");	
		print("ArrayList add(): " + addTimeTest(list2, 1000));
		print("IntsHolder2 add(): " + addTimeTest(ih2, 1000));
		print();
		print("Get times (nanoseconds):");
		print("Mean of 1000:");
		print("ArrayList get(): " + getTimeTest(list2, 1000));
		print("IntsHolder2 get(): " + getTimeTest(ih2, 1000));	
		print();
		ArrayList<Integer> list3 = new ArrayList<Integer>();
		for(int i = 0; i < 1000; i++) list3.add(1);
		IntsHolder ih3 = new IntsHolder();
		for(int i = 0; i < 1000; i++) ih3.add(1);
		IntsHolder2 ih4 = new IntsHolder2();
		for(int i = 0; i < 1000; i++) ih4.add(1);
		print("Increment times (1000 elements):");
		print("ArrayList: " + incrementTimeTest(list3));	
		print("IntsHolder: " + incrementTimeTest(ih3));
		print("intsHolder2: " + incrementTimeTest(ih4));			
	}
}