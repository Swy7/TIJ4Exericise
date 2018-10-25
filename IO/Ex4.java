// io/Ex4.java
// TIJ4 Chapter IO, Exercise 4, page 912
// Use Directory.walk() to sum the sizes of all files in a directory
// tree whose names match a particular regular expression.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* January, 2008
*/

import java.io.*;
import net.mindview.util.*;

public class Ex4 {
	public static void main(String[] args) {
		long sum = 0;
		if(args.length == 0) {
			for(File file : Directory.walk(".").files) {
				System.out.print(file + ": ");
				System.out.println(file.length());
				sum += file.length();
			}
			
		}
		else 
			for(String arg : args) {
				{
				for(File file : Directory.walk(".", arg).files) {
					System.out.print(file + ": ");
					System.out.println(file.length());
					sum += file.length();
				}
				System.out.println();
			}
		}
		System.out.println("Total size: " + sum);
	}
}