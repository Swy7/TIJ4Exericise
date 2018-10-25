// io/ReverseLines7.java
// TIJ4 Chapter IO, Exercise 7, page 928
/* Open a text file so that you can read the file one line at a time. Read
* each line as a String and place that String object into a LinkedList. 
* Print all of the lines in the LinkedList in reverse order.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* January, 2008
*/

import java.io.*;
import java.util.*;

public class ReverseLines7 {
	// Throw exceptions to console:
	public static String readAndReverse(String filename) throws IOException {
		// Reading input by lines:
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		LinkedList<String> list = new LinkedList<String>(); 
		StringBuilder sb = new StringBuilder(); // For output String
		while((s = in.readLine()) != null)
			list.add(s); // Add each line to LinkedList
		while(list.peekLast() != null) // While there is a lase line
			sb.append(list.pollLast() + "\n"); // Removes line
		in.close();
		return sb.toString();		
	}
	public static void main(String[] args) throws IOException {
		System.out.print(readAndReverse("ReverseLines7.java"));
	}
}