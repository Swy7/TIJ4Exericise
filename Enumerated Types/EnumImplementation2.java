// enumerated/cartoons/EnumImplementation2.java
// Exercise 2, page 1021
// Instead of implementing an interface, make next() a static 
// method. What are the benefits and drawbacks of this approach?

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2008
*/

package enumerated.cartoons;
import java.util.*;

enum CartoonCharacter { 
	SLAPPY, SPANKY, PUNCHY, SILLY, BOUNCY, NUTTY, BOB;
	private static Random rand = new Random();
	public static CartoonCharacter next() {
		return values()[rand.nextInt(values().length)];
	}
}

public class EnumImplementation2 {
	public static void main(String[] args) {
		// Choose any instance:
		CartoonCharacter cc = CartoonCharacter.BOB;
		for(int i = 0; i < 10; i++) {
			System.out.print(cc.next()); 
			System.out.print((i < 9) ? ", " : "");			
		}
	}
}	