// containers/TwoStrings42.java
// TIJ4 Chapter Containers, Exercise 421, page 885
// Modify Exercise 40 so that an alphabetic sort is used.

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

public class TwoStrings42 implements Comparable<TwoStrings42> {
	private String first = "";
	private String second = "";
	public TwoStrings42(String s1, String s2) {
		first = s1;
		second = s2;
	}
	// Using only first String to compare:
	public int compareTo(TwoStrings42 ts) {
		return first.compareToIgnoreCase(ts.first);
	}
	// Optional inner class to use second String to compare:
	public static class Comp2 implements Comparator<TwoStrings42> {
		public int compare(TwoStrings42 ts1, TwoStrings42 ts2) {
			return ts1.second.compareToIgnoreCase(ts2.second);
		}
	}
	public String toString() {
		return first + " & " + second;
	}
	static void printArray(TwoStrings42[] sa) {
		printnb("(");
		for(int i = 0; i < sa.length - 1; i++) 
			printnb(sa[i] + ", ");
		print(sa[sa.length - 1] + ")");		
	}
	public static void main(String[] args) {
		RandomGenerator.String rgs = new RandomGenerator.String(4);
		TwoStrings42[] array = new TwoStrings42[5];
		List<TwoStrings42> list = new ArrayList<TwoStrings42>();
		for(int i = 0; i < array.length; i++) {
			String s1 = rgs.next();
			String s2 = rgs.next();
			array[i] = new TwoStrings42(s1, s2);
			list.add(new TwoStrings42(s1, s2));
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
		TwoStrings42.Comp2 comp = new TwoStrings42.Comp2();
		Arrays.sort(array, comp);
		Collections.sort(list, comp);
		print();
		print("Sorted by second word:");
		printnb("Array: ");
		printArray(array);
		print("List: " + list);	
	}
}