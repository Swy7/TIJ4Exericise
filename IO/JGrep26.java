// io/JGrep26.java
// TIJ4 Chapter IO, Exercise 26, page 970
// Modify strings/JGrep.java to use java.nio memory mapped files.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2008
*/

import java.util.regex.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.*;
import java.nio.charset.*;

public class JGrep26  {
	public static void main(String[] args) throws Exception {
		if(args.length < 2) {
			System.out.println("Usage: java JGrep26 file regex");
			System.exit(0);
		}
		FileChannel fc = new FileInputStream(args[0]).getChannel();		
		MappedByteBuffer in = 
			fc.map(FileChannel.MapMode.READ_ONLY, 0, 
				new File(args[0]).length());
		// Decode bytes to chars and create array of 'lines':
		String[] sa = Charset.forName(System.getProperty("file.encoding"))
			.decode(in).toString().split("\n");
		Pattern p = Pattern.compile(args[1]);		
		Matcher m = p.matcher(""); // creates emtpy Matcher object
		int index = 0;
		for(String line : sa) {
			m.reset(line);
			while(m.find())
				System.out.println(index++ + ": " + 
					m.group() + ": " + m.start());
		}
		fc.close();
	}
}