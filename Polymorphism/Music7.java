// polymorphism/music7/Music7.java
// TIJ4 Chapter Polymorphism, Exercise 7, page 289
/* Add a new Instrument to Music3.java and verify that polymorphism works for
* your new type.
*/
package polymorphism.music7;
import polymorphism.music.Note;
import static net.mindview.util.Print.*;

class Instrument {
	void play(Note n) { print("Instrument.play() " + n); }
	public String toString() { return "Instrument"; }
	void adjust() { print("Adjusting Instrument"); }
}

class Wind extends Instrument {
	void play(Note n) { print("Wind.play() " + n); }
	public String toString() { return "Wind"; }
	void adjust() { print("Adjusting Wind"); }
}

class Percussion extends Instrument {
	void play(Note n) { print("Percussion.play() " + n); }
	public String toString() { return "Percussion"; }
	void adjust() { print("Adjusting Percussion"); } 
}

class Stringed extends Instrument {
	void play(Note n) { print("Stringed.play() " + n); }
	public String toString() { return "Stringed"; }
	void adjust() { print("Adjusting Stringed"); } 
}

class Keyboard extends Instrument {
	void play(Note n) { print("Keyboard.play() " + n); }
	public String toString() { return "Keyboard"; }
	void adjust() { print("Adjusting Keyboard"); } 
}

class Brass extends Wind {
	void play(Note n) { print("Brass.play() " + n); }
	public String toString() { return "Brass"; }
	void adjust() { print("Adjusting Brass"); }
}

class Woodwind extends