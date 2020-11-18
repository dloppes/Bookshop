
/**
 * 
 * @author Daniel Lopes
 *
 *         Book entity and all of its particular methods and attributes
 */

public class Book {

	private String id;
	private String title;
	private String author;
	private String year;
	private boolean available = true;
	private MyLinkedList waitingList;

	public Book(String id, String title, String author, String year, boolean available) {

		/*
		 * This constructor is used to create the book entity.
		 */

		this.id = id;
		this.title = title;
		this.author = author;
		this.year = year;
		this.available = available;
		this.waitingList = new MyLinkedList();

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

		protected String dateOut;
		protected String dateIn;

		protected Book book;
		protected Reader reader;

		public RentedBooks(Book book, Reader reader, String dateOut, String dateIn) {

			this.book = book;
			this.reader = reader;
			this.dateOut = dateOut;
			this.dateIn = dateIn;

		}

		public Book getBook() {
			return book;
		}

		public Reader getReader() {
			return reader;
		}

		public String getDateOut() {
			return dateOut;
		}

		public String getDateIn() {
			return dateIn;
		}

	}

}
