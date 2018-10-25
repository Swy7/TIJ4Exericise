// annotations/HashMapTest9.java
// TIJ4 Chapter Annotations, Exercise 9, page 1095
// Write basic @Unit tests for HashMap.

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

public class HashMapTest9 extends HashMap<Integer,String> {
	@Test void _put() {
		put(1, "Hi");
		assert get(1).equals("Hi");
	}
	@Test void _containsKey() {
		put(2, "Bye");
		assert containsKey(2);
	}
	@Test void _containsValue() {
		put(3, "yes");
		assert containsValue("yes");
	}
	@Test void _get() {
		put(4, "hello");
		assert get(4).equals("hello");
	}
	@Test void _size() {
		assert size() == 0;
	}
	@Test void _clear() {
		put(5, "so long");
		clear();
		assert size() == 0;
	}		
	public static void main(String[] args) throws Exception {
		OSExecute.command(
			"java net.mindview.atunit.AtUnit HashMapTest9");
	}
}
	