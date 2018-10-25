// io/DirList1b.java
// TIJ4 Chapter IO, Exercise 1, page 906
/*/ Modify DirList.java (or one of it's variants) so that the FilenameFilter
* opens and reads each file (using the net.mindview.util.TextFile utility) 
* and accepts the file based on whether any of the trailing arguments on the
* command line exist in that file.
* (See also solution DirList1a.java)
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* January, 2008
*/

import java.util.regex.*;
import java.io.*;
import java.util.*;
import net.mindview.util.*; 

public class DirList1b {		
	public static void main(final String[] args) {
		File path = new File(".");
		final String[] list;
		if(args.length == 0) { 
			list = path.list();
			System.out.println(
				"Usage: enter words, one or more of which each file must contain");
		}
		else {
			list = path.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return !(Collections.disjoint(
							Arrays.asList(args),
							new TextFile(name, "\\W+")
						)
					);
				}
			});
		}
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for(String dirItem : list)
			System.out.println(dirItem);
	}
}

