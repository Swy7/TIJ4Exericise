// containers/MapPerformance36.java
// TIJ4 Chapter Containers, Exercise 36, page 878
/* Modify SlowMap so that instead of two ArrayLists, it holds a single
* ArrayList of MapEntry objects. Verify that the modified version works 
* correctly. Using MapPerformance.java, test the speed of your new Map.
* Now change the put() method so that if performs a sort() after each 
* pair is entered, and modify get() to use Collections.binarySearch() 
* to look up the key. Compare the performance of the new version with 
* the old ones.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel)
* compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* December, 2007
*/

import java.util.*;
import net.mindview.util.*;
import static net.mindview.util.Print.*;

// SlowMap with one ArrayList<MapEntry<K,V>> and
// original put() and get() methods:
class SlowMap36a<K,V> extends AbstractMap<K,V> { 
	protected List<MapEntry<K,V>> entryList = new ArrayList<MapEntry<K,V>>();
	private EntrySet entries = new EntrySet();
	public Set<Map.Entry<K,V>> entrySet() { return entries; }
	private class EntrySet extends AbstractSet<Map.Entry<K,V>> {
		public int size() { return entryList.size(); }
		public Iterator<Map.Entry<K,V>> iterator() {
			return new Iterator<Map.Entry<K,V>>() {
				private int index = -1;
				public boolean hasNext() {
					return index < entryList.size() - 1; 
				}
				@SuppressWarnings("unchecked")
				public Map.Entry<K,V> next() {
					int i = ++index;
					return entryList.get(i);	 
				}
				public void remove() {
					entryList.remove(index--);		
				}				
			};
		}				
	}	
	public V put(K key, V value) {
		V oldValue = get(key); // The old value or null
		Iterator<MapEntry<K,V>> it = entryList.iterator();
		while(it.hasNext()) {
			MapEntry<K,V> me = it.next();
			if(me.getKey().equals(key))
				me.setValue(value);
		}
		entryList.add(new MapEntry<K,V>(key, value));		
		return oldValue;
	}
	public V get(Object key) { // key is type Object, not K
		Iterator<MapEntry<K,V>> it = entryList.iterator();
		while(it.hasNext()) {
			MapEntry<K,V> me = it.next();
			if(me.getKey().equals(key))
				return me.getValue(); 
		}
		return null;
	}	
}
// SlowMap with modified put() and get() methods:
class SlowMap36b<K,V> extends SlowMap36a<K,V> { 
	// To allow search for key via Collections.binarySearch(List<T>,T,Comparator):
	private List<MapEntry<Integer,V>> hashEntryList = new ArrayList<MapEntry<Integer,V>>();	
	public List<MapEntry<Integer,V>> hashEntryList() { return hashEntryList; }
	// To allow sorting:
	private final MapEntryKeyComp<K,V> comp1 = new MapEntryKeyComp<K,V>();
	private final MapEntryKeyComp<Integer,V> comp2 = new MapEntryKeyComp<Integer,V>();
	// Very slow put() with 2 sorts:
	@Override
	public V put(K key, V value) {
		V oldValue = get(key); // The old value or null
		Iterator<MapEntry<K,V>> it = entryList.iterator();
		while(it.hasNext()) {
			MapEntry<K,V> me = it.next();
			if(me.getKey().equals(key)) {
				me.setValue(value);
			}
		}
		entryList.add(new MapEntry<K,V>(key, value));
		hashEntryList.add(new MapEntry<Integer,V>(key.hashCode(), value));
		// Sort (uses hashCode()) after adding:
		Collections.sort(entryList, comp1);
		Collections.sort(hashEntryList, comp2);
		return oldValue;
	}
	// Faster get() with sorting done in put:
	@Override
	public V get(Object key) { // key is type Object, not K
		// Same key, same hashCode via MapEntryKeyComp<K,V>:
		MapEntry<Integer,V> sub = new MapEntry<Integer,V>(key.hashCode(), null); 
		int i = Collections.binarySearch(hashEntryList, sub, comp2);
		if(i >= 0) return hashEntryList.get(i).getValue();
		return null;		
	}	
}
// Sort by key hashCode():
class MapEntryKeyComp<K,V> implements Comparator<MapEntry<K,V>> {
	public int compare(MapEntry<K,V> me1, MapEntry<K,V> me2) {
		return me1.getKey().hashCode() - me2.getKey().hashCode();
	}
} 
public class MapPerformance36 {
	static List<Test<Map<Integer,Integer>>> tests = 
		new ArrayList<Test<Map<Integer,Integer>>>();
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
			Tester.defaultParams = TestParam.array(10, 100, 50, 50, 100, 20);
		print("Tesing SlowMap36a:");
		SlowMap36a<String,String> m = new SlowMap36a<String,String>();
		m.putAll(Countries.capitals(15));
		print("m: " + m);
		print("m.get(\"BURUNDI\"): " + m.get("BURUNDI"));
		print("m.entrySet(): " + m.entrySet());
		print("m.keySet(): " + m.keySet());		
		print("m.values() = " + m.values());
		print("Two different maps: ");
		SlowMap36a<String,String> m2 = new SlowMap36a<String,String>();
		print("m.equals(m2): " + m.equals(m2)); 
		m2.putAll(Countries.capitals(15));
		print("Maps with same entries: ");
		print("m.equals(m2): " + m.equals(m2));
		m.clear();
		print("After m.clear(), m.isEmpty(): " + 
			m.isEmpty() + ", m = " + m);	
		m2.keySet().clear();
		print("After m2.keySet().clear(), m2.isEmpty(): " 
			+ m2.isEmpty() + ", m2 = " + m2);
		print();
		print("Testing SlowMap36b:");
		SlowMap36b<String,String> m3 = new SlowMap36b<String,String>();
		m3.putAll(Countries.capitals(15));
		print("m3: " + m3);
		print("m3.get(\"BURUNDI\"): " + m3.get("BURUNDI"));
		print("m3.entrySet(): " + m3.entrySet());
		print("m3.hashEntryList(): " + m3.hashEntryList());
		m3.clear();
		print("After m3.clear(), m3.isEmpty(): " + 
			m3.isEmpty() + ", m3 = " + m3);	
		m3.keySet().clear();
		print("After m3.keySet().clear(), m3.isEmpty(): " 
			+ m3.isEmpty() + ", m3 = " + m3);
		print();
		print("Comparative time tests:");		
		Tester.run(new SlowMap<Integer,Integer>(), tests);
		Tester.run(new SlowMap17<Integer,Integer>(), tests);
		Tester.run(new SlowMap36a<Integer,Integer>(), tests);
		Tester.run(new SlowMap36b<Integer,Integer>(), tests);
		Tester.run(new HashMap<Integer,Integer>(), tests);
		Tester.run(new TreeMap<Integer,Integer>(), tests);		
		Tester.run(new LinkedHashMap<Integer,Integer>(), tests);
		Tester.run(new IdentityHashMap<Integer,Integer>(), tests);
		Tester.run(new WeakHashMap<Integer,Integer>(), tests);
		Tester.run(new Hashtable<Integer,Integer>(), tests);			
	}
}	