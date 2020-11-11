import java.util.Scanner;

public class Reader {

	Scanner scanner = new Scanner(System.in);
	protected String id;
	protected String fName;
	protected String lName;
	protected String email;
	protected String phoneNumber;

	public Reader(String id, String fName, String lName, String email, String phoneNumber) {

		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.phoneNumber = phoneNumber;

	}

	public Reader() {

	}

	public String getId() {
		return id;
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
