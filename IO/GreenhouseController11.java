// io/GreenhouseController11.java
// TIJ4 Chapter IO, Exercise 11, page 928
/* In the innerclasses/GreenhouseController.java example, GreenhouseController
* contains a hard-coded set of events. Change the program so that it reads the
* events from a text file. 
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* January, 2008
*/
/******** Events.txt file:
* Bell(900)
* ThermostatNight(100)
* LightOn(200)
* LightOff(400)
* WaterOn(600)
* WaterOff(800)
* ThermostatDay(1400)
*********/

import java.io.*;
import innerclasses.controller.*;
import innerclasses.*;
import java.util.*;

public class GreenhouseController11 {
	// To read events from text file and add to HashMap<String,Long>:
	public static Map<String,Long> readEvents(String filename) 
	throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		Map<String,Long> map = new HashMap<String,Long>();
		while((s = in.readLine()) != null) {
			String [] sa = s.split("[()]");
			map.put(sa[0], new Long(sa[1]));
		}
		in.close();
		return map;
	}
	// To build Event objects from Map.Entry objects:
	private static Event makeEvent(GreenhouseControls gc, Map.Entry<String,Long> me) {
		String key = me.getKey();
		Long value = me.getValue();
		if(key.equals("Bell")) return gc.new Bell(value);
		if(key.equals("LightOn")) return gc.new LightOn(value);
		if(key.equals("LightOff")) return gc.new LightOff(value);
		if(key.equals("WaterOn")) return gc.new WaterOn(value);
		if(key.equals("WaterOff")) return gc.new WaterOff(value);
		if(key.equals("ThermostatDay")) return gc.new ThermostatDay(value);
		if(key.equals("ThermostatNight")) return gc.new ThermostatNight(value);
		return null;
	}
	public static void main(String[] args) {
		GreenhouseControls gc = new GreenhouseControls();
		// Instead of hard-wiring, you could parse
		// configuration information from a text file here:
		try {
			// Read text and convert lines to map entries:
			Map<String,Long> map = readEvents("Events.txt");
			Event[] eventList = new Event[map.size()];
			int i = 0;
			// Make events from map and add to Event array: 
			for(Map.Entry<String,Long> me : map.entrySet()) {
				eventList[i++] = makeEvent(gc, me);
			}
			gc.addEvent(gc.new Restart(2000, eventList));
			if(args.length != 1) {
				System.out.println("Usage: enter integer terminate time");
				System.exit(0);
			}
			if(args.length == 1)
				gc.addEvent(new GreenhouseControls.Terminate(
					new Integer(args[0])));
		gc.run();
		} catch(IOException e) {
			System.out.println(e);
		}	
	}	
}