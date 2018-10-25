// annotations/LinkedListTest7.java
// TIJ4 Chapter Annotations, Exercise 7, page 1089
// Modify the previous exercise to use the inheritance approach.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2009
*/

package annotations;
import java.util.*;
import net.mindview.atunit.*;
import net.mindview.util.*;

public class LinkedListTest7 extends LinkedList<String> {
	@Test void initialization() {
		assert this.isEmpty();
	}
	@Test void _contains() {
		this.add("one");
		assert this.contains("one");
	}
	@Test void _remove() {
		this.add("one");
		this.remove("one");
		assert this.isEmpty();
	}
	public static void main(String[] args) throws Exception {
		OSExecute.command(
			"java net.mindview.atunit.AtUnit LinkedListTest7");
	}
}