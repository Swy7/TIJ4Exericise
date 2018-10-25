// enumerated/VowelsAndConsonants5.java
// TIJ4 Chapter Enumerated, Exercise 5, page 1027
/* Modify contol/VowelsAndConsonants.java so that it uses three enum types:
* VOWEL, SOMETIMES_A_VOWEL, and, CONSONANT. The enum constructor should
* take the various letters that describe that particular category. Hint: 
* use varargs, and remember that varargs automatically creates an array
* for you.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* June, 2008
*/

import java.util.*;
import net.mindview.util.*;
import static net.mindview.util.Print.*;

public enum VowelsAndConsonants5 {
	VOWEL('a', 'e', 'i', 'o', 'u'),
	SOMETIMES_A_VOWEL('w', 'y'),
	CONSONANT('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
		'n', 'p', 'q', 'r', 's', 't', 'v', 'x', 'z');
	private Character[] letters;
	private VowelsAndConsonants5(Character... letters) {
		this.letters = letters;
	}
	public static VowelsAndConsonants5 LetterType(Character c) {
		if(Arrays.asList(VOWEL.letters).contains(c)) return VOWEL;
		if(Arrays.asList(SOMETIMES_A_VOWEL.letters).contains(c)) 
			return SOMETIMES_A_VOWEL;
		return CONSONANT;
	}
	public static void main(String[] args) {
		Random rand = new Random();
		for(int i = 0; i < 100; i++) {
			int c = rand.nextInt(26) + 'a';
			printnb((char)c + ", " + c + ": "); 
			print(LetterType((char)c));			
		}		
	}
}