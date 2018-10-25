// enumerated/menu/Meal6.java
// TIJ4 Chapter Enumerated, Exercise 6, page 1028
/* Is there any special benefit in nexting Appetizer, MainCourse, Dessert, and 
* Coffee inside Food rather than making them standalone enums that just happen
* to implement Food?
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* June, 2008
*/

// Standalone enum technic works (see below), but code is not as clear.

package enumerated.menu;
import net.mindview.util.*;

interface Food {}

enum Appetizer implements Food {
	SALAD, SOUP, SPRING_ROLLS;
	}
enum MainCourse implements Food {
	LASAGNE, BURRITO, PAD_THAI,
	LENTILS, HUMMOUS, VINDALOO;
}
enum Dessert implements Food {
	TIRAMISU, GELATO, BLACK_FOREST_CAKE,
	FRUIT, CREME_CARAMEL;
}
enum Coffee implements Food {
	BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
	LATTE, CAPPUCCINO, TEA, HERB_TEA;
}

public enum Meal6 {
	APPETIZER(Appetizer.class),
	MAINCOURSE(MainCourse.class),
	DESSERT(Dessert.class),
	COFFEE(Coffee.class);
	private Food[] values;
	private Meal6(Class<? extends Food> kind) {
		values = kind.getEnumConstants();
	}	
	public Food randomSelection() {
		return Enums.random(values);
	}
	public static void main(String[] args) {
		for(int i = 0; i < 5; i++) {
			for(Meal6 meal: Meal6.values()) {
				Food food = meal.randomSelection();
				System.out.println(food);
			}
			System.out.println("---");
		}
	}
}