import java.util.Scanner;

/**
 * 
 * @author Daniel Lopes
 *
 *         Book entity and all of its particular methods and attributes
 */

public class Book {

	Scanner scanner = new Scanner(System.in);
	protected int id;
	protected String title;
	protected String author;
	protected int year;
	protected boolean available;

	public Book(int id, String title, String author,int year) {

		/*
		 * This constructor is used to create the book entity.
		 */

		this.id = id;
		this.title = title;
		this.author = author;
		this.year = year;
		available = true;
	}

	public Book() {

	}

	public boolean validateBookMenu(String option) {

		boolean result = true;

		if (!option.matches("[1-4]+")) {
			result = false;

		}
		return result;

	}

}
