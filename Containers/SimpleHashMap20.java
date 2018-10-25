// containers/SimpleHashMap20.java
// TIJ4 Chapter Containers, Exercise 20, page 851
// Modify SimpleHashMap so that it reports collisions, and test
// this by adding the same data twice so that you see collisions.
import java.util.*;
import net.mindview.util.*;

public class SimpleHashMap20<K,V> extends AbstractMap<K,V> {
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
			if(iPair.getKey().equals(key)) { // collision
				System.out.println("Collision: new " +
					pair + " for old " + iPair);
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
		SimpleHashMap20<String,String> m =
			new SimpleHashMap20<String,String>();
		m.putAll(Countries.capitals(10));
		System.out.println(m);
		m.put("EGYPT","Berlin?");
		m.put("EGYPT","Cairo");
		System.out.println(m);
		m.putAll(Countries.capitals(10));
	}
}