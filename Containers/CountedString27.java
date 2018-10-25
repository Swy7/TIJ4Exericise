// containers/CountedString27.java
// Creating a good hashCode().
// TIJ4 Chapter Containers, Exercise 27, page 858
/* Modify the hashCode() in CountedString.java by removing the combination
* with id, and demonstrate that CountedString still works as a key. What
* is the problem with this approach?
*/

/* This is my solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* December, 2007
*/

import java.util.*;
import static net.mindview.util.Print.*;

public class CountedString27 {
	private static List<String> created = 
		new ArrayList<String>();
	private String s;
	private int id = 0;
	public CountedString27(String str) { 
		s = str;
		created.add(s);
		// id is the total number of instances
		// of this string in use by CountedString:
		for(String s2: created)
			if(s2.equals(s)) id++;
	}
	public String toString() {
		return "String: " + s + " id: " + id +
			" hashCode(): " + hashCode();
	}	
	public int hashCode() {
		int result = 17;
		result = 37 * result + s.hashCode();
		// result = 37 * result + id;
		return result;
	}
	public boolean equals(Object o) {
		return o instanceof CountedString27 &&
			s.equals(((CountedString27)o).s) &&
			id == ((CountedString27)o).id;
	}
	public static void main(String[] args) {
		Map<CountedString27,Integer> map =
			new HashMap<CountedString27,Integer>();
		CountedString27[] cs = new CountedString27[5];
		for(int i = 0; i < cs.length; i++) {
			cs[i] = new CountedString27("hi");
			map.put(cs[i], i); // Autobox int -> Integer
		}
		print(map);
		// Problem: same hash code for different objects:
		for(CountedString27 cstring : cs) {
			print("Looking up " + cstring);
			print(map.get(cstring));
		}	
	}
}