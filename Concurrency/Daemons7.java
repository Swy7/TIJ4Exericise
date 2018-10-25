// concurrency/Daemons7.java
// TIJ4 Chapter Concurrency, Exercise 7, page 1135
// Experiment with different sleep times in Daemons.java to see what happens

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

// With my machine (SonyViao 1.20GHz, WinXP SP2) I have to eliminate sleep time
// and increase number of threads to 30 before not all threads are started and/or
// not all isDaemon() tests are printed:
import java.util.concurrent.*;
import static net.mindview.util.Print.*;

class Daemon implements Runnable {
	private Thread[] t = new Thread[30];
	public void run() {
		for(int i = 0; i < t.length; i++) {
			t[i] = new Thread(new DaemonSpawn());
			t[i].start();
			printnb("DaemonSpawn " + i + " started, ");
		}
		for(int i = 0; i < t.length; i++)
			printnb("t[" + i + "].isDaemon() = " + 
				t[i].isDaemon() + ", ");
		while(true)
			Thread.yield();
	}
}

class DaemonSpawn implements Runnable {
	public void run() {
		while(true)
			Thread.yield();
	}
}

public class Daemons7 {
	public static void main(String[] args) throws Exception {
		Thread d = new Thread(new Daemon());
		d.setDaemon(true);
		d.start();
		printnb("d.isDaemon() = " + d.isDaemon() + ", ");
		// no sleeping: 
		// TimeUnit.SECONDS.sleep(1);
	}
}

