// io/ByteCounter19.java
// TIJ4 Chapter IO, Exercise 19, page 941
// 19 Using BinaryFile and a Map<Byte,Integer>, create a program that 
// counts the occurrence of all the different bytes in a file.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* March, 2008
*/

import java.io.*;
import net.mindview.util.*;
import static net.mindview.util.BinaryFile.*;
import java.util.*;
import static net.mindview.util.Print.*;

public class ByteCounter19 {	
	public static void main(String[] args) {
		// File whose Characters are to be counted:
		String fileName = "ByteCounter19.java";
		try {
			// Array of all bytes in file:
			byte[] ba = read(fileName);		
			// Set of unique Bytes in file:
			Set<Byte> byteSet = new TreeSet<Byte>();	
			for(Byte b : ba) byteSet.add(b); // Autoboxing
			// List of all Bytes in file:
			List<Byte> byteList = new ArrayList<Byte>();
			for(byte b : ba) byteList.add(b);
			Map<Byte,Integer> byteMap = new HashMap<Byte,Integer>();
			for(Byte b : byteSet) {
				int count = 0;
				for(Byte d : byteList) {
					if(d.equals(b)) count++;
				}
			byteMap.put(b, count);
			}  
			print();
			print(byteMap);	
		} catch(IOException e) {
			System.err.print("Caught " + e);
		} 
	}
}