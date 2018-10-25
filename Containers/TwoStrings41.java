// containers/TwoStrings41.java
// TIJ4 Chapter Containers, Exercise 41, page 885
// Modify the class in the previous exercise so that it will work with
// HashSets and as a key in HashMaps.

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

public class TwoStrings41 implements Comparable<TwoStrings41> {
	private String first = "";
	private String second = "";
	public TwoStrings41(String s1, String s2) {
		first = s1;
		second = s2;
	}
	// Using only first String to compare:
	public int compareTo(TwoStrings41 ts) {
		return first.compareTo(ts.first);
	}
	// Optional inner class to use second String to compare:
	public static class Comp2 implements Comparator<TwoStrings41> {
		public int compare(TwoStrings41 ts1, TwoStrings41 ts2) {
			return ts1.second.compareTo(ts2.second);
		}
	}
	public String toString() {
		return first + " & " + second;
	}
	static void printArray(TwoStrings41[] sa) {
		printnb("(");
		for(int i = 0; i < sa.length - 1; i++) 
			printnb(sa[i] + ", ");
		print(sa[sa.length - 1] + ")");		
	}
	public static void main(String[] args) {
		RandomGenerator.String rgs = new RandomGenerator.String(4);
		RandomGenerator.Integer rgi = new RandomGenerator.Integer(100);
		Set<TwoStrings41> set = new HashSet<TwoStrings41>();
		Map<TwoStrings41,Integer> map = new HashMap<TwoStrings41,Integer>();
		for(int i = 0; i < 4; i++) {
			String s1 = rgs.next();
			String s2 = rgs.next();
			set.add(new TwoStrings41(s1, s2));
			map.put(new TwoStrings41(s1, s2), rgi.next());
		}
		print("Set: " + set);
		print("Map: " + map);
		print("map.keySet(): " + map.keySet());
		print("Sorted by first String:");
		Set<TwoStrings41> treeSet = new TreeSet<TwoStrings41>(set);
		Map<TwoStrings41,Integer> treeMap = 
			new TreeMap<TwoStrings41,Integer>(map);
		print("TreeSet: " + treeSet);
		print("TreeMap: " + treeMap);
		print("Sorted by second String:");
		TwoStrings41.Comp2 comp = new TwoStrings41.Comp2();
		Set<TwoStrings41> treeSet2 = 
			new TreeSet<TwoStrings41>(comp);
		Map<TwoStrings41,Integer> treeMap2 = 
			new TreeMap<TwoStrings41,Integer>(comp);
		treeSet2.addAll(treeSet);
		treeMap2.putAll(treeMap);
		print("TreeSet2: " + treeSet2);
		print("TreeMap2: " + treeMap2);		
	}
}