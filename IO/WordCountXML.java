// xml/WordCountXML.java
// TIJ4 Chapter IO, Exercise 32, page 1006
/* Using a Map<String,Integer> and the net.mindview.util.TextFile utility, write
* a program that counts the number of words in a file (use "//W+" as the second
* argument to the TextFile constructor). Store the results as an XML file.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2008
*/

import net.mindview.util.*;	
import java.util.*;
import nu.xom.*;
import java.io.*;

public class WordCountXML {
	public static void format(OutputStream os, Document doc) 
	throws Exception {
		Serializer serializer = new Serializer(os, "ISO-8859-1");
		serializer.setIndent(4);
		serializer.setMaxLength(64);
		serializer.write(doc);
		serializer.flush();
	}
	public static void main(String[] args) {
		// File whose words are to be counted:
		String fileName = "WordCountXML.java";
		// Set of unique words in file:
		Set<String> uniqueWords = 
			new TreeSet<String>(new TextFile(fileName, "\\W+"));
		// List of all words in file:
		ArrayList<String> allWords = new TextFile(fileName, "\\W+");
		Map<String,Integer> wordCount = new TreeMap<String,Integer>();	
		// Count appearances of each unique word and add to map:
		for(String s : uniqueWords) {
			int count = 0;
			for(String t : allWords) {
				if(t.equals(s)) count++;
			}
			wordCount.put(s, count);
		}
		Element root = new Element("words"); // XML root
		// Add map data to xml root:
		Iterator it = wordCount.entrySet().iterator();
		while(it.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String,Integer> me = 
				(Map.Entry<String,Integer>)it.next();
			Element word = new Element("word");
			word.appendChild(me.getKey() + ": ");
			word.appendChild(Integer.toString(me.getValue()));
			root.appendChild(word);
		}		
		Document doc = new Document(root);
		// Send output to console, and to new file WordCount.xml: 
		try {		
			format(System.out, doc);
			format(new BufferedOutputStream(
				new FileOutputStream("WordCount.xml")), doc); 
		} catch(Exception e) {
			System.err.println(e);
		}
	}
}