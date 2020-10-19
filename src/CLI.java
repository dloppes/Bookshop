import java.util.Scanner;

public class CLI {

	Scanner scanner = new Scanner(System.in);
	Book book = new Book();
	Reader reader = new Reader();
	

	public CLI() {
		// TODO Auto-generated constructor stub

		Database database = new Database();
		welcomeMenu();
	}

	public void welcomeMenu() {

		System.out.println("Welcome to the CCT Bookshop! Select one of the options below:");
		System.out.println("1 - Books");
		System.out.println("2 -Readers");

		String input = scanner.next();

		menuValidator(input);

		if (input.equals("1")) {

			// the value got from the method books menu is stored in the option
			booksMenu();

		}

		if (input.equals("2")) {
			readerMenu();

		}

	}

	public void booksMenu() {

		System.out.println("-------------------------------");
		System.out.println("What would you like to do?");
		System.out.println("1 - Search Books");
		System.out.println("2 - List of Books");
		System.out.println("3 - Set Book as Returned");
		System.out.println("4 - Return to the Main Menu");

		String option = scanner.next();

		if (book.validateBookMenu(option) == false) {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Please select a valid option. Only numbers from 1 to 4 are accepted!");
			booksMenu();

		} else if (option.equals("1")) {
			searchBooks();
		}

		else if (option.equals("2")) {

		}

		else if (option.equals("3")) {

		}

		else if (option.equals("4")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();

		}

	}

	public void searchBooks() {

		System.out.println("How would you like to search by?");
		System.out.println("1 - Title");
		System.out.println("2 - Author");
		System.out.println("3 - Return to the Main Menu");

		String searchBy = scanner.next();

		/*
		 * used reader validator in books, because it checks two entries. SO I don`t
		 * have to recode something that already checks it.
		 */
		if (reader.validateThreeOptions(searchBy) == false) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please select a valid option! Only numbers 1 or 3 are accepted");
			searchBooks();
		}

		else if (searchBy.equals("1")) {
			System.out.println("------------------------");
			System.out.println("Please insert Title:");
		} else if (searchBy.equals("2")) {
			System.out.println("------------------------");
			System.out.println("Please insert Author:");
		}

		else if (searchBy.equals("3")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();
		}

	}

	public void readerMenu() {

		System.out.println("----------------------------");
		System.out.println("What would you like to do?");
		System.out.println("1 - Search Reader");
		System.out.println("2 - List of Readers");
		System.out.println("3 - Return to the Main Menu");

		String option = scanner.next();

		if (reader.validateThreeOptions(option) == false) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please select a valid option! Only numbers 1 or 3 are accepted");
			readerMenu();
		} else if (option.equals("1")) {
			searchReader();
		} else if (option.equals("2")) {
			reader.listOfReaders();
		}
		else if (option.equals("3")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();
		}
	}

	public void searchReader() {

		System.out.println("-----------------------------------");
		System.out.println("How would you like to search by?");
		System.out.println("1 - ID");
		System.out.println("2 - Name");
		System.out.println("3 - Return to the Main Menu");

		String searchBy = scanner.next();
		if (reader.validateThreeOptions(searchBy) == false) {

			System.out.println("----------------------------------------------------------------");
			System.out.println("Please select a valid option! Only numbers 1 or 3 are accepted");
			searchReader();
		}
		
		else if(searchBy.equals("1")) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please insert ID number:");
		}
		
		else if(searchBy.equals("2")) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please insert Name:");
		}

		else if (searchBy.equals("3")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();
		}

	}

	public void menuValidator(String input) {

		/*
		 * As I will have multiple times where I have to check between two options I
		 * this method will check it and returns if the option is valid
		 */

		boolean result = false;

		if (!input.matches("[1-2]+")) {
			result = false;
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please select a valid option! Only numbers 1 or 2 are accepted");
			welcomeMenu();
		}
		result = true;

	}

}
