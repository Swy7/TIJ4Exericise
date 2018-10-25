// io/CharBufferPrinter23.java
// TIJ4 Chapter IO, Exercise 23, page 953
// Create and test a utility to print the contents of a CharBuffer
// up to the point where the characters are no longer printable.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* March, 2008
*/

import java.nio.*;
import static org.greggordon.tools.Print.*;

public class CharBufferPrinter23 {
	public static boolean isPrintable(char c) {
		// Check char in printable range:
		return (((c >= '!') && (c <= '~')) ? true : false);
	}
	public static void printCharBuffer(CharBuffer cb) {
		cb.clear(); // Sets position to zero, limit to capacity
		while(cb.hasRemaining()) {
			char c = cb.get();		
			if(isPrintable(c)) System.out.print(c);
		}
	} 
	public static void main(String[] args) throws Exception {
		char[] ca = {'w','x','y','z'};
		CharBuffer cb = CharBuffer.wrap(ca);
		print("CharBuffer by wrapping char[]: ");
		printCharBuffer(cb);
		println();
		CharBuffer cb2 = CharBuffer.allocate(6);
		char[] ca2 = {'s','t','u','v','w'};
		cb2.put(ca2); 
		print("CharBuffer by allocation: ");
		printCharBuffer(cb2);
		println();
		// Try some unprintable chars:
		char[] ca3 = {(char)0x01, (char)0x07, (char)0x7F,'b','y','e'};
		CharBuffer cb3 = CharBuffer.wrap(ca3);
		print("CharBuffer including some unprintables: ");
		printCharBuffer(cb3);		
	}
}