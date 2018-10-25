// containers/HashMaps38.java
// TIJ4 Chapter Containers, Exercise 38, page 879
/* Look up the HashMap class in the JDK documentation. Create a
* HashMap, fill it with elements, and determine the load factor.
* Test the lookup speed with this map, then attempt to increase
* the speed by making a new HashMap with a larger initial capacity
* and copying the old map into the new one, then run your lookup
* speed test again on the new map.
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
import static org.greggordon.tools.Print.*;

public class HashMaps38 {
	static List<Test<Map<Integer,String>>> tests = 
		new ArrayList<Test<Map<Integer,String>>>();
	static CountingGenerator.String cgs = new CountingGenerator.String(5);
	static {
		tests.add(new Test<Map<Integer,String>>("put") {
			int test(Map<Integer,String> map, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for(int i = 0; i < loops; i++) {
					map.clear();
					for(int j = 0; j < size; j++) 
						map.put(j, cgs.next());
				}
				return loops * size;
			}
		});
		tests.add(new Test<Map<Integer,String>>("get") {
			int test(Map<Integer,String> map, TestParam tp) {
				int loops = tp.loops;
				int span = tp.size * 2;
				for(int i = 0; i < loops; i++)
					for(int j = 0; j < span; j++)
						map.get(j);
				return loops * span;
			}
		});
		tests.add(new Test<Map<Integer,String>>("iterate") {
			int test(Map<Integer,String> map, TestParam tp) {
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
		HashMap<Integer,String> map1 = new HashMap<Integer,String>();		
		println("map1: " + map1);
		map1.putAll(new CountingMapData(16));
		println("map1: " + map1);	
		HashMap<Integer,String> map2 = new HashMap<Integer,String>(64);
		println("map2: " + map2);
		map2.putAll(map1);
		println("map2: " + map2);
		HashMap<Integer,String> map3 = new HashMap<Integer,String>(1028);
		println("map3: " + map3);
		map2.putAll(map1);
		println("map3: " + map3);
		if(args.length > 0)
			Tester.defaultParams = TestParam.array(args);
		else
			Tester.defaultParams = TestParam.array(10, 1000, 100, 1000, 1000, 1000);
		println("Comparative time tests:");		
		Tester.run(map1, tests);
		Tester.run(map2, tests);
		Tester.run(map3, tests);
	}
} 