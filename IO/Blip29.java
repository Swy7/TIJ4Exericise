// io/Blip29.java
// TIJ4 Chapter IO, Excercise 29, page 990
// In Blip29.java, comment out the two lines after the phrases "You must
// do this:" and run the program. Explain the result and why it differs
// from when the two lines are in the program.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2008
*/

import java.io.*;
import static net.mindview.util.Print.*;

public class Blip29 implements Externalizable {
	private int i;
	private String s; // No initialization
	public Blip29() {
		print("Blip29 Contstructor");
		// s, i not initialized
	}
	public Blip29(String x, int a) {
		print("Blip29(String x, int a)");
		s = x;
		i = a;
		// s & i initialized only in non-default contructor
	}
	public String toString() { return s + i; }
	public void writeExternal(ObjectOutput out) 
	throws IOException {
		print("Blip29.writeExternal");
		// You must do this:
		// (or else default values will be used to
		// initialize fields)
		// out.writeObject(s);
		// out.writeInt(i);
		// or out.writeObject(i); 
	}
	public void readExternal(ObjectInput in)
	throws IOException, ClassNotFoundException {
		print("Blip29.readExternal");
		// You must do this:
		// (or else dafault constructor will automatically
		// initialize fields to default values)
		// s = (String)in.readObject();
		// i = in.readInt();
		// or i = (Integer)in.readObject(); 
	}
	public static void main(String[] args)
	throws IOException, ClassNotFoundException {
		print("Constructing objects:");
		Blip29 b29 = new Blip29("A String ", 47);
		print(b29);
		ObjectOutputStream o = new ObjectOutputStream(
			new FileOutputStream("Blip29.out"));
		print("Saving object:");
		o.writeObject(b29);
		o.close();
		// Now get it back:
		ObjectInputStream in = new ObjectInputStream(
			new FileInputStream("Blip29.out"));
		print("Recovering b29:");
		b29 = (Blip29)in.readObject();
		print(b29);
	}
}