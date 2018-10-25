// containers/Tuple28Test.java
// TIJ4 Chapter Containers, Exercise 28, page 858
/* Modify net/mindview/util/Tuple.java to make it a 
* general-purpose class by adding hashCode(), equals(), 
* and implementing Comparable for each type of Tuple.
*/

/* This is my solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* December, 2007
*/

import java.util.*;
import static net.mindview.util.Print.*;

class TwoTuple28<A,B> implements Comparable {
  	public final A first;
  	public final B second;
  	public TwoTuple28(A a, B b) { first = a; second = b; }
	public int hashCode() {
		int result = 17;
		result = result * 37 + first.hashCode();
		result = result * 37 + second.hashCode();
		return result;
	}
	public boolean equals(Object o) {
		return o instanceof TwoTuple28 &&
		((TwoTuple28)o).first.equals(first) &&
		((TwoTuple28)o).second.equals(second); 
	}
	public int compareTo(Object o) {
		if(!(o instanceof TwoTuple28)) throw new ClassCastException();
		TwoTuple28 t = (TwoTuple28)o;
		return (this.hashCode() - t.hashCode() < 0) ? -1 : 
		((this.hashCode() - t.hashCode() > 0 ? 1 : 0));
	}
  	public String toString() {
  	  return "(" + first + ", " + second + ")";
  	}
} 

class ThreeTuple28<A,B,C> extends TwoTuple28<A,B> 
implements Comparable {
  	public final C third;
  	public ThreeTuple28(A a, B b, C c) {
  		super(a, b);
    		third = c;
  	}
	public int hashCode() {
		int result = super.hashCode();
		result = result * 37 + third.hashCode();
		return result;
	}
	public boolean equals(Object o) {
		return o instanceof ThreeTuple28 &&
		((ThreeTuple28)o).first.equals(first) &&
		((ThreeTuple28)o).second.equals(second) &&
		((ThreeTuple28)o).third.equals(third); 
	}
	public int compareTo(Object o) {
		if(!(o instanceof ThreeTuple28)) throw new ClassCastException();
		ThreeTuple28 t = (ThreeTuple28)o;
		return (this.hashCode() - t.hashCode() < 0) ? -1 : 
		((this.hashCode() - t.hashCode() > 0 ? 1 : 0));
	}
  	public String toString() {
  		return "(" + first + ", " + second + ", " + third +")";
  	}
} 

class FourTuple28<A,B,C,D> extends ThreeTuple28<A,B,C> 
implements Comparable {
  	public final D fourth;
  	public FourTuple28(A a, B b, C c, D d) {
    		super(a, b, c);
    		fourth = d;
  	}
	public int hashCode() {
		int result = super.hashCode();
		result = result * 37 + fourth.hashCode();
		return result;
	}
	public boolean equals(Object o) {
		return o instanceof FourTuple28 &&
		((FourTuple28)o).first.equals(first) &&
		((FourTuple28)o).second.equals(second) &&
		((FourTuple28)o).third.equals(third) &&
		((FourTuple28)o).fourth.equals(fourth);
	}
	public int compareTo(Object o) {
		if(!(o instanceof FourTuple28)) throw new ClassCastException();
		FourTuple28 t = (FourTuple28)o;
		return (this.hashCode() - t.hashCode() < 0) ? -1 : 
		((this.hashCode() - t.hashCode() > 0 ? 1 : 0));
	}
  	public String toString() {
    		return "(" + first + ", " + second + ", " +
      		third + ", " + fourth + ")";
  	}
} 

class FiveTuple28<A,B,C,D,E>
extends FourTuple28<A,B,C,D> 
implements Comparable {
  	public final E fifth;
  	public FiveTuple28(A a, B b, C c, D d, E e) {
    		super(a, b, c, d);
    		fifth = e;
  	}
	public int hashCode() {
		int result = super.hashCode();
		result = result * 37 + fifth.hashCode();
		return result;
	}
	public boolean equals(Object o) {
		return o instanceof FiveTuple28 &&
		((FiveTuple28)o).first.equals(first) &&
		((FiveTuple28)o).second.equals(second) &&
		((FiveTuple28)o).third.equals(third) &&
		((FiveTuple28)o).fourth.equals(fourth) &&
		((FiveTuple28)o).fifth.equals(fifth);
	}
	public int compareTo(Object o) {
		if(!(o instanceof FiveTuple28)) throw new ClassCastException();
		FiveTuple28 t = (FiveTuple28)o;
		return (this.hashCode() - t.hashCode() < 0) ? -1 : 
		((this.hashCode() - t.hashCode() > 0 ? 1 : 0));
	}
  	public String toString() {
    		return "(" + first + ", " + second + ", " +
      		third + ", " + fourth + ", " + fifth + ")";
  	}
} 

class Tuple28 {
  	public static <A,B> TwoTuple28<A,B> tuple(A a, B b) {
    		return new TwoTuple28<A,B>(a, b);
  	}
  	public static <A,B,C> ThreeTuple28<A,B,C>
  	tuple28(A a, B b, C c) {
    		return new ThreeTuple28<A,B,C>(a, b, c);
  	}
  	public static <A,B,C,D> FourTuple28<A,B,C,D>
  	tuple(A a, B b, C c, D d) {
    		return new FourTuple28<A,B,C,D>(a, b, c, d);
  	}
  	public static <A,B,C,D,E>
  	FiveTuple28<A,B,C,D,E> tuple(A a, B b, C c, D d, E e) {
    		return new FiveTuple28<A,B,C,D,E>(a, b, c, d, e);
  	}
} 

public class Tuple28Test {
	public static void main(String[] args) {
		Tuple28 t = new Tuple28();
		FiveTuple28 t1b = t.tuple(1,1,1,1,1);
		FiveTuple28 t2b = t.tuple(1,1,1,2,1);
		FiveTuple28 t3b = t.tuple(1,1,1,1,1);
		FiveTuple28 t4b = t.tuple(1,1,1,1,0);
		print(t1b.compareTo(t1b));
		print(t1b.compareTo(t2b));
		print(t1b.compareTo(t3b));
		print(t1b.compareTo(t4b));
		List<FiveTuple28> list = 
			new ArrayList<FiveTuple28>(Arrays.asList(t1b,t2b,t3b,t4b));
		Set<FiveTuple28> s = new TreeSet<FiveTuple28>();
		s.addAll(list);
		print(s);			
	}
}
