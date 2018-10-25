// io/BasicFileOutput14.java
// TIJ4 Chapter IO, Exercise 14, page 932
// Starting with BasicFileOutput.java, write a program that compares the
// performance of writing to a file when using buffered and unbuffered I/O.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* February, 2008
*/

import java.io.*;

public class BasicFileOutput14 {
	static String file = "BasicFileOutput14.out";
	static String file2 = "BasicFileOutput142.out";
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(
			new StringReader(
				BufferedInputFile.read("BasicFileOutput14.java")));
		// Buffered writer:
		PrintWriter out = new PrintWriter(
			new BufferedWriter(new FileWriter(file)));
		int lineCount = 1;
		String s;
		long start = System.nanoTime();
		while((s = in.readLine()) != null )
			out.println(lineCount++ + ": " + s);
		long duration = System.nanoTime() - start;
		out.close();
		System.out.println("Buffered write:   " + duration + " nanoseconds");
		BufferedReader in2 = new BufferedReader(
			new StringReader(
				BufferedInputFile.read("BasicFileOutput14.java")));
		// Unbuffered writer:
		PrintWriter out2 = new PrintWriter(new FileWriter(file2));
		int lineCount2 = 1;
		String s2;
		long start2 = System.nanoTime();
		while((s2 = in2.readLine()) != null )
			out2.println(lineCount2++ + ": " + s2);
		long duration2 = System.nanoTime() - start2;
		out2.close();
		System.out.println("Unbuffered write: " + duration2 + " nanoseconds");
		// Show the stored files:
		System.out.println("file: " + BufferedInputFile.read(file));
		System.out.println("file2: " + BufferedInputFile.read(file2));
	}		
}