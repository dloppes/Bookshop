import java.util.Scanner;

/**
 * 
 * @author Daniel Lopes
 *
 *         Book entity and all of its particular methods and attributes
 */

public class Book {

	Scanner scanner = new Scanner(System.in);
	protected String id;
	protected String title;
	protected String author;
	protected String year;
	protected boolean available;

	public Book(String id, String title, String author, String year) {

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

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getYear() {
		return year;
	}

}
