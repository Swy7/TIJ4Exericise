// io/ReverseLines8.java
// TIJ4 Chapter IO, Exercise 8, page 928
// Modify Exercise 7 so that the name of the file you read is provided as a 
// command-line argument.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* January, 2008
*/

import java.io.*;
import java.util.*;

public class ReverseLines8 {
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
		if(args.length != 1) {
			System.out.println("Usage: enter file name");
			System.exit(1);
		}
		System.out.print(readAndReverse(args[0]));
	}
}