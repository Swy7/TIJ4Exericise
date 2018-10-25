// io/DirListFileSizes.java
// TIJ4 Chapter IO, Exercise 3, page 906
// Modify DirList.java (or one of its variants) so that it
// sums up the file sizes of the selected files.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* January, 2008
*/

import java.io.*;
import java.util.regex.*;
import java.util.*;

public class DirListFileSizes {	
	public static void main(final String[] args) {
		File path = new File(".");
		File[] files;
		if(args.length == 0)
		 	files = path.listFiles(); 
		else
			files = path.listFiles(new FileFilter() {
				private Pattern pattern = Pattern.compile(args[0]);
				public boolean accept(File file) {
					return pattern.matcher(file.getPath()).matches();
				}
			});
		Arrays.sort(files);
		long sum = 0;
		for(File f : files) {
			System.out.print(f + ": ");
			System.out.println(f.length());
			sum += f.length();
		}
		System.out.println("Total size: " + sum);
	}
}