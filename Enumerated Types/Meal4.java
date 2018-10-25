// enumerated/menu/Meal4.java
// TIJ4 Chapter Enumerated, Exercise 4, page 1027
// Repeat the above (EX 3) for Meal2.java.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* June, 2008
*/

package enumerated.menu;
import net.mindview.util.*;

public enum Meal4 {
	APPETIZER(Food.Appetizer.class),
	MAINCOURSE(Food.MainCourse.class),
	SECONDCOURSE(Food.SecondCourse.class), // added Course
	DESSERT(Food.Dessert.class),
	COFFEE(Food.Coffee.class);
	private Food[] values;
	private Meal4(Class<? extends Food> kind) {
		values = kind.getEnumConstants();
	}
	public interface Food {
	 	enum Appetizer implements Food {
			SALAD, SOUP, SPRING_ROLLS;
			}
		enum MainCourse implements Food {
			LASAGNE, BURRITO, PAD_THAI,
			LENTILS, HUMMOUS, VINDALOO;
		}
		enum SecondCourse implements Food {
			LEG_OF_LAMB, NEW_YORK_STEAK, 
			MAINE_LOBSTER, ALASKAN_KING_CRAB;
		}
		enum Dessert implements Food {
			TIRAMISU, GELATO, BLACK_FOREST_CAKE,
			FRUIT, CREME_CARAMEL;
		}
		enum Coffee implements Food {
			BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
			LATTE, CAPPUCCINO, TEA, HERB_TEA;
		}
	}
	public Food randomSelection() {
		return Enums.random(values);
	}
	public static void main(String[] args) {
		for(int i = 0; i < 5; i++) {
			for(Meal4 meal: Meal4.values()) {
				Food food = meal.randomSelection();
				System.out.println(food);
			}
			System.out.println("---");
		}
	}
}