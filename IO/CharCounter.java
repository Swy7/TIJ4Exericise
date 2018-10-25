// io/CharCounter.java
// TIJ4 Chapter IO, Exercise 17, page 940
/* Using TextFile and a Map<Character,Integer>, create a program that counts
* the occurrence of all the different characters in a file. (So if there are
* 12 occurrences of the letter 'a' in the file, the Integer associated with
* the Character containing 'a' in the Map contains '12').
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* February, 2008
*/

import java.io.*;
import net.mindview.util.*;
import static net.mindview.util.TextFile.*;
import java.util.*;
import static net.mindview.util.Print.*;

public class CharCounter {
	
	public static void main(String[] args) {
		// File whose Characters are to be counted:
		String fileName = "CharCounter.java";
		// Array of all char in file:
		char[] ca = read(fileName).toCharArray();
		// Set of unique Characters in file:
		Set<Character> charSet = new TreeSet<Character>();	
		for(char c : ca) charSet.add(c); // Autoboxing
		// List of all Characters in file:
		List<Character> charList = new ArrayList<Character>();
		for(char c : ca) charList.add(c);
		Map<Character,Integer> charMap = new HashMap<Character,Integer>();
		for(Character c : charSet) {
			int count = 0;
			for(Character d : charList) {
				if(d.equals(c)) count++;
			}
			charMap.put(c, count);
		}  
		print();
		print(charMap);		
	}
}

