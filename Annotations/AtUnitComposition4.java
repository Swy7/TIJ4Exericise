// annotations/AtUnitComposition4.java
// TIJ4 Chapter Annotations, Exercise 4, page 1089
// Verify that a new testObject is created before each test.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2009
*/

/** In same package:
* annotations/AtUnitExampleEx4.java
* package annotations;
* import net.mindview.atunit.*;
* import net.mindview.util.*;
* public class AtUnitExampleEx4 {
*	public static int count = 0;
*	public int id = ++count;
*	public AtUnitExampleEx4() { 
*		System.out.println("Constructing AtUnitExampleEx4 #" + id);
*	}
*	public String methodOne() {
*		System.out.println("In methodOne() of AtUnitExampleEx4 #" +id);
*		return "This is methodOne";
*	}
*	public int methodTwo() {
*		System.out.println("In methodTwo() of AtUnitExampleEx4 #" +id);
*		System.out.println("This is methodTwo");
*		return 2;
*	}
*	@Test boolean methodOneTest() {
*		return methodOne().equals("This is methodOne");
*	}
*	@Test boolean m2() {
*		return methodTwo() == 2;
*	}
*	@Test boolean m3() { return true; }
*	// Show output for failure:
*	@Test boolean failureTest() { return false; }
*	@Test boolean anotherDisappointment() { return false; }
*	public static void main(String[] args) throws Exception {
*		OSExecute.command("java net.mindview.atunit.AtUnit AtUnitExampleEx4");
*	}
* }
**/

package annotations;
import java.util.*;
import net.mindview.atunit.*;
import net.mindview.util.*;

public class AtUnitComposition4 {
	AtUnitExampleEx4 testObject = new AtUnitExampleEx4();
	@Test boolean _methodOne() { 
		return testObject.methodOne().equals("This is methodOne");
	}
	@Test boolean _methodTwo() {
		return testObject.methodTwo() == 2;
	}
	public static void main(String[] args) throws Exception {
		OSExecute.command(
			"java net.mindview.atunit.AtUnit AtUnitComposition4");
	}
}