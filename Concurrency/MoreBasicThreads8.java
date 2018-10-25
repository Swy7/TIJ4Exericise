// concurrency/MoreBasicThreads8.java
// TIJ4 Chapter Concurrency, Exercies 8, page 1135
// Modify MoreBasicThreads.java so that all the threads are daemon threads,
// and verify that the program ends as soon as main() is able to exit.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

// On my SonyViao 1.20GHz, WinXP SP2, many (not all) Daemons still print 
// after "Finally out of main"

import static net.mindview.util.Print.*;
public class MoreBasicThreads8 {
	public static void main(String[] args) {
		try { // increase number of threads to see effect
			for(int i = 0; i < 25; i++) { 
				Thread t = new Thread(new LiftOff());
				t.setDaemon(true);
				t.start();				
			}
			System.out.println("Waiting for LiftOff");	
		
		} finally {
			System.out.println("Finally out of main");
		}
	}
}