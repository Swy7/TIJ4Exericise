// concurrency/Ex15.java
// TIJ4 Chapter Concurrency, Exercise 15, page 1177
/** Create a class with three methods containing critical sections
* that all synchronize on the same object. Create multiple tasks to
* demonstrate that only one of these methods can run at a time. Now
* modify the methods so that each one synchronizes on a different 
* object and show that all three methods can be runing at once. 
**/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

import static net.mindview.util.Print.*; 

class SyncTest1 { // all methods synchronized on this
	public void f1() {
		synchronized(this) {
			for(int i = 0; i < 5; i++) {
				print("f1()");
				Thread.yield();
			}
		}
	}
	public void g1() {
		synchronized(this) {
			for(int i = 0; i < 5; i++) {
				print("g1()");
				Thread.yield();
			}
		}
	}
	public void h1() {
		synchronized(this) {
			for(int i = 0; i < 5; i++) {
				print("h1()");
				Thread.yield();
			}
		}
	}
}

class SyncTest2 { // methods synchronized on different objects
	private Object syncObject1 = new Object();
	private Object syncObject2 = new Object();
	private Object syncObject3 = new Object();
	public void f2() {
		synchronized(syncObject1) {
			for(int i = 0; i < 5; i++) {
				print("f2()");
				Thread.yield();
			}
		}
	}
	public void g2() {
		synchronized(syncObject2) {
			for(int i = 0; i < 5; i++) {
				print("g2()");
				Thread.yield();
			}
		}
	}
	public void h2() {
		synchronized(syncObject3) {
			for(int i = 0; i < 5; i++) {
				print("h2()");
				Thread.yield();
			}
		}
	}
}

public class Ex15 {
	public static void main(String[] args) {
		final SyncTest1 st1 = new SyncTest1();
		final SyncTest2 st2 = new SyncTest2();
		new Thread() {
			public void run() {
				st1.f1();
			}
		}.start();
		new Thread() {
			public void run() {
				st1.g1();
			}
		}.start();
		new Thread() {
			public void run() {
				st1.h1();
			}
		}.start();		
		new Thread() {
			public void run() {
				st2.f2();
			}
		}.start();
		new Thread() {
			public void run() {
				st2.g2();
			}
		}.start();
		new Thread() {
			public void run() {
				st2.h2();
			}
		}.start();			
	}
}
