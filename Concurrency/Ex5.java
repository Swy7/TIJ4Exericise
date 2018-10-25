// concurrency/Ex5.java
// TIJ4 Chapter Concurrency, Exercise 5, page 1125
// Modify Exercise 2 so that the task is a Callable that sums the values of all
// the Fibonacci numbers. Create several tasks and display the results.

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

class Ex5Fibonacci implements Callable<Integer> {
	private int n = 0;
	public Ex5Fibonacci(int n) {
		this.n = n;
	}
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
}

public class Ex5 {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
		for(int i = 0; i < 20; i++)
			results.add(exec.submit(new Ex5Fibonacci(i)));
		for(Future<Integer> fs : results)
			try {
				// get() blocks until completion:
				System.out.println(fs.get());
			} catch(InterruptedException e) {
				System.out.println(e);
				return;
			} catch(ExecutionException e) {
				System.out.println(e);	
			} finally {
				exec.shutdown();
			}				
	}
}