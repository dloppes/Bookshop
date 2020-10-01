
/**
 * 
 * @author Daniel Lopes
 *
 *         This abstract class has all the attributes that are common amongst
 *         reader and employee. They will be able to extend it and add any other
 *         attribute that is particular to that entity.
 */

public abstract class Person {

	protected int id;
	protected String fName;
	protected String lName;
	protected String email;

	public Person(String fName, String lName, String email) {
		// TODO Auto-generated constructor stub

		this.fName = fName;
		this.lName = lName;
		this.email = email;
	}

	public Person(int id, String fName, String lName, String email) {
		// TODO Auto-generated constructor stub

		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
	}

}
