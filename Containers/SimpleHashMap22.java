// containers/SimpleHashMap22.java
// TIJ4 Chapter Containers, Exercise 22, page 851
// Implement the clear() and remove() methods for SimpleHashMap.

/* Compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* November, 2007
*/

import java.util.*;
import net.mindview.util.*;
import static org.greggordon.tools.Print.*; // at www.greggordon.org

public class SimpleHashMap22<K,V> extends AbstractMap<K,V> {
	// Choose a prime number for the hash table
	// size, to achieve a uniform distribution:
	static final int SIZE = 997;
	// You can't have a physical array of generics,
	// but you can upcast to one:
	@SuppressWarnings("unchecked")
	LinkedList<MapEntry<K,V>>[] buckets =
		new LinkedList[SIZE];
	public V put(K key, V value) {
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
	public void clear() {
		for(LinkedList<MapEntry<K,V>> bucket : buckets)
			if(bucket != null) bucket.clear();		
	}
	public V remove(Object o) {
		V v = null;
		if(this.get(o) != null) {
			int index = Math.abs(o.hashCode()) % SIZE;
			for(MapEntry<K,V> iPair : buckets[index])
				if(iPair.getKey().equals(o)) {
					v = iPair.getValue();
					int i =
					buckets[index].indexOf(iPair);
					buckets[index].remove(i);
					break;		
				}
		}
		return v;		
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
		SimpleHashMap22<String,String> m =
			new SimpleHashMap22<String,String>();
		m.putAll(Countries.capitals(5));
		println(m);
		println(m.get("ALGERIA"));
		println(m.remove("ALGERIA"));
		println(m.get("ALGERIA"));
		println(m.remove("ANGOLA"));
		println(m);	
		m.clear();
		println(m);			
	}
}