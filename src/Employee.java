
public class Employee extends Person {

	protected String username;
	protected String password;

	public Employee(String fName, String lName, String email, String username, String password) {
		super(fName, lName, email);

		this.username = username;
		this.password = password;

	}

	public Employee(int id, String fName, String lName, String email, String username, String password) {
		super(id, fName, lName, email);
		// TODO Auto-generated constructor stub

		this.username = username;
		this.password = password;
	}

}
