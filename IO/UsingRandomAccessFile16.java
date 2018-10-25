// io/UsingRandomAccessFile16.java
// TIJ4 Chapter IO, Exercise 16, page 936
/* Look up RandomAccessFile in the JDK documentation. Starting with 
* UsingRandomAccessFile.java, create a program that stores and then
* retrieves all the different possible types provided by the 
* RandomAccessFile class. Verify that the values are stored and
* retrieved properly.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* February, 2008
*/

import java.io.*;
import static org.greggordon.tools.Print.*;

public class UsingRandomAccessFile16 {
	static String file = "r16test.dat";
	static void display() throws IOException {
		RandomAccessFile rf = new RandomAccessFile(file, "r");
		byte[] bIn = new byte[3];
		rf.read(bIn);
		for(int i = 0; i < bIn.length; i++)
			print(bIn[i] + " ");
		println();
		// Read next 4 bytes as int:
		println("rf.readInt() = " + rf.readInt());
		println("rf.read() = " + rf.read());		
		println("rf.readBoolean() = " + rf.readBoolean());		
		println("rf.readByte() = " + rf.readByte());		
		println("rf.read() = " + rf.read()); // ASCII h = 104
		println("rf.read() = " + rf.read()); // ASCII i = 105		
		println("rf.readChar() = " + rf.readChar());
		println("rf.readChar() = " + rf.readChar());
		println("rf.readChar() = " + rf.readChar());
		println("rf.readDouble() = " + rf.readDouble());
		println("rf.readFloat() = " + rf.readFloat());
		println("rf.readInt() = " + rf.readInt());
		println("rf.readLong() = " + rf.readLong());
		println("rf.readShort() = " + rf.readShort());
		println("rf.readUTF() = " + rf.readUTF());
		rf.close();
	}
	public static void main(String[] args) throws IOException {	
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		byte[] ba = {0,1,2,3};
		// Store first 3 bytes of byte[] ba:
		rf.write(ba,0,3);
		// Store all 4 bytes in byte[] ba:
		rf.write(ba);
		rf.write((int)255); // Stores the lower 8 bits of int
		rf.writeBoolean(true);
		rf.writeByte((int)1000000);
		rf.writeBytes((String)"hi");
		rf.writeChar(120);
		rf.writeChars("hi");		
		rf.writeDouble(3.14159);
		rf.writeFloat(2.1f);
		rf.writeInt(1057);
		rf.writeLong(123456789L);
		rf.writeShort(123);
		rf.writeUTF("Nice piece of work");		
		display();		
	}
}