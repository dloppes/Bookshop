
/**
 * 
 * @author Daniel Lopes
 *
 *         Book entity and all of its particular methods
 */

public class Book {

	protected int id;
	protected String title;
	protected String author;
	protected String director;
	protected int year;

	public Book(String title, String author, String director, int year) {

		/*
		 * This constructor is used when I first create the book entity, It has no ID
		 * because the ID might be generated automatically when inserted into the
		 * database.
		 */

		this.title = title;
		this.author = author;
		this.director = director;
		this.year = year;
	}

	public Book(int id, String title, String author, String director, int year) {

		/*
		 * Once the book is inserted into the database I will have an ID, therefore I
		 * can query and get the ID from the system in order to create an entity with
		 * the ID.
		 */

		this.id = id;
		this.title = title;
		this.author = author;
		this.director = director;
		this.year = year;
	}

}
