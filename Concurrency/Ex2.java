// concurrency/Ex2.java
// TIJ4 Chapter Concurrency, Exercise 2, page 1120
/** Following the form of generics/Fibonacci.java, create a task that produces
* a sequence of n Fibonacci numbers, where n is provided to the constructor
* of the task. Create a number of these tasks and drive them using threads.
**/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

import static org.greggordon.tools.Print.*;

class Ex2FibonacciA implements Runnable {
	private int n = 0;
	public Ex2FibonacciA(int n) {
		this.n = n;
	}
	private int fib(int x) {
		if(x < 2) return 1;
		return fib(x - 2) + fib(x - 1);
	}
	public void run() {
		for(int i = 0; i < n; i++)
			print(fib(i) + " ");
			println();		
	}
}

class Ex2FibonacciB implements Runnable {
	private int n = 0;
	public Ex2FibonacciB(int n) {
		this.n = n;
	}
	private int fib(int x) {
		if(x < 2) return 1;
		return fib(x - 2) + fib(x - 1);
	}
	public void run() {
		for(int i = 0; i < n; i++)
			print(fib(i) + " ");
			println();		
	}
}

class Ex2FibonacciC implements Runnable {
	private int n = 0;
	public Ex2FibonacciC(int n) {
		this.n = n;
	}
	private int fib(int x) {
		if(x < 2) return 1;
		return fib(x - 2) + fib(x - 1);
	}
	public void run() {
		for(int i = 0; i < n; i++)
			print(fib(i) + " ");	
			println();	
	}
}

class Ex2FibonacciD implements Runnable {
	private int n = 0;
	public Ex2FibonacciD(int n) {
		this.n = n;
	}
	private int fib(int x) {
		if(x < 2) return 1;
		return fib(x - 2) + fib(x - 1);
	}
	public void run() {
		for(int i = 0; i < n; i++)
			print(fib(i) + " ");	
			println();	
	}
}

public class Ex2 {
	public static void main(String[] args) {
		Thread f1 = new Thread(new Ex2FibonacciA(15));		
		Thread f2 = new Thread(new Ex2FibonacciB(15));		
		Thread f3 = new Thread(new Ex2FibonacciC(15));
		Thread f4 = new Thread(new Ex2FibonacciD(15));
		f1.start();
		f2.start();
		f3.start();
		f4.start();
	}
}	