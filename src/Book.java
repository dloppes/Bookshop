import java.sql.Date;

/**
 * 
 * @author Daniel Lopes
 *
 *         Book entity and all of its particular methods and attributes
 */

public class Book {

	protected String id;
	protected String title;
	protected String author;
	protected String year;
	protected boolean available = true;
	MyLinkedList waitingList;

	public Book(String id, String title, String author, String year, boolean available) {

		/*
		 * This constructor is used to create the book entity.
		 */

		this.id = id;
		this.title = title;
		this.author = author;
		this.year = year;
		this.available = available;

	}

	public Book() {

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

	public MyLinkedList getWaitingList() {
		return waitingList;
	}

	public void setWaitingList(MyLinkedList waitingList) {
		this.waitingList = waitingList;
	}

	public class RentedBooks {

		protected String readerID;
		protected String bookID;
		protected String dateOut;
		protected String dateIn;

		protected Book book;
		protected Reader reader;

		public RentedBooks(String readerID, String bookID, String dateOut, String dateIn) {

			this.readerID = readerID;
			this.bookID = bookID;
			this.dateOut = dateOut;
			this.dateIn = dateIn;
		}

		public RentedBooks(Book book, Reader reader, String dateOut, String dateIn ) {

			this.book = book;
			this.reader = reader;

		}

		public Book getBook() {
			return book;
		}

		public Reader getReader() {
			return reader;
		}

		public String getReaderID() {
			return readerID;
		}

		public String getBookID() {
			return bookID;
		}

		public String getDateOut() {
			return dateOut;
		}

		public String getDateIn() {
			return dateIn;
		}

	}

}
