// annotations/Element10.java
// TIJ4 Chapter Annotations, Exercise 10, page 1095
// Select an example from elsewhere in the book and add @Unit tests.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2009
*/

package annotations;
import net.mindview.atunit.*;
import net.mindview.util.*;

public class Element10 {
	private String ident;
	public Element10(String id) { ident = id; }
	public String toString() { return ident; }
	public int hashCode() { return ident.hashCode(); }
	public boolean equals(Object r) {
		return r instanceof Element10 &&
			ident.equals(((Element10)r).ident);
	}
	protected void finalize() {
		System.out.println("Finalizing " + 
		getClass().getSimpleName() + " " + ident);
	}
	@TestObjectCreate static Element10 create() {
		return new Element10("hi");
	}
	@Test void _hashCode() {
		String s = "hi";
		assert hashCode() == s.hashCode(); 
	}
	@Test void _equals() {
		Element10 e = new Element10("hi");
		assert this.equals(e);
	}
	public static void main(String[] args) throws Exception {
		OSExecute.command(
			"java net.mindview.atunit.AtUnit Element10");
	}
}