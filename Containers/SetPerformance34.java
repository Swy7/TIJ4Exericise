// containers/SetPerformance34.java
// TIJ4 Chapter Containers, Exercise 34, page 874
// Modify SetPerformance.java so that the Sets hold String objects instead 
// of Integers. Use a Generator from the Arrays chapter to create test values.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel)
* compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* December, 2007
*/

import java.util.*;
import net.mindview.util.*;

public class SetPerformance34 {
	static CountingGenerator.String cgs = new CountingGenerator.String(5);
	static CountingGenerator.String cgs2 = new CountingGenerator.String(5);
	static List<Test<Set<String>>> tests = new ArrayList<Test<Set<String>>>();
		static {
		tests.add(new Test<Set<String>>("add") {
			int test(Set<String> set, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for(int i = 0; i < loops; i++) {
					set.clear();
					for(int j = 0; j < size; j++) 
						set.add(cgs.next());
				}
				return loops * size;
			}
		});
		tests.add(new Test<Set<String>>("contains") {
			int test(Set<String> set, TestParam tp) {
				int loops = tp.loops;
				int span = tp.size * 2;
				for(int i = 0; i < loops; i++)
					for(int j = 0; j < span; j++)
						set.contains(cgs2.next());
				return loops * span;
			}
		});
		tests.add(new Test<Set<String>>("iterate") {
			int test(Set<String> set, TestParam tp) {
				int loops = tp.loops * 10;
				for(int i = 0; i < loops; i++) {
					Iterator<String> it = set.iterator();
					while(it.hasNext()) it.next();
				}
				return loops * set.size();
			}
		});
	}
	public static void main(String[] args) {
		if(args.length < 0)
			Tester.defaultParams = TestParam.array(args);
		Tester.fieldWidth = 10;
		Tester.run(new TreeSet<String>(), tests);
		Tester.run(new HashSet<String>(), tests);
		Tester.run(new LinkedHashSet<String>(), tests);	
	}
}	