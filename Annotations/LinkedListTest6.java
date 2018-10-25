// annotations/LinkedListTest6.java
// TIJ4 Chapter Annotations, Exercise 6, page 1089
// Test LinkedList using the approach shown in HashSetTest.java.

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

public class LinkedListTest6 {
	LinkedList<String> testObject = new LinkedList<String>();
	@Test void initialization() {
		assert testObject.isEmpty();
	}
	@Test void _contains() {
		testObject.add("one");
		assert testObject.contains("one");
	}
	@Test void _remove() {
		testObject.add("one");
		testObject.remove("one");
		assert testObject.isEmpty();
	}
	public static void main(String[] args) throws Exception {
		OSExecute.command(
			"java net.mindview.atunit.AtUnit LinkedListTest6");
	}
}