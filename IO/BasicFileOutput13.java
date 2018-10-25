// io/BasicFileOutput13.java
// TIJ4 Chapter IO, Exercise 13, page 932
/* Modify BasicFileOutput.java so that it uses LineNumberReader to keep
* track of the line count. Note that it's much easier to just keep track
* programmatically.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* February, 2008
*/

import java.io.*;

public class BasicFileOutput13 {
	static String file = "BasicFileOutput13.out";
	public static void main(String[] args) throws IOException {
		LineNumberReader in = new LineNumberReader(
			new FileReader("BasicFileOutput13.java"));
		PrintWriter out = new PrintWriter(file);
		String s;
		while((s = in.readLine()) != null )
			out.println(in.getLineNumber() + ": " + s);
		out.close();
		// Show the stored file:
		System.out.println(BufferedInputFile.read(file));
	}		
}