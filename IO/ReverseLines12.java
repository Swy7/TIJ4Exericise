// io/ReverseLines12.java
// TIJ4 Chapter IO, Exercise 12, page 932
/* Modify Exercise 8 to also open a text file so you can write text into it. 
* Write the lines in the LinkedList, along with line numbers (do not attempt
* to use the "LineNumber" classes), out to the file.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* February, 2008
*/

import java.io.*;
import java.util.*;

public class ReverseLines12 {
	// Throw exceptions to console:
	public static String readAndReverse(String filename) throws IOException {
		// Reading input by lines:
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		LinkedList<String> list = new LinkedList<String>(); 
		StringBuilder sb = new StringBuilder(); // For output String
		String[] temp = filename.split("\\.");
		String file = temp[0] + "Reversed." + temp[1]; 
		PrintWriter out = new PrintWriter(file); // Auto buffered
		int lineCount = 1;
		while((s = in.readLine()) != null)
			// Add numbered lines to LinkedList
			list.add(lineCount++ + " " + s); 
		while(list.peekLast() != null) { // While there is a last line
			String t = list.pollLast(); // Removes line 
			out.println(t);
			sb.append(t + "\n"); 
		}
		in.close();
		out.close(); // Flush buffer and close
		return sb.toString();		
	}
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.out.println("Usage: enter file name");
			System.exit(1);
		}
		System.out.println(readAndReverse(args[0]));
	}
}