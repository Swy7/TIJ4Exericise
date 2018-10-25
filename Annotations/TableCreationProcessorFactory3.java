// annotations/database/TableCreationProcessorFactory3.java
// TIJ4  Chapter Annotations, Exercise 3, page 1083
// Add support for more SQL types to TableCreationProcessorFactory.java.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* April, 2009
*/

// The database example using Visitor
// {Exec: apt -factory annotations.database.TableCreationProcessorFactory3 Member3.java -s ../database}
package annotations.database;
import com.sun.mirror.apt.*;
import com.sun.mirror.declaration.*;
import com.sun.mirror.util.*;
import java.util.*;
import static com.sun.mirror.util.DeclarationVisitors.*;

public class TableCreationProcessorFactory3 implements AnnotationProcessorFactory {
	public AnnotationProcessor getProcessorFor(
		Set<AnnotationTypeDeclaration> atds, AnnotationProcessorEnvironment env) {
		return new TableCreationProcessor(env);
	}
	public Collection<String> supportedAnnotationTypes() {
		return Arrays.asList(
			"annotations.database.DBTable",
			"annotations.database.Constraints",
			"annotations.database.SQLString",
			"annotations.database.SQLInteger",
			"annotations.database.SQLBoolean",
			"annotations.database.SQLVarChar",
			"annotations.database.SQLDateTime",
			"annotations.database.SQLBlob");
	}
	public Collection<String> supportedOptions() {
		return Collections.emptySet();
	}
	private static class TableCreationProcessor implements AnnotationProcessor {
		private final AnnotationProcessorEnvironment env;
		private String sql = "";
		public TableCreationProcessor(AnnotationProcessorEnvironment env) {
			this.env = env;
		} 
		public void process() {
			for(TypeDeclaration typeDecl : env.getSpecifiedTypeDeclarations()) {
				typeDecl.accept(getDeclarationScanner(new TableCreationVisitor(), NO_OP));
				sql = sql.substring(0, sql.length() - 1) + ");";
				System.out.println("creation SQL is :\n" + sql);
				sql = "";
			}
		}	
		private class TableCreationVisitor extends SimpleDeclarationVisitor {
			public void visitClassDeclaration(ClassDeclaration d) {
				DBTable dbTable = d.getAnnotation(DBTable. class);
				if(dbTable != null) {
					sql += "CREATE TABLE ";
					sql += (dbTable.name().length() < 1) ? 
						d.getSimpleName().toUpperCase() : dbTable.name();
					sql += " (";
				}
			}
			public void visitFieldDeclaration(FieldDeclaration d) {
				String columnName = "";
				if(d.getAnnotation(SQLInteger.class) != null) {
					SQLInteger sInt = d.getAnnotation(SQLInteger.class);
					// Use field name if name not specified
					if(sInt.name().length() < 1)
						columnName = d.getSimpleName().toUpperCase();
					else
						columnName = sInt.name();
					sql += "\n   " + columnName + " INT" + 
						getConstraints(sInt.constraints()) + ",";
				}
				if(d.getAnnotation(SQLString.class) != null) {
					SQLString sString = d.getAnnotation(SQLString.class);
					// Use field name if name not specified
					if(sString.name().length() < 1)
						columnName = d.getSimpleName().toUpperCase();
					else
						columnName = sString.name();
					sql += "\n   " + columnName + " VARCHAR(" + 
						sString.value() + ")" +
						getConstraints(sString.constraints()) + ",";
				}
				if(d.getAnnotation(SQLBoolean.class) != null) {
					SQLBoolean sBoolean = d.getAnnotation(SQLBoolean.class);
					// Use field name if name not specifiec
					if(sBoolean.name().length() < 1)
						columnName = d.getSimpleName().toUpperCase();
					else
						columnName = sBoolean.name();
					sql += "\n   " + columnName + " BOOLEAN(" +
						getConstraints(sBoolean.constraints()) + ",";
				}
				if(d.getAnnotation(SQLVarChar.class) != null) {
					SQLVarChar sVarChar = d.getAnnotation(SQLVarChar.class);
					// Use field name if name not specified
					if(sVarChar.name().length() < 1)
						columnName = d.getSimpleName().toUpperCase();
					else
						columnName = sVarChar.name();
					sql += "\n   " + columnName + " VARCHAR(" +
						getConstraints(sVarChar.constraints()) + ","; 	
				}
				if(d.getAnnotation(SQLDateTime.class) != null) {
					SQLDateTime sDateTime = d.getAnnotation(SQLDateTime.class);
					if(sDateTime.name().length() < 1)
						columnName = d.getSimpleName().toUpperCase();
					else
						columnName = sDateTime.name();
					sql += "\n   " + columnName + " DATETIME(" +
						getConstraints(sDateTime.constraints()) + ",";
				}
				if(d.getAnnotation(SQLBlob.class) != null) {
					SQLBlob sBlob = d.getAnnotation(SQLBlob.class);
					if(sBlob.name().length()< 1)
						columnName = d.getSimpleName().toUpperCase();
					else
						columnName = sBlob.name();
					sql += "\n   " + columnName + " BLOB(" +
						getConstraints(sBlob.constraints()) + ",";
				}
			}
			private String getConstraints(Constraints con) {
				String constraints = "";
				if(!con.allowNull())  constraints += " NOT NULL";
				if(con.primaryKey())  constraints += " PRIMARY KEY";
				if(con.unique()) constraints += " UNIQUE";
				return constraints;
			}
		}	
	}
}