// io/ReverseLinesWordSearch10.java
// TIJ4 Chapter IO, Exercise 10, page 928
// Modify Exercise 8 to take additional command-line arguments of words to 
// find in the file. Print all lines in which any of the words match.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* January, 2008
*/

import java.io.*;
import java.util.*;

public class ReverseLinesWordSearch10 {
	// Throw exceptions to console:
	public static String readReverseFind(String[] sa) throws IOException {
		// Reading input by lines:
		BufferedReader in = new BufferedReader(new FileReader(sa[0]));
		String s;
		List<String> find = new ArrayList<String>();
		for(String t : sa) find.add(t);
		find.remove(0); // Remove the file name
		LinkedList<String> list = new LinkedList<String>(); 
		StringBuilder sb = new StringBuilder(); // For output String
		while((s = in.readLine()) != null) {
			// List of words in line:
			List<String> line = Arrays.asList(s.split("\\W+"));
			// Find lines with word(s) in common: 
			if(!(Collections.disjoint(line, find))) 
				list.add(s); // Add to list 		
		}
		while(list.peekLast() != null) // While there is a last line
			sb.append(list.pollLast() + "\n"); // Removes line from list
		in.close();
		return sb.toString();		
	}
	public static void main(String[] args) throws IOException {
		if(args.length < 2) {
			System.out.println("Usage: enter file name\n" +
				"followed by words to find in lines of that file");
			System.exit(1);
		}
		System.out.print(readReverseFind(args));
	}
}