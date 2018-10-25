// annotations/Ex8Test.java
// TIJ4 Chapter Annotations, Exercise 8, page 1095
// Create a class with a private method and add a non-private @TestProperty method 
// as described above. Call this method in your test code.


/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2009
*/

/* In same package:
* package annotations;
* import net.mindview.atunit.*;
* public class Ex8 {
*	private String methodOne() { return "methodOne"; }
*	@TestProperty protected String methodOneCall() { return this.methodOne(); }
* }
*/

package annotations;
import net.mindview.atunit.*;
import net.mindview.util.*;

public class Ex8Test extends Ex8 {
	@Test boolean _methodOneCall() { 
		return methodOneCall().equals("methodOne");
	}
	public static void main(String[] args) throws Exception {
		OSExecute.command(
			"java net.mindview.atunit.AtUnit Ex8Test");
	}
}
	