// containers/MapPerformance35.java
// TIJ4 Chapter Containers, Exercise 35, page 877
// Modify MapPerformance.java to include tests of SlowMap.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel)
* compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* December, 2007
*/

import java.util.*;

public class MapPerformance35 {
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
			Tester.defaultParams = TestParam.array(10, 500, 100, 500, 500, 100);
		Tester.run(new SlowMap<Integer,Integer>(), tests);
		Tester.run(new TreeMap<Integer,Integer>(), tests);
		Tester.run(new HashMap<Integer,Integer>(), tests);
		Tester.run(new LinkedHashMap<Integer,Integer>(), tests);
		Tester.run(new IdentityHashMap<Integer,Integer>(), tests);
		Tester.run(new WeakHashMap<Integer,Integer>(), tests);
		Tester.run(new Hashtable<Integer,Integer>(), tests);
	}
}	