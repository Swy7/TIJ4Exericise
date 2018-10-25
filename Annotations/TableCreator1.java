// annotations.database.TableCreator1.java
// TIJ4 Chapter Annotations, Exercise 1, page 1073
// Implement more SQL types in the database example.
// // {Args: annotations.database.Member1}

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2009
*/

/**** 
*Include, in same package:
*package annotations.database;
*import java.lang.annotation.*;
*@Target(ElementType.FIELD)
*@Retention(RetentionPolicy.RUNTIME)]
*public @interface SQLBlob {
*	String name() default "";
*	Constraints contstraints() default @Constraints;
*}
*package annotations.database;
*import java.lang.annotation.*;
*@Target(ElementType.FIELD)
*@Retention(RetentionPolicy.RUNTIME)]
*public @interface SQLBoolean {
*	String name() default "";
*	Constraints contstraints() default @Constraints;
*}
*package annotations.database;
*import java.lang.annotation.*;
*@Target(ElementType.FIELD)
*@Retention(RetentionPolicy.RUNTIME)]
*public @interface SQLDateTime {
*	String name() default "";
*	Constraints contstraints() default @Constraints;
*}
*package annotations.database;
*import java.lang.annotation.*;
*@Target(ElementType.FIELD)
*@Retention(RetentionPolicy.RUNTIME)]
*public @interface SQLVarChar {
*	String name() default "";
*	Constraints contstraints() default @Constraints;
*}
*package annotations.database;
*import java.lang.annotation.*;
*@Target(ElementType.FIELD)
*@Retention(RetentionPolicy.RUNTIME)
*public @interface Constraints {
*	boolean primaryKey() default false;
*	boolean allowNull() default true;
*	boolean unique() default false;
*}	
*package annotations.database;
*import java.lang.annotation.*;
*class Photo {}
*@DBTable(name = "MEMBER1")
*public class Member1 {
*	@SQLString(30) String firstName;
*	@SQLString(50) String lastName;	
*	@SQLInteger Integer age;
*	@SQLString(value = 30, constraints = @Constraints(primaryKey = true))
*		String handle;
*	@SQLBoolean Boolean newMemberStatus;
*	@SQLVarChar String address;
*	@SQLDateTime String dateTime;
*	@SQLBlob Photo photo;
*	static int MemberCount;
*	public String getHandle() { return handle; }
*	public String getFirstName() { return firstName; }
*	public String getLastName() { return lastName; }
*	public String toString() { return handle; }
*	public Integer getAge() { return age; }
*	public Boolean getNewMemberStatus() { return newMemberStatus; }
*	public String getAddress() { return address; }
*	public Photo getPhoto() { return Photo; }	
*}
****/
package annotations.database;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class TableCreator1 {
	public static void main(String[] args) throws Exception {
		if(args.length < 1) {
			System.out.println("arguments: annotated classes");
			System.exit(0);
		}	
		for(String className : args) {
			Class<?> cl = Class.forName(className);
			DBTable dbTable = cl.getAnnotation(DBTable.class);
			if(dbTable == null) {
				System.out.println("No DBTable annotations in class " +
					className);
				continue;
			}
			String tableName = dbTable.name();
			// If the name is empty, use the Class name
			if(tableName.length() < 1)
				tableName = cl.getName().toUpperCase();
			List<String> columnDefs = new ArrayList<String>();
			for(Field field : cl.getDeclaredFields()) {
				String columnName = null;
				Annotation[] anns = field.getDeclaredAnnotations();
				if(anns.length < 1) continue; // Not a db table column
				if(anns[0] instanceof SQLInteger) {
					SQLInteger sInt = (SQLInteger) anns[0];
					// Use field name if name not specified
					if(sInt.name().length() < 1)
						columnName = field.getName().toUpperCase();
					else
						columnName = sInt.name();
					columnDefs.add(columnName + " INT" +
						getConstraints(sInt.constraints()));
				}
				if(anns[0] instanceof SQLString) {
					SQLString sString = (SQLString)anns[0];
					// Use field name if name not specified
					if(sString.name().length() < 1)
						columnName = field.getName().toUpperCase();
					else
						 columnName = sString.name();
					columnDefs.add(columnName + " VARCHAR(" + 
						sString.value() + ")" +
						getConstraints(sString.constraints()));
				}
				if(anns[0] instanceof SQLBoolean) {
					SQLBoolean sBool = (SQLBoolean) anns[0];
					// Use field name if name not specified
					if(sBool.name().length() < 1)
						columnName = field.getName().toUpperCase();
					else
						columnName = sBool.name();
					columnDefs.add(columnName + " BOOLEAN(" +
						getConstraints(sBool.constraints()));
				}
				if(anns[0] instanceof SQLDateTime) { 
					SQLDateTime sDateTime = (SQLDateTime)anns[0];
					// Use field name if name not specified
					if(sDateTime.name().length() < 1)
						columnName = field.getName().toUpperCase();
					else
						columnName = sDateTime.name();
					columnDefs.add(columnName + " DATETIME(" +
						getConstraints(sDateTime.constraints())); 	
				}
				if(anns[0] instanceof SQLVarChar) {
					SQLVarChar sVarChar = (SQLVarChar)anns[0];
					if(sVarChar.name().length() < 1)
						columnName = field.getName().toUpperCase();
					else
						columnName = sVarChar.name();
					columnDefs.add(columnName + " VARCHAR(" +
						getConstraints(sVarChar.constraints()));
				}
				if(anns[0] instanceof SQLBlob) {
					SQLBlob sBlob = (SQLBlob)anns[0];
					// Use field name if name not specified
					if(sBlob.name().length() < 1)
						columnName = field.getName().toUpperCase();
					else
						columnName = sBlob.name();
					columnDefs.add(columnName + " BLOB(" +
						getConstraints(sBlob.constraints()));
				}
				StringBuilder createCommand = new StringBuilder(
					"CREATE TABLE " + tableName + "(");
				for(String columnDef : columnDefs)
					createCommand.append("\n     " + columnDef + ",");
				// Remove trailing coma
				String tableCreate = createCommand.substring(
					0, createCommand.length() - 1) +");";
				System.out.println("Table Creation SQL for " + 
					className + " is :\n" + tableCreate);
			}
		}
	}
	private static String getConstraints(Constraints con) {
		String  constraints = "";
		if(!con.allowNull()) constraints += " NOT NULL";
		if(con.primaryKey()) constraints += " PRIMARY KEY";
		if(con.unique()) constraints += " UNIQUE";
		return constraints;
	}
}	
