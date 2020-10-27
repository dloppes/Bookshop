import java.util.Scanner;

public class CLI {

	protected Database database = new Database();
	Scanner scanner = new Scanner(System.in);
	Book book = new Book();
	Reader reader = new Reader();

	public CLI() {
		// TODO Auto-generated constructor stub

//		database.readBooksFile();
//		database.readReadersFile();
		database.readWaitingListFile();
		welcomeMenu();
	}

	public void welcomeMenu() {

		System.out.println("Welcome to the CCT Bookshop! Select one of the options below:");
		System.out.println("1 - Books");
		System.out.println("2 - Readers");

		String input = scanner.next();

		if (input.equals("1")) {

			// the value got from the method books menu is stored in the option
			booksMenu();

		}

		if (input.equals("2")) {
			readerMenu();

		} else {

			System.out.println("----------------------------------------------------------------");
			System.out.println("Please select a valid option! Only numbers 1 or 2 are accepted");
			welcomeMenu();
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

			String selected;
			do {
				System.out.println("------------------------------------------");
				System.out.println("How would you like the list ordered by:");
				System.out.println("*** Only numbers from 1 to 3 are accepted ***");
				System.out.println("1 - By Title");
				System.out.println("2 - By Author");
				System.out.println("3 - Return to the main Menu");
				selected = scanner.next();
			} while (reader.validateThreeOptions(selected) == false);

			if (selected.equals("1")) {
				database.sortBooksList("title");
			} else if (selected.equals("2")) {
				database.sortBooksList("author");
			} else if (selected.equals("3")) {
				booksMenu();
			}
		}

		else if (option.equals("3")) {
			System.out.println("-----------------------------");
			System.out.println("Please insert book name:");

			Scanner bookScanner = new Scanner(System.in);
			String bookName = bookScanner.nextLine();

			if (database.setBookAsAvailable(bookName) == false) {

				System.out.println("Sorry! No book by that name found in the system!");
				System.out.println("--------------------------------------------------");
				booksMenu();

			} else {
				System.out.println("TODO: Report next user waiting for the book that book is available!");
			}
		}

		else if (option.equals("4")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();

		}

	}

	public void searchBooks() {

		Scanner scBookSearch = new Scanner(System.in);

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

			String bookTitle = scBookSearch.nextLine();

			book = database.searchBook(bookTitle, "title");
			if (book == null) {
				System.out.println("------------------------------------------------------------------");
				System.out.println("Sorry, book " + bookTitle + " is not available at the moment!");
				System.out.println("Would you like to be added in the waiting list?");
				System.out.println("1 - Yes");
				System.out.println("2 - No");
			} else {

				System.out.println("-----------------------------------");
				System.out.println("ID: " + book.getId());
				System.out.println("Title: " + book.getTitle());
				System.out.println("Author: " + book.getAuthor());
				System.out.println("-----------------------------------");
				System.out.println("Would you like to rent it?");
				System.out.println("1 - Yes");
				System.out.println("2 - No");

			}
		} else if (searchBy.equals("2")) {
			System.out.println("------------------------");
			System.out.println("Please insert Author:");
			String bookAuthor = scBookSearch.nextLine();

			book = database.searchBook(bookAuthor, "author");
			if (book == null) {
				System.out.println("------------------------------------------------------------------");
				System.out.println("Sorry, book from " + bookAuthor + " is not available at the moment!");
				System.out.println("Would you like to be added in the waiting list?");
				System.out.println("1 - Yes");
				System.out.println("2 - No");
			} else {

				System.out.println("-----------------------------------");
				System.out.println("ID: " + book.getId());
				System.out.println("Title: " + book.getTitle());
				System.out.println("Author: " + book.getAuthor());
				System.out.println("-----------------------------------");
				System.out.println("Would you like to rent it?");
				System.out.println("1 - Yes");
				System.out.println("2 - No");

			}
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
			String selected;
			do {
				System.out.println("------------------------------------------");
				System.out.println("How would you like the list ordered by:");
				System.out.println("*** Only numbers from 1 to 3 are accepted ***");
				System.out.println("1 - By ID");
				System.out.println("2 - By Name");
				System.out.println("3 - Return to the main Menu");
				selected = scanner.next();
			} while (reader.validateThreeOptions(selected) == false);

			if (selected.equals("1")) {
				database.sortReaderList("id");
			} else if (selected.equals("2")) {
				database.sortReaderList("name");
			} else if (selected.equals("3")) {
				readerMenu();
			}

		} else if (option.equals("3")) {
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

		else if (searchBy.equals("1")) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please insert ID number:");
		}

		else if (searchBy.equals("2")) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please insert Name:");
		}

		else if (searchBy.equals("3")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();
		}

	}

}
