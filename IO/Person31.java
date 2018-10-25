// xml/Person31.java
// TIJ4 Chapter IO, Exercise 31, page 1006
// Add appropriate address information to Person.java and People.java.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2008
*/


import nu.xom.*;
import java.io.*;
import java.util.*;

public class Person31 {
	private String first, last, address;
	public Person31(String first, String last, String address) {
		this.first = first;
		this.last = last;
		this.address = address;
	}
	// Produce an XML Element from this Person31 object:
	public Element getXML() {
		Element person = new Element("person");
		Element firstName = new Element("first");
		firstName.appendChild(first);
		Element lastName = new Element("last");
		lastName.appendChild(last);
		Element homeAddress = new Element("address");
		homeAddress.appendChild(address);
		person.appendChild(firstName);
		person.appendChild(lastName);
		person.appendChild(homeAddress);
		return person;
	}	
	// Constructor to restore a Person from31 an XML Element:
	public Person31(Element person) {
		first = person.getFirstChildElement("first").getValue();
		last = person.getFirstChildElement("last").getValue();
		address = person.getFirstChildElement("address").getValue();
	}
	public String toString() { return first + " " + last + " " + address; }
	// Make it human-readable: 
	public static void
	format(OutputStream os, Document doc) throws Exception {
		Serializer serializer = new Serializer(os, "ISO-8859-1");
		serializer.setIndent(4);
		serializer.setMaxLength(60);
		serializer.write(doc);
		serializer.flush();
	}
	public static void main(String[] args) throws Exception {
		List<Person31> people = Arrays.asList(
			new Person31("Dr. Bunsen", "Honeydew", "1234 Main Street"),
			new Person31("Gonzo", "The Great", "Top-o-the-heap"),
			new Person31("Phillip J.", "Fry", "66 Beat Street"));
		System.out.println(people);
		Element root = new Element("people");
		for(Person31 p : people)
			root.appendChild(p.getXML());	
		Document doc = new Document(root);
		format(System.out, doc);
		format(new BufferedOutputStream(new FileOutputStream(
			"People31.xml")), doc);	
	}
}