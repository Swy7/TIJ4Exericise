// io/DoubleBufferDemo24.java
// TIJ4 Chapter IO, Exercise 24, page 958
// Modify IntBufferDemo.java to use doubles.
// Manipulating ints in a ByteBuffer with a  DoubleBuffer

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* March, 2008
*/

import java.nio.*; 

public class DoubleBufferDemo24 {
	private static final int BSIZE = 1024;
	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.allocate(BSIZE);
		DoubleBuffer db = bb.asDoubleBuffer();
		// Store and array of double:
		db.put(new 
			double[]{11.11,42.42,27.37,99.99,143.143,811.811,1016.1026});
		// Absolute location read and write:
		System.out.println(db.get(3));
		db.put(3, 1811.1811);
		// Setting a new limit before rewinding the buffer.
		db.flip();
		while(db.hasRemaining()) {
			double d = db.get();
			System.out.println(d);
		}
	}
}