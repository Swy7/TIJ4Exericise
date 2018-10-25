// enumerated/VendingMachine11.java
// TIJ4 Chapter Enumerated, Exercise 11, page 1047
/* In a real vending machine you will want to easily add and change
* the type of vended items, so the limits imposed by an enum on
* Input are impractical (remember that enums are for a restricted set 
* of types). Modify VendingMachine.java so that the vended items are
* represented by a class instead of being part of Input, and initialize
* an ArrayList of these objects from a text file 
* (using net.mindview.util.TextFile).
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2009
*/

/** Files required for input, in same package:
VendingMachineInput.txt
QUARTER;QUARTER;QUARTER;CHIPS;
DOLLAR; DOLLAR; TOOTHPASTE;
QUARTER; DIME; SODA;
QUARTER; DIME; NICKEL; SODA;
ABORT_TRANSACTION;
STOP;
VendingMachine11Input.txt:
Money:Nickel(5),Dime(10),Quarter(25),Dollar(100);Selection:Soda(100),Juice(125),
HotChocolate(100),Coffee(145),CandyBar(90);VendingEvent:AbortTransaction,Stop, 
**/

package enumerated;
import java.util.*;
import net.mindview.util.*;
import static enumerated.Input.*;
import static net.mindview.util.Print.*;

// Vending elements as classes implementing common interface:
interface VendingInput { String name(); } 

class MonetaryUnit implements VendingInput {
	private String name;
	private int amount = 0;
	MonetaryUnit(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}
	public String name() { return name; }
	public int amount() { return amount; }
}

class VendedItem implements VendingInput {
	private String name;
	private int price = 0;
	VendedItem(String name, int price) {
		this.name = name;
		this.price = price;
	}
	public String name() { return name; }
	public int price() { return price; }
} 

class VendingEvent implements VendingInput {
	private String name;
	VendingEvent(String name) {
		this.name = name;
	}
	public String name() { return name; }	
}

public class VendingMachine11 {
	private static State state = State.RESTING;
	private static int amount = 0;
	private static VendedItem selection = null;
	enum StateDuration { TRANSIENT } // Tagging enum
	enum State {
		RESTING {
			void next(VendingInput in) {
				if(MonetaryUnit.class.isInstance(in)) {
					amount += ((MonetaryUnit)in).amount();
					state = ADDING_MONEY;	
				}
				if(VendingEvent.class.isInstance(in)) {
					if(((VendingEvent)in).name().equals("Stop"))
						state = TERMINAL;
				}
			}
		},
		ADDING_MONEY {
			void next(VendingInput in) {
				if(MonetaryUnit.class.isInstance(in)) {
					amount += ((MonetaryUnit)in).amount();
				}				
				if(VendedItem.class.isInstance(in)) {
					selection = (VendedItem)in;
					if(amount < selection.price())
						print("Insufficient money for " + selection.name());
					else state = DISPENSING;
				}
				if(VendingEvent.class.isInstance(in)) {
					if(((VendingEvent)in).name().equals("AbortTransaction"))
						state = GIVING_CHANGE;
					if(((VendingEvent)in).name().equals("Stop"))
						state = TERMINAL;
				}				
			}
		},
		DISPENSING(StateDuration.TRANSIENT) {
			void next() {
				print("here is your " + selection.name());
				amount -= selection.price();
				state = GIVING_CHANGE;
			}
		},
		GIVING_CHANGE(StateDuration.TRANSIENT) {
			void next() {
				if(amount > 0) {
					print("Your change: " + amount);
					amount = 0;
				}
				state = RESTING;
			}
		},
		TERMINAL { void output() { print("Halted"); } };
		private boolean isTransient = false;
		State()	{} // no-arg constructor
		State(StateDuration trans) { isTransient = true; }
		void next(VendingInput in) {
			throw new RuntimeException("Only call " +
				"next(VendingInput in) for non-transient states");
		}
		void next() {	
			throw new RuntimeException("Only call next() for " +
				"StateDuration.TRANSIENT states");
		}
		void output() { print(amount); }
	}
	static void run(Generator<VendingInput> gen) {
		while(state != State.TERMINAL) {		
			state.next(((FileInputGenerator11)gen).next());
			while(state.isTransient) 
				state.next();
			state.output();					
		}
		state = State.RESTING;
		print();
	}
	static void runRandom(Generator<VendingInput> gen) {
		while(state != State.TERMINAL) {		
			state.next(((FileInputGenerator11)gen).randomNext());
			while(state.isTransient) 
				state.next();
			state.output();					
		}
		state = State.RESTING;
		print();				
	}
	static void runTextExample(Generator<VendingInput> gen) {
		while(state != State.TERMINAL) {	
		// for(int i = 0; i < 16; i++) {	
			state.next(((FileInputGenerator11)gen).textExampleNext("VendingMachineInput.txt"));
			while(state.isTransient) 
				state.next();
			state.output();					
		}
		state = State.RESTING;
		print();				
	}
	public static void main(String[] args) {
		FileInputGenerator11 gen = 
			new FileInputGenerator11("VendingMachine11Input.txt");
		runRandom(gen); // random vending inputs 
		run(gen); 
		runTextExample(gen); // inputs from VendingMachineInput.txt
	}	
}

class FileInputGenerator11 implements Generator<VendingInput> {
	private ArrayList<String> list;
	private List<VendingInput> vendList = new ArrayList<VendingInput>();
	private Iterator<VendingInput> it;
	private Random rand = new Random();
	private Iterator<String> input = new TextFile("VendingMachineInput.txt", ";").iterator();
	// Construct List of VendingInput from appropriately formatted txt file:
	public FileInputGenerator11(String fileName) { 
		list = new TextFile(fileName, ",|;|:");
		int m = list.indexOf("Money");
		int se = list.indexOf("Selection");
		int e = list.indexOf("VendingEvent");
		VendingInput vIn;
		for(String s : list) {
				int x = list.indexOf(s);
				if(m < x && x < se) {
					String[] sa = s.split("\\(|\\)");
					vIn = new MonetaryUnit(sa[0], Integer.parseInt(sa[1]));
					vendList.add(vIn);
				}
				else if(se < x && x < e) {
					String[] sa = s.split("\\(|\\)");
					vIn = new VendedItem(sa[0], Integer.parseInt(sa[1]));
					vendList.add(vIn);
				}
				else if(e < x) {
					vIn = new VendingEvent(s);
					vendList.add(vIn);
				}			
		}
		it = vendList.iterator();		
	}
	public VendingInput next() {
		if(list.isEmpty()) return null; 		
		if(it.hasNext()) {
			return it.next();			
		}
		return null;
	}
	public VendingInput randomNext() { 
		return vendList.get(rand.nextInt(vendList.size()));
	}
	public VendingInput textExampleNext(String fileName) {		
		if(!input.hasNext()) return null;
		String s = input.next().trim();
		String s1 = s.charAt(0) + s.substring(1).toLowerCase();
		for(int i = 0; i < this.vendList.size(); i++) {
			if(vendList.get(i).name().equals(s1)) { 
				return vendList.get(i);			
			}
		}
		return null;
	}	
}