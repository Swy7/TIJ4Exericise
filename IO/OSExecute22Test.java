// io/OSExecute22Test.java
// TIJ4 Chapter IO, Exercise 22, page 946
/* Modify OSExecute.java so that, instead of printing the standard output stream,
* it returns the results of executing the program as a List of Strings. Demonstrate
* the use of this new version fo the utility.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* March, 2008
*/

// Run an operating system command and return the output as List<String>
import java.io.*;
import java.util.*;
import static org.greggordon.tools.Print.*;

class OSExecute22Exception extends RuntimeException {
	public OSExecute22Exception(String s) { super(s); }
}

class OSExecute22 {
	public static List<String> command(String command) {
		boolean err = false;
		List<String> ls = new ArrayList<String>(); 
		try {
			Process process = new ProcessBuilder(
				command.split(" ")).start();
			BufferedReader results = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
			String s;			
			while((s = results.readLine()) != null) {
				ls.add(s);
			}
			BufferedReader errors = new BufferedReader(
				new InputStreamReader(process.getErrorStream()));
			// Report errors and return nonzero value to calling
			// process if there are problems:
			while((s = errors.readLine()) != null) { 
				System.err.println(s);
				err = true;
			}
		} catch(Exception e) {
			// Compensate for Windows 2000, which throws an exception
			// for the default command line:
			if(!command.startsWith("CMD /C"))	
				command("CMD /C " + command);
			else
				throw new RuntimeException(e);
		}
		if(err)
			throw new OSExecute22Exception("Errors executing " + command);
		return ls;
	}
} 

public class OSExecute22Test {
	public static void main(String[] args) {
		for(String s : OSExecute22.command("javap OSExecute22Test"))
			println(s);
	}
}