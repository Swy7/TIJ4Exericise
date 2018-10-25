// containers/MapPerformance37.java
// TIJ4 Chapter Containers, Exercise 37, page 878
/* Modify SimpleHashMap to use ArrayLists instead of LinkedLists.
* Modify MapPerformance.java to compare the performance of the two
* implementations.
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

class SimpleHashMap37a<K,V> extends AbstractMap<K,V> {
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
		SimpleHashMap37a<String,String> m =
			new SimpleHashMap37a<String,String>();
		m.putAll(Countries.capitals(25));
		System.out.println(m);
		System.out.println(m.get("ERITREA"));
		System.out.println(m.entrySet());
	}
}

class SimpleHashMap37b<K,V> extends AbstractMap<K,V> {
	// Choose a prime number for the hash table
	// size, to achieve a uniform distribution:
	static final int SIZE = 997;
	// You can't have a physical array of generics,
	// but you can upcast to one:
	@SuppressWarnings("unchecked")
	ArrayList<MapEntry<K,V>>[] buckets =
		new ArrayList[SIZE];
	public V put(K key, V value) {
		V oldValue = null;
		int index = Math.abs(key.hashCode()) % SIZE;
		if(buckets[index] == null)
			buckets[index] = new ArrayList<MapEntry<K,V>>();
		ArrayList<MapEntry<K,V>> bucket = buckets[index];
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
		for(ArrayList<MapEntry<K,V>> bucket : buckets) {
			if(bucket == null) continue;
			for(MapEntry<K,V> mpair : bucket)
				set.add(mpair);
		}
		return set;
	}
	public static void main(String[] args) {
		SimpleHashMap37b<String,String> m =
			new SimpleHashMap37b<String,String>();
		m.putAll(Countries.capitals(25));
		System.out.println(m);
		System.out.println(m.get("ERITREA"));
		System.out.println(m.entrySet());
	}
}

public class MapPerformance37 {
	static List<Test<Map<Integer,Integer>>> tests = new ArrayList<Test<Map<Integer,Integer>>>();
	static {
		tests.add(new Test<Map<Integer,Integer>>("put") {
			int test(Map<Integer,Integer> map, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for(int i = 0; i < loops; i++) {
					map.clear();
					for(int j = 0; j < size; j++) 
						map.put(j, j);
				}
				return loops * size;
			}
		});
		tests.add(new Test<Map<Integer,Integer>>("get") {
			int test(Map<Integer,Integer> map, TestParam tp) {
				int loops = tp.loops;
				int span = tp.size * 2;
				for(int i = 0; i < loops; i++)
					for(int j = 0; j < span; j++)
						map.get(j);
				return loops * span;
			}
		});
		tests.add(new Test<Map<Integer,Integer>>("iterate") {
			int test(Map<Integer,Integer> map, TestParam tp) {
				int loops = tp.loops * 10;
				for(int i = 0; i < loops; i++) {
					Iterator it = map.entrySet().iterator();
					while(it.hasNext()) it.next();
				}
				return loops * map.size();
			}
		});
	}
	public static void main(String[] args) {
		if(args.length > 0)
			Tester.defaultParams = TestParam.array(args);
		else
			Tester.defaultParams = TestParam.array(10, 1000, 100, 1000, 1000, 100);
		Tester.run(new SimpleHashMap37a<Integer,Integer>(), tests);
		Tester.run(new SimpleHashMap37b<Integer,Integer>(), tests);
		Tester.run(new TreeMap<Integer,Integer>(), tests);
		Tester.run(new HashMap<Integer,Integer>(), tests);
		Tester.run(new LinkedHashMap<Integer,Integer>(), tests);
		Tester.run(new IdentityHashMap<Integer,Integer>(), tests);
		Tester.run(new WeakHashMap<Integer,Integer>(), tests);
		Tester.run(new Hashtable<Integer,Integer>(), tests);	
	}
}	