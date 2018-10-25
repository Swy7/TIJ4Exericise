// io/BlipCheck.java
// TIJ4 Chapter IO, Exercise 28, page 990
/* In Blips.java, copy the file and rename it to BlipCheck.java and
* rename the class Blip2 to BlipCheck (making it public and removing
* the public scope from the class Blips in the process). Remove the
* //! marks in the file and execute the program, including the offending
* lines. Next, comment out the default constructor for BlipCheck. Run
* it and explain why it works. Note that after compiling, you must 
* execute the program with "java Blips" because the main() methon is 
* still in the class Blips.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2008
*/

import java.io.*;
import static net.mindview.util.Print.*;

class Blip1 implements Externalizable {
	public Blip1() {
		print("Blip1 Constructor");
	}
	public void writeExternal(ObjectOutput out) 
	throws  IOException {
		print("Blip1.writeExternal");
	}
	public void readExternal(ObjectInput in) 
	throws IOException, ClassNotFoundException {
		print("Blip1.readExternal");
	}
}

public class BlipCheck implements Externalizable {
	// javac will automatically create a default constructor
	// if no constructors are defined, so don't need:
	// BlipCheck() {
	//	print("BlipCheck Constructor");
	// }
	public void writeExternal(ObjectOutput out) 
	throws IOException {
		print("BlipCheck writeExternal");
	}
	public void readExternal(ObjectInput in) 
	throws IOException, ClassNotFoundException {
		print("BlipCheck readExternal");
	}
}

class Blips {
	public static void main(String[] args) 
	throws IOException, ClassNotFoundException {
		print("Constructing objects:");
		Blip1 b1 = new Blip1();
		Blip2 b2 = new Blip2();
		BlipCheck bc = new BlipCheck();
		ObjectOutputStream o = new ObjectOutputStream(
			new FileOutputStream("Blips.out"));
		print("Saving objects:");
		o.writeObject(b1);
		o.writeObject(b2);
		o.writeObject(bc);
		o.close();
		// Now get them back:
		ObjectInputStream in = new ObjectInputStream(
			new FileInputStream("Blips.out"));
		print("Recovering b1:");
		b1 = (Blip1)in.readObject();
		print("Recovering b2:");
		b2 = (Blip2)in.readObject();
		print("Recovering bc:");
		bc = (BlipCheck)in.readObject();
	}
}