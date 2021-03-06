// io/ProcessFiles5.java
// TIJ4 Chapter IO, Exercise 5, page 912
// Modify ProcessFiles.java so that it matches a regular expression
// rather that a fixed extension.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* January, 2008
*/

import java.io.*;
import java.util.regex.*;
import net.mindview.util.*;

public class ProcessFiles5 {
 	 public interface Strategy {
    		void process(File file);
  	}
  	private Strategy strategy;
 	private String regex;
	public ProcessFiles5(Strategy strategy, String regex) {
    		this.strategy = strategy;
   	 	this.regex = regex;
  	}
  	public void start(String[] args) {
    		try {
      			if(args.length == 0)
        		processDirectoryTree(new File("."));
      		else
        		for(String arg : args) {
          			File fileArg = new File(arg);
          			if(fileArg.isDirectory())
            				processDirectoryTree(fileArg);
          			else {
					Pattern pattern = Pattern.compile(regex);
					if(pattern.matcher(arg).matches())            
            					strategy.process(
              						new File(arg).getCanonicalFile());
          			}
        		}
    		} catch(IOException e) {
      			throw new RuntimeException(e);
    		}
  	}
  	public void processDirectoryTree(File root) throws IOException {
    		for(File file : Directory.walk(
        		root.getAbsolutePath(), regex))
      		strategy.process(file.getCanonicalFile());
  	}
  	// Demonstration of how to use it:
  	public static void main(String[] args) {
    		new ProcessFiles5(new ProcessFiles5.Strategy() {
      			public void process(File file) {
        			System.out.println(file);
      			}
    		}, ".*D.*").start(args);
  	}
} 
