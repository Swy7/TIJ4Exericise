// concurrency/Ex10a.java
// TIJ4 Chapter Concurrency, Exercies 10, page 1141
/** Modify Exercise 5 following the example of the ThreadMethod class,
* so that runTask() takes an argument of the number of Fibonacci 
* numbers to sum, and each time you call runTask() it returns the 
* Future produced by the call to submit().
**/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

import static org.greggordon.tools.Print.*;
import java.util.concurrent.*;
import java.util.*;

class Ex10Fibonacci implements Callable<Integer> {
	private Integer n = 0;
	ExecutorService exec = Executors.newSingleThreadExecutor();
	private int fib(int x) {
		if(x < 2) return 1;
		return fib(x - 2) + fib(x - 1);
	}	
	public Integer call() {
		int result = 0;
		for(int i = 0; i < n; i++) 
			result += fib(i);		
		return (Integer)result;
	}
	public Future<Integer> runTask(Integer n) {
		this.n = n;	
		return exec.submit(this);		
	}
}

public class Ex10 {
	public static void main(String[] args) {		
		Ex10Fibonacci fib = new Ex10Fibonacci();
		try {
			for(int i = 0; i < 15; i++) {
				print("Sum of first " + i + 
					" Fibonacci numbers = ");
				println(fib.runTask(i).get());

			}			
		} catch(InterruptedException e) {
			System.out.println(e);
			return;
		} catch(ExecutionException e) {
			System.out.println(e);	
		} finally {
			fib.exec.shutdown();
		}			
	}
}