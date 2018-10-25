// containers/SimpleHashMap39.java
// TIJ4 Chapter Containers, Exercise 39, page 879
/* Add a private rehash() method to SimpleHashMap that is invoked
* when the load factor exceeds 0.75. During rehashing, double the
* number of buckets, then search for the first prime number greater 
* than that to determine the new number of buckets.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* December, 2007
*/

import java.util.*;
import net.mindview.util.*;
import static net.mindview.util.Print.*;

// Helper class, to find first prime after an int:
class Prime {
	public static int firstPrimeAfter(int n) {
		for(int i = n + 1; i > n; i++) {
			int factors = 0;
			for(int j = 1; j < (i + 2)/2; j++) {
				if((i % j) == 0) factors++;
			}
			if(factors < 2) return i;
		}
		return 0;
	}
}

public class SimpleHashMap39<K,V> extends AbstractMap<K,V> {
	// Choose a prime number for the hash table
	// size, to achieve a uniform distribution:
	static final int SIZE = 997;
	// capacity field starts equal to SIZE
	private int capacity = SIZE;
	// You can't have a physical array of generics,
	// but you can upcast to one:
	@SuppressWarnings("unchecked")	
	LinkedList<MapEntry<K,V>>[] buckets = new LinkedList[SIZE];
	public int capacity() { return capacity; }
	// method to appropriately increase capacity, and
	// refill the new buckets with the old data:
	@SuppressWarnings("unchecked")
	private void rehash() {
		capacity = Prime.firstPrimeAfter(capacity * 2);
		LinkedList<MapEntry<K,V>>[] oldBuckets = buckets;		
		buckets = new LinkedList[capacity];
		for(LinkedList<MapEntry<K,V>> bucket : oldBuckets) {
			if(bucket == null) continue;
			for(MapEntry<K,V> mpair : bucket)
				this.put(mpair.getKey(), mpair.getValue());
		}			
	}
	public V put(K key, V value) {
		// Check and call rehash() if needed:
		if(this.size() > 0.75 * capacity) rehash();
		V oldValue = null;
		int index = Math.abs(key.hashCode()) % SIZE;
		if(buckets[index] == null)
			buckets[index] = new LinkedList<MapEntry<K,V>>();
		LinkedList<MapEntry<K,V>> bucket = buckets[index];
		MapEntry<K,V> pair = new MapEntry<K,V>(key, value);
		boolean found = false;
		ListIterator<MapEntry<K,V>> it = bucket.listIterator();
		while(it.hasNext()) {
			MapEntry<K,V> iPair = it.next();
			if(iPair.getKey().equals(key)) {
				oldValue = iPair.getValue();
				it.set(pair); // Replace old with new
				found = true;
				break;
			}
		}	
		if(!found)
			buckets[index].add(pair);
		return oldValue;
	}
	public V get(Object key) {
		int index = Math.abs(key.hashCode()) % SIZE;
		if(buckets[index] == null) return null;
		for(MapEntry<K,V> iPair : buckets[index])
			if(iPair.getKey().equals(key))
				return iPair.getValue();
		return null;
	}
	public Set<Map.Entry<K,V>> entrySet() {
		Set<Map.Entry<K,V>> set = new HashSet<Map.Entry<K,V>>();
		for(LinkedList<MapEntry<K,V>> bucket : buckets) {
			if(bucket == null) continue;
			for(MapEntry<K,V> mpair : bucket)
				set.add(mpair);
		}
		return set;
	}
	public static void main(String[] args) {
		RandomGenerator.Integer rgi = new RandomGenerator.Integer(10000);
		print("Testing map m of <String,String>: the basics:");
		SimpleHashMap39<String,String> m = new SimpleHashMap39<String,String>();
		m.putAll(Countries.capitals(10));
		print("m: " + m);
		print("m.get(\"CHAD\") " + m.get("CHAD"));
		print("m.size() = " + m.size());
		print("m.capacity() = " + m.capacity());
		print("Testing rehash() for a map m2 of <Integer,Integer>:");
		SimpleHashMap39<Integer,Integer> m2 = new SimpleHashMap39<Integer,Integer>();
		print("m2: " + m2);
		print("m2.size() = " + m2.size());
		print("m2.capacity() = " + m2.capacity());
		m2.putAll(MapData.map(rgi, rgi, 996));
		print("after m2.putAll(MapData.map(rgi, rgi, 996): ");
		print("m2.size() = " + m2.size());
		print("m2.capacity() = " + m2.capacity());
		m2.putAll(MapData.map(rgi, rgi, 2000));
		print("after m2.putAll(MapData.map(rgi, rgi, 2000): ");
		print("m2.size() = " + m2.size());
		print("m2.capacity() = " + m2.capacity());
		m2.putAll(MapData.map(rgi, rgi, 1500));
		print("after m2.putAll(MapData.map(rgi, rgi, 1500): ");
		print("m2.size() = " + m2.size());
		print("m2.capacity() = " + m2.capacity());
	}
}

