// io/PreferencesDemo33.java
// TIJ4 Chapter IO, Exercise 33, page 1008
// Write a program that displays the current value of a directory called
// "base directory" and prompts you for a new value. Use the Preferences API
// to store the value.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2008
*/

import java.util.prefs.*;
import static net.mindview.util.Print.*;
import java.io.*;

public class PreferencesDemo33 {
	public static void main(String[] args) throws Exception {		
		Preferences prefs = Preferences
			.userNodeForPackage(PreferencesDemo33.class);
		int value = prefs.getInt("base directory", 0);
		System.out.print("Base directory value = " + value +
			"\nEnter new base directory value (integer): ");
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));
		try {
         		value = Integer.parseInt(br.readLine());
      		} catch (Exception e) {
         		System.err.println(e);
         		System.exit(1);
      		}
		prefs.putInt("base directory", value);		
	}
}