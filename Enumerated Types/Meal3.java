// enumerated/menu/Meal3.java
// TIJ4 Chapter Enumerated, Exercise 3, page 1027
// Add a new Course to Course.java and demonstrate that it works in Meal.java.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* June, 2008
*/

package enumerated.menu;
import net.mindview.util.*;

enum Course3 {
	APPETIZER(Food3.Appetizer.class),
	MAINCOURSE(Food3.MainCourse.class),
	SECONDCOURSE(Food3.SecondCourse.class), // added Course
	DESSERT(Food3.Dessert.class),
	COFFEE(Food3.Coffee.class);
	private Food3[] values;
	private Course3(Class<? extends Food3> kind) {
		values = kind.getEnumConstants();
	}
	public Food3 randomSelection() {
		return Enums.random(values);
	}
}

interface Food3 {
	enum Appetizer implements Food3 {
		SALAD, SOUP, SPRING_ROLLS;
	}
	enum MainCourse implements Food3 {
		LASAGNE, BURRITO, PAD_THAI,
		LENTILS, HUMMOUS, VINDALOO;
	}
	enum SecondCourse implements Food3 {
		LEG_OF_LAMB, NEW_YORK_STEAK, 
		MAINE_LOBSTER, ALASKAN_KING_CRAB;
	}
	enum Dessert implements Food3 {
		TIRAMISU, GELATO, BLACK_FOREST_CAKE,
		FRUIT, CREME_CARAMEL;
	}
	enum Coffee implements Food3 {
		BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
		LATTE, CAPPUCCINO, TEA, HERB_TEA;
	}
}

public class Meal3 {
	public static void main(String[] args) {
		for(int i = 0; i < 5; i++) {
			for(Course3 course : Course3.values()) {
				Food3 food = course.randomSelection();
				System.out.println(food);
			}
			System.out.println("---");
		}
	}
}