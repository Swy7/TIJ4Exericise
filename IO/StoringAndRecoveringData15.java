// io/StoringAndRecoveringData15.java
// TIJ4 Chapter IO, Exercise 15, page 934
/* Look up DataOutputStream and DataInputStream in the JDK documentation. 
* Starting with StoringAndRecoveringData.java, create a program that stores
* and then retrieves all the different possible types provided by the 
* DataOutputStream and DataInputStream classes. Verify that the values
* are stored and retrieved correctly.
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

public class StoringAndRecoveringData15 {
	public static void main(String[] args) throws IOException {
		DataOutputStream out = new DataOutputStream(
			new BufferedOutputStream(
				new FileOutputStream("Data.txt")));
		println("Initial out.size() = " + out.size());
		byte[] ba = {0,1,2,3};
		// Store first 3 bytes of byte[] ba:
		out.write(ba,0,3);
		// Store all 4 bytes in byte[] ba:
		out.write(ba);
		out.write((int)255); // Stores the lower 8 bits of int
		out.writeBoolean(true);
		out.writeByte((int)1000000);
		out.writeBytes((String)"hi");
		out.writeChar(120);
		out.writeChars("hi");		
		out.writeDouble(3.14159);
		out.writeFloat(2.1f);
		out.writeInt(1057);
		out.writeLong(123456789L);
		out.writeShort(123);
		out.writeUTF("Nice piece of work");
		println("After writing, out.size() = " + out.size());		
		out.close();
		println("Reading:");
		DataInputStream in = new DataInputStream(
			new BufferedInputStream(
				new FileInputStream("Data.txt")));
		byte[] baIn = new byte[3];
		println("bytes read by in.read(baIn, 0, 3) = " + in.read(baIn, 0, 3));
		print("baIn = ");
		for(int i = 0; i < baIn.length; i++)
			print(baIn[i] + " ");
		println();
		// Read next 4 bytes as int:
		println("in.readInt() = " + in.readInt());
		println("in.read() = " + in.read());		
		println("in.readBoolean() = " + in.readBoolean());		
		println("in.readByte() = " + in.readByte());		
		println("in.read() = " + in.read()); // ASCII h = 104
		println("in.read() = " + in.read()); // ASCII i = 105		
		println("in.readChar() = " + in.readChar());
		println("in.readChar() = " + in.readChar());
		println("in.readChar() = " + in.readChar());
		println("in.readDouble() = " + in.readDouble());
		println("in.readFloat() = " + in.readFloat());
		println("in.readInt() = " + in.readInt());
		println("in.readLong() = " + in.readLong());
		println("in.readShort() = " + in.readShort());
		println("in.readUTF() = " + in.readUTF());		
	}
}