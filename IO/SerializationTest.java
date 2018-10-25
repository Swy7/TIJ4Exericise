// io/SerializationTest.java
// TIJ4 Chapter IO, Exercise 27, page 984
/* Create a Serializable class containing a reference to an object of a
* second Serializable class. Create an instance of your class, serialize it
* to disk, then restore it and verify that the process worked correctly.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2008
*/

import java.io.*;
import java.util.*;

class A implements Serializable { 
	private int i;	
	A(int i) { this.i = i; }
	public String toString() { return "A:" + i; }
} 

class B implements Serializable {
	private char c;
	private A a;
	B(char c, A a) {
		this.c = c;
		this.a = a;
	}
	public String toString() {
		return "B:" + c + " " + a;
	}	
}

public class SerializationTest implements Serializable {
	private static Random rand = new Random();
	public static void main(String[] args) 
	throws ClassNotFoundException, IOException {
		B b = new B('x', new A(rand.nextInt(100)));
		System.out.println("b: " + b);
		ObjectOutputStream out = new ObjectOutputStream(
			new FileOutputStream("STest.out"));
		out.writeObject(b);
		out.close();
		ObjectInputStream in = new ObjectInputStream(
			new FileInputStream("STest.out"));
		B b2 = (B)in.readObject();
		System.out.println("b2: " + b2);		
	}
}