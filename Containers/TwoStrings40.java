// containers/TwoStrings40.java
// TIJ4 Chapter Containers, Exercise 40, page 885
/* Create a class containing two String objects and make it Comparable so
* that the comparison only cares about the first String. Fill an array
* and an ArrayList with objects of your class, using the RandomGenerator
* generator. Demonstrate that sorting works properly. Now make a 
* Comparator that only cares about the second String, and demonstrate
* that sorting works properly. Also perform a binary search using your
* Comparator.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* December, 2007
*/

import java.util.*;
import net.mindview.util.*;
import static net.mindview.util.Print.*;

public class TwoStrings40 implements Comparable<TwoStrings40> {
	private String first = "";
	private String second = "";
	public TwoStrings40(String s1, String s2) {
		first = s1;
		second = s2;
	}
	// Using only first String to compare:
	public int compareTo(TwoStrings40 ts) {
		return first.compareTo(ts.first);
	}
	// Optional inner class to use second String to compare:
	public static class Comp2 implements Comparator<TwoStrings40> {
		public int compare(TwoStrings40 ts1, TwoStrings40 ts2) {
			return ts1.second.compareTo(ts2.second);
		}
	}
	public String toString() {
		return first + " & " + second;
	}
	static void printArray(TwoStrings40[] sa) {
		printnb("(");
		for(int i = 0; i < sa.length - 1; i++) 
			printnb(sa[i] + ", ");
		print(sa[sa.length - 1] + ")");		
	}
	public static void main(String[] args) {
		RandomGenerator.String rgs = new RandomGenerator.String(4);
		TwoStrings40[] array = new TwoStrings40[5];
		List<TwoStrings40> list = new ArrayList<TwoStrings40>();
		for(int i = 0; i < array.length; i++) {
			String s1 = rgs.next();
			String s2 = rgs.next();
			array[i] = new TwoStrings40(s1, s2);
			list.add(new TwoStrings40(s1, s2));
		}
		printnb("Array: ");
		printArray(array);
		print("List: " + list);
		Arrays.sort(array);
		Collections.sort(list, null);
		print();
		print("Sorted by first word:");
		printnb("Array: ");
		printArray(array);
		print("List: " + list);
		TwoStrings40.Comp2 comp = new TwoStrings40.Comp2();
		Arrays.sort(array, comp);
		Collections.sort(list, comp);
		print();
		print("Sorted by second word:");
		printnb("Array: ");
		printArray(array);
		print("List: " + list);
	}
}