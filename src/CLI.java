import java.util.Scanner;

public class CLI {

	protected Database database = new Database();
	Scanner scanner = new Scanner(System.in);
	Book book = new Book();
	Reader reader = new Reader();

	public CLI() {

		database.readReadersFile();
		database.readBooksFile();
		// database.readWaitingListFile();
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

		if (option.equals("1")) {
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

			} while (validateThreeOptions(selected) == false);

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

				System.out.println("Sorry! No book by that name has been found in the system!");
				System.out.println("-----------------------------------------------------------");
				booksMenu();

			} else {
				System.out.println("TODO: Report next user waiting for the book that book is available!");
			}
		}

		else if (option.equals("4")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();

		}

		else {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Please select a valid option. Only numbers from 1 to 4 are accepted!");
			booksMenu();
		}

	}

	public void searchBooks() {

		Scanner scBookSearch = new Scanner(System.in);

		System.out.println("How would you like to search by?");
		System.out.println("1 - Title");
		System.out.println("2 - Author");
		System.out.println("3 - Return to the Main Menu");

		String searchBy = scBookSearch.nextLine();

		/*
		 * used reader validator in books, because it checks two entries. SO I don`t
		 * have to recode something that already checks it.
		 */
		while (validateThreeOptions(searchBy) == false) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please select a valid option! Only numbers 1 or 3 are accepted");
			searchBooks();
		}

		if (searchBy.equals("1")) {
			System.out.println("------------------------");
			System.out.println("Please insert Title:");

			String bookTitle = scBookSearch.nextLine();

			if (database.searchBook(bookTitle) == null) {
				System.out.println("Sorry title: " + bookTitle + " does not exist in our database!");
				booksMenu();
			} else if (database.searchBook(bookTitle).isAvailable() == false) {
				String option;
				do {
					System.out.println("------------------------------------------------------------------");
					System.out.println("Sorry, book " + bookTitle + " is not available at the moment!");
					System.out.println("Would you like to be added in the waiting list?");
					System.out.println("1 - Yes");
					System.out.println("2 - No");
					option = scBookSearch.next();
				} while (!option.matches("[1-2]+"));

				if (option.equals("1")) {
					// TODO create method that write into the file

				}

				else {
					System.out.println("-----------------------------------");
					System.out.println("You will be sent back to the book Menu");
					booksMenu();
				}
			} else {

				String option;
				do {
					book = database.searchBook(bookTitle);
					System.out.println("-----------------------------------");
					System.out.println("ID: " + book.getId());
					System.out.println("Title: " + book.getTitle());
					System.out.println("Author: " + book.getAuthor());
					System.out.println("-----------------------------------");
					System.out.println("Would you like to rent it?");
					System.out.println("1 - Yes");
					System.out.println("2 - No");

					option = scBookSearch.next();

				}

				while (!option.matches("[1-2]+"));

				if (option.equals("1")) {
					System.out.println("create method to rent title");
				}

				else {
					System.out.println("-----------------------------------");
					System.out.println("You will be sent back to the book Menu");
					booksMenu();
				}

			}
		} else if (searchBy.equals("2")) {
			System.out.println("------------------------");
			System.out.println("Please insert Author:");
			String bookAuthor = scBookSearch.nextLine();

			if (database.searchBook(bookAuthor) == null) {
				System.out.println("Sorry Author: " + bookAuthor + " does not exist in our database!");
				booksMenu();
				;
			}

			else if (database.searchBook(bookAuthor).isAvailable() == false) {

				String option;

				do {
					System.out.println("------------------------------------------------------------------");
					System.out.println("Sorry, book from " + bookAuthor + " is not available at the moment!");
					System.out.println("Would you like to be added in the waiting list?");
					System.out.println("1 - Yes");
					System.out.println("2 - No");
					option = scBookSearch.next();
				}

				while (!option.matches("[1-2]+"));

				if (option.equals("1")) {
					// TODO create method that write into the file

				}

				else {
					System.out.println("-----------------------------------");
					System.out.println("You will be sent back to the book Menu");
					booksMenu();
				}

			}

			else {
				book = database.searchBook(bookAuthor);
				System.out.println("-----------------------------------");
				System.out.println("ID: " + book.getId());
				System.out.println("Title: " + book.getTitle());
				System.out.println("Author: " + book.getAuthor());
				System.out.println("-----------------------------------");
				System.out.println("Would you like to rent it?");
				System.out.println("1 - Yes");
				System.out.println("2 - No");

				String option = scBookSearch.next();

				if (option.equals("1")) {

				}

				else if (option.equals("2")) {
					System.out.println("-----------------------------------");
					System.out.println("Okay. You will be sent to the main Menu");
					System.out.println("-----------------------------------");
					welcomeMenu();

				}

				else {
					System.out.println("-----------------------------------");
					System.out.println("Only numbers 1 or 2 are accepted!");

					searchBooks();

				}

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

		if (validateThreeOptions(option) == false) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please select a valid option! Only numbers 1 or 3 are accepted");
			readerMenu();
		}
		if (option.equals("1")) {
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
			} while (validateThreeOptions(selected) == false);

			if (selected.equals("1")) {
				database.sortReaderList("id");
				System.out.println("------------------------------------------");
				readerMenu();
			} else if (selected.equals("2")) {
				database.sortReaderList("name");
				System.out.println("------------------------------------------");
				readerMenu();
			} else if (selected.equals("3")) {
				System.out.println("------------------------------------------");
				readerMenu();
			}

		} else if (option.equals("3")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();
		}

	}

	public void searchReader() {

		Scanner scReaderSearch = new Scanner(System.in);

		System.out.println("-----------------------------------");
		System.out.println("How would you like to search by?");
		System.out.println("1 - ID");
		System.out.println("2 - Name");
		System.out.println("3 - Return to the Main Menu");

		String searchBy = scReaderSearch.next();

		if (validateThreeOptions(searchBy) == false) {

			System.out.println("----------------------------------------------------------------");
			System.out.println("Please select a valid option! Only numbers 1 or 3 are accepted");
			searchReader();
		}

		else if (searchBy.equals("1")) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please insert ID number:");

			String readerID = scReaderSearch.next();

			if (database.searchReaders("", "", readerID) == null) {
				System.out.println("Sorry Reader ID: " + readerID + " does not exist in our database!");
				readerMenu();
			}

			else {
				reader = database.searchReaders("", "", readerID);
				System.out.println("-----------------------------------");
				System.out.println("ID: " + reader.getId());
				System.out.println("Name: " + reader.getfName() + " " + reader.getlName());
				System.out.println("-----------------------------------");
				System.out.println("Would you like to see the titles rented by " + reader.getfName() + " "
						+ reader.getlName() + "?");
				System.out.println("1 - Yes");
				System.out.println("2 - No");

				String option = scReaderSearch.next();

				if (option.equals("1")) {

					if (database.searchRentedBooksFile(reader) == false) {

						System.out.println(
								reader.getfName() + " " + reader.getlName() + " has not rented any books yet!");

						readerMenu();
					}

				}

				else if (option.equals("2")) {
					System.out.println("-----------------------------------");
					System.out.println("Okay. You will be sent to the main Menu");
					System.out.println("-----------------------------------");
					welcomeMenu();

				}

				else {
					System.out.println("-----------------------------------");
					System.out.println("Only numbers 1 or 2 are accepted!");

					searchReader();

				}

			}
		}

		else if (searchBy.equals("2")) {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please insert First Name:");
			String firstName = scReaderSearch.next();
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please insert Last Name:");
			String lastName = scReaderSearch.next();

			if (database.searchReaders(firstName, lastName, "") == null) {
				System.out
						.println("Sorry, name " + firstName + " " + lastName + " have not been found in our database!");
				readerMenu();
			} else {

				reader = database.searchReaders(firstName, lastName, "");
				System.out.println("-----------------------------------");
				System.out.println("ID: " + reader.getId());
				System.out.println("Name: " + reader.getfName() + " " + reader.getlName());
				System.out.println("-----------------------------------");
				System.out.println("Would you like to see the titles rented by " + reader.getfName() + " "
						+ reader.getlName() + "?");
				System.out.println("1 - Yes");
				System.out.println("2 - No");

				String option = scReaderSearch.next();

				if (option.equals("1")) {

					if (database.searchRentedBooksFile(reader) == false) {
						System.out.println(
								reader.getfName() + " " + reader.getlName() + " has not rented any books yet!");

						readerMenu();
					}

				}

				else if (option.equals("2")) {
					System.out.println("-----------------------------------");
					System.out.println("Okay. You will be sent to the main Menu");
					System.out.println("-----------------------------------");
					welcomeMenu();

				}

				else {
					System.out.println("-----------------------------------");
					System.out.println("Only numbers 1 or 2 are accepted!");

					searchReader();

				}

			}
		}

		else if (searchBy.equals("3")) {
			System.out.println("--------------------------------------------------------");
			welcomeMenu();
		}

	}

	public boolean validateThreeOptions(String option) {

		boolean result = true;

		if (!option.matches("[1-3]+")) {
			result = false;

		}
		return result;

	}

}
