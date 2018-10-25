// enumerated/TrafficLight1.java
// TIJ4 Chapter Enumerated Types, Exercise 1, page 1017
// Use a static import to modify TrafficLight.java so you 
// don't have to qualify the enum instances.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2008
*/

package enumerated;
import static net.mindview.util.Print.*;
import static enumerated.Signal.*;

// Define an enum type in another file in same package:
// public enum Signal { GREEN, YELLOW, RED }

public class TrafficLight1 {
	Signal color = RED;
	public void change() {
		switch(color) {
			case RED:	color = GREEN;
					break;
			case GREEN: 	color = YELLOW;
					break;
			case YELLOW:	color = RED;
					break;			
		}
	}
	public String toString() {
		return "The traffic light is " + color;
	}
	public static void main(String[] args) {
		TrafficLight1 t = new TrafficLight1();
		for(int i = 0; i < 7; i++) {
			print(t);
			t.change();
		}		
	}
}