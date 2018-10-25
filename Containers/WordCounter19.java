// containers/WordCounter19.java
// TIJ4 Chapter Containers, Exercise 19, page 851
// Repeat Exercise 13 using a SimpleHashMap.
import net.mindview.util.*;	
import static net.mindview.util.Print.*;
import java.util.*;

public class WordCounter19 {
	public static void main(String[] args) {
		// File whose words are to be counted:
		String fileName = "WordCounter19.java";
		// Set of unique words in file:
		Set<String> words = new TreeSet<String>(new TextFile(fileName, "\\W+"));
		// Create initialize array of correct length:
		SimpleHashMap<String,Integer> wordCount =
			new SimpleHashMap<String,Integer>();		
		// Word list of all words in file:
		ArrayList<String> fileList = new TextFile(fileName, "\\W+");
		// Count appearances of each unique word and add to array:
		for(String s : words) {
			int count = 0;
			for(String t : fileList) {
				if(t.equals(s)) count++;
			}
			wordCount.put(s, count);
		}
		print(wordCount);
	}
}