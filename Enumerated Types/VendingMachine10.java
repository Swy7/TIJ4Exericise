// enumerated/VendingMachine10.java
// {Arg: VendingMachine10aInput.txt}
// TIJ4 Chapter Enumerated, Exercise 10, page 1047
// Modify class VendingMachine (only) using EnumMap so that one 
// program can have multiple instances of VendingMachine.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2009
*/

// Files required for input, in same package:
// VendingMachine10aInput.txt: 
// QUARTER;QUARTER;QUARTER;CHIPS;
// DOLLAR; DOLLAR; TOOTHPASTE;
// QUARTER; DIME; SODA;
// QUARTER; DIME; NICKEL; SODA;
// ABORT_TRANSACTION;
// STOP;
// VendingMachine10bInput.txt:
// DOLLAR;CHIPS;
// QUARTER;DIME;TOOTHPASTE;
// STOP;
// VendingMachine10cInput.txt
// DOLLAR;DOLLAR;DOLLAR;CHIPS;
// QUARTER;QUATER;SODA;
// ABORT_TRANSACTION;
// STOP;

package enumerated;
import static enumerated.Input.*;
import java.util.*;
import net.mindview.util.*;
import static net.mindview.util.Print.*;

enum Category {
	MONEY(NICKEL, DIME, QUARTER, DOLLAR),
	ITEM_SELECTION(TOOTHPASTE, CHIPS, SODA, SOAP),
	QUIT_TRANSACTION(ABORT_TRANSACTION),
	SHUT_DOWN(STOP);
	private Input[] values;
	Category(Input... types) { values = types; }
	private static EnumMap<Input,Category> categories =
		new EnumMap<Input,Category>(Input.class);
	static {
		for(Category c : Category.class.getEnumConstants())
			for(Input type : c.values)
				categories.put(type, c);
	}	
	public static Category categorize(Input input) {
		return categories.get(input);
	}
}

interface Command {  // In order to use a Command Design Pattern
	void next(Input input);
	void next();
}

enum State {
	RESTING,
	ADDING_MONEY,
	DISPENSING,
	GIVING_CHANGE,	
	TERMINAL
}

class VendingMachine10 {
	int id = ++count;
	static int count = 0; 
	State state = State.RESTING;
	int amount = 0; // for each transaction
	int banked = 0; // retained after transactions
	Input input = null;
	Input selection = null;
	boolean isTransient = false;
	// Enums must be static, sot use classes instead:
	class RestingDo implements Command {
		public void next(Input in) {
			isTransient = false;
			input = in;
			switch(Category.categorize(in)) {
				case MONEY:
					amount += in.amount();
					state = State.ADDING_MONEY;
					break;
				case SHUT_DOWN:
					state = State.TERMINAL;
				default:					
			}
		}
		public void next() { 
			isTransient = false; 			
		}
	}
	class AddingMoneyDo implements Command {
		public void next(Input input) {
			isTransient = false;
			switch(Category.categorize(input)) {
				case MONEY:
					amount += input.amount();
					break;
				case ITEM_SELECTION:
					selection = input;
					if(amount < selection.amount()) {
						print("Insufficient money for " + selection);							}
					else {
						state = State.DISPENSING;
						isTransient = true;
					}
					break;
				case QUIT_TRANSACTION:
					state = State.GIVING_CHANGE;
					isTransient = true;
					break;
				case SHUT_DOWN:
					state = State.TERMINAL;
					banked = banked += amount;					
				default:
			}
		}
		public void next() { isTransient = false; }
	}
	class DispensingDo implements Command {
		public void next() {
			isTransient = true; 
			print("Here is your " + selection);
			state = State.GIVING_CHANGE;
		}
		public void next(Input input) { 
			isTransient = true; 
			print("Here is your " + selection);
			state = State.GIVING_CHANGE; 	
		}
	}
	class GivingChangeDo implements Command {
		public void next(Input input) { 
			isTransient = true; 
			if(amount > selection.amount()) {
				print("Your change: " + (amount - selection.amount()));					
			}
			banked = banked += selection.amount();
			amount = 0; // reset
			state = State.RESTING;					 
		}
		public void next() {
			isTransient = true; 
			if(amount < selection.amount())
				print("Returning your: " + amount); 
			if(amount > selection.amount()) {
				print("Your change: " + (amount - selection.amount()));
				banked = banked += selection.amount();					
			}
			if(amount == selection.amount())
				banked = banked += selection.amount();
			amount = 0;
			state = State.RESTING;
		}
	}
	class TerminalDo implements Command { 
		public void next(Input input) {
			print("state TERMINAL");
			isTransient = false;
		}
		public void next() {
			print("state TERMINAL");
			isTransient = false;			
		}		
	}
	Map<State,Command> em = 
		Collections.synchronizedMap(new EnumMap<State,Command>(State.class));
	VendingMachine10() { // Load up the EnumMap in the constructor
		print("VendingMachine10()#" + id); 	
		em.put(State.RESTING, new RestingDo());
		em.put(State.ADDING_MONEY, new AddingMoneyDo());
		em.put(State.DISPENSING, new DispensingDo());
		em.put(State.GIVING_CHANGE, new GivingChangeDo());
		em.put(State.TERMINAL, new TerminalDo());
	}
	void showAmount() { print("amount = " + amount); }
	void showBanked() { print("banked = " + banked); }
	public static void main(String[] args) {		
		Generator<Input> gen = new RandomInputGenerator();
		if(args.length == 1)
			gen = new FileInputGenerator10(args[0]);
		VendingMachine10 vm10a = new VendingMachine10();
		VendingMachine10 vm10b = new VendingMachine10();	
		VendingMachine10 vm10c = new VendingMachine10();
		print();
		print("Testing VendingMachine 10a:");
		while(vm10a.state != State.TERMINAL) {
			Input in = gen.next();
			(vm10a.em.get(vm10a.state)).next(in);
			while(vm10a.isTransient) {
				(vm10a.em.get(vm10a.state)).next();				
			}	
			vm10a.showAmount();							
		}
		vm10a.showBanked();
		print();
		print("Testing VendingMachine 10b:");
		gen = new FileInputGenerator10("VendingMachine10bInput.txt");
		while(vm10b.state != State.TERMINAL) {
			Input in = gen.next();
			(vm10b.em.get(vm10b.state)).next(in);
			while(vm10b.isTransient) {
				(vm10b.em.get(vm10b.state)).next();				
			}	
			vm10b.showAmount();							
		}
		print();
		print("Testing VendingMachine 10c:");
		gen = new FileInputGenerator10("VendingMachine10cInput.txt");
		while(vm10c.state != State.TERMINAL) {
			Input in = gen.next();
			(vm10c.em.get(vm10c.state)).next(in);
			while(vm10c.isTransient) {
				(vm10c.em.get(vm10c.state)).next();				
			}	
			vm10c.showAmount();							
		}
	}
}

// Create inputs from a file of ';'-separated strings;
class FileInputGenerator10 implements Generator<Input> {
	private Iterator<String> input;
	public FileInputGenerator10(String fileName) {
		input = new TextFile(fileName, ";").iterator();
	}
	public Input next() {
		if(input.hasNext()) {
			return Enum.valueOf(Input.class, input.next().trim());
		}
		return null;
	}	
}


