import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * 
 * @author Daniel Lopes
 *
 *         Database class that interacts with all arrays and objects as well as
 *         the CLI class.
 */
public class Database {

	Reader reader;
	Book book;
	Book.RentedBooks rentedBooks;
	private ArrayList<Reader> readerList = new ArrayList<Reader>();
	private ArrayList<Book> bookList = new ArrayList<Book>();
	private ArrayList<Book.RentedBooks> booksLog = new ArrayList<>();

	public void returnABook(Book outsideBook) {

		for (Book book : bookList) {
			// change book attribute isAvailable to true;
			if (book.getId().equals(outsideBook.getId())) {
				book.setAvailable(true);
				saveFile("books");
			}
		}

		String dateIn = getFormattedDate();

		// find book in the booksLog
		for (Book.RentedBooks rentedBooks : booksLog) {

			// once book has been found, set return date
			if (rentedBooks.getBook().getId().equals(outsideBook.getId()) && rentedBooks.getDateIn().isEmpty()) {
				rentedBooks.setDateIn(dateIn);
				saveFile("rented books");
			}
		}

		try {
			// find book in the booksLog array and alter information
			// code to remove the reader waiting for the book from the queue.
			for (Book book : bookList) {
				// once book has been found, set return date
				if (book.getId().equals(outsideBook.getId())) {

					if (book.getWaitingList().isEmpty()) {
						System.out.println("Thanks for returning the book " + book.getTitle()
								+ " ! There is nobody waiting for this title at the moment!");
					}

					reader = book.getWaitingList().findElementByPosition(0).getElement();
					System.out.println(
							"---------------------------------------------------------------------------------------------------------");
					System.out.println("Thanks for returning the book " + book.getTitle() + "!");
					System.out.println(
							"---------------------------------------------------------------------------------------------------------");
					System.out.println("Reader " + reader.getfName() + " " + reader.getlName()
							+ " is the first in queue waiting for this title!");

					book.getWaitingList().removeFirst();

					// save waiting listArray
					saveFile("waiting list");
				}
			}
		} catch (NullPointerException nullPointerException) {
			/*
			 * this try catch is just to handle the nullPointer of Exception, as I know
			 * waiting list can be empty I don`t want the program to crash.
			 */
		}

	}

	public void rentABook(Reader reader, Book outsideBook) {

		for (Book book : bookList) {

			// first change the book attribute isAvailable to false;
			if (book.getId().equals(outsideBook.getId())) {
				book.setAvailable(false);
				saveFile("books");
			}
		}

		// getting todays date from method
		String dateOut = getFormattedDate();

		/*
		 * creating a rented Book object with Reader/Book/dateOut & adding the object to
		 * the array booksLog
		 */
		rentedBooks = book.new RentedBooks(outsideBook, reader, dateOut, "");
		booksLog.add(rentedBooks);

		// saving new data into RentedBooks XML
		saveFile("rented books");

	}

	public String getFormattedDate() {
		/*
		 * this method will return today`s date as String. It will be called to set date
		 * when book is rented as well when it is returned
		 */
		String today = "";

		DateFormat df = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();

		today = df.format(date);

		return today;
	}

	public void addReaderToWaitingListArray(Reader outsideReader, Book outsideBook) {

		/*
		 * This method handles the searching for the book in the array bookList and
		 * insertion of reader to its waiting list
		 */

		// this first part I check to see if user is already in the waiting list
		if (isAlreadyInTheList(outsideBook, outsideReader) == true) {
			System.out.println("Reader: " + outsideReader.getfName() + " " + outsideReader.getlName()
					+ " is already in the waiting list of this book!");

		} else {
			for (Book book : bookList) {

				if (book.getId().equals(outsideBook.getId())) {

					book.getWaitingList().addLast(outsideReader);

					System.out.println(outsideReader.getfName() + " " + outsideReader.getlName()
							+ " has been added to the waiting list!");

					// calling method to save file with the new data.
					saveFile("waiting list");
				}
			}
		}

	}

	public boolean isAlreadyInTheList(Book book, Reader reader) {

		/*
		 * this method will find the book in the bookList, get into its waiting list and
		 * see if the reader trying to be added to the waiting list already exists.
		 */

		for (Book bookSearched : bookList) {
			if (bookSearched.getId().equals(book.getId())) {
				for (int i = 0; i < bookSearched.getWaitingList().size(); i++) {
					if (bookSearched.getWaitingList().findElementByPosition(i).getElement().getId()
							.equals(reader.getId())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public void saveFile(String fileToSave) {

		/*
		 * Method to save updates to the xml File, this method receives the name of the
		 * file I want to save the information to.
		 */

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		if (fileToSave.equals("books")) {

			try {
				dBuilder = dbFactory.newDocumentBuilder();

				Document document = dBuilder.newDocument();

				Element root = document.createElement("books");

				// create book
				Element bookNode = null;

				// create title
				Element titleNode = null;

				// create author
				Element authorNode = null;

				// create year
				Element yearNode = null;

				// create available
				Element availableNode = null;

				// create a loop to create nodes while int i is not equal to array size

				for (int i = 0; i < bookList.size(); i++) {

					bookNode = document.createElement("book");

					// create book ID
					// Element bookIDNode = document.createElement("bookID");
					// code to add text value
					bookNode.setAttribute("id", bookList.get(i).getId());
					// bookNode.appendChild(bookIDNode);

					titleNode = document.createElement("title");
					Text titleText = document.createTextNode(bookList.get(i).getTitle());
					titleNode.appendChild(titleText);

					authorNode = document.createElement("author");
					Text authorText = document.createTextNode(bookList.get(i).getAuthor());
					authorNode.appendChild(authorText);

					yearNode = document.createElement("year");
					Text yearText = document.createTextNode(bookList.get(i).getYear());
					yearNode.appendChild(yearText);

					availableNode = document.createElement("available");
					Text availableText = document.createTextNode(Boolean.toString(bookList.get(i).isAvailable()));
					availableNode.appendChild(availableText);

					// appending the values to Book
					// bookNode.appendChild(bookIDNode);
					bookNode.appendChild(titleNode);
					bookNode.appendChild(authorNode);
					bookNode.appendChild(yearNode);
					bookNode.appendChild(availableNode);

					// adding bookNode to the root Books
					root.appendChild(bookNode);
				}

				// add root to the document
				document.appendChild(root);

				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				String path = "Books.xml";
				File f = new File(path);
				StreamResult result = new StreamResult(new PrintWriter(new FileOutputStream(f, false)));
				DOMSource source = new DOMSource(document);
				transformer.transform(source, result);

			} catch (TransformerFactoryConfigurationError | FileNotFoundException | TransformerException
					| ParserConfigurationException e) {

				e.printStackTrace();
			}
		}

		if (fileToSave.equals("rented books")) {
			try {
				dBuilder = dbFactory.newDocumentBuilder();

				Document document = dBuilder.newDocument();

				Element root = document.createElement("rentedList");

				// create reader
				Element readerNode = null;

				// create book
				Element bookNode = null;

				// create dateOut
				Element dateOutdNode = null;

				// create dateIn
				Element dateInNode = null;

				// create a loop to create nodes while int i is not equal to array size

				for (int i = 0; i < booksLog.size(); i++) {

					readerNode = document.createElement("reader");
					readerNode.setAttribute("id", booksLog.get(i).getReader().getId());

					bookNode = document.createElement("book");
					bookNode.setAttribute("id", booksLog.get(i).getBook().getId());

					dateOutdNode = document.createElement("dateOut");
					Text dateOutText = document.createTextNode(booksLog.get(i).getDateOut());
					dateOutdNode.appendChild(dateOutText);

					dateInNode = document.createElement("dateIn");
					Text dateInText = document.createTextNode(booksLog.get(i).getDateIn());
					dateInNode.appendChild(dateInText);

					// appending the values to Book
					bookNode.appendChild(dateOutdNode);
					bookNode.appendChild(dateInNode);

					// appending the values to Reader
					readerNode.appendChild(bookNode);

					// adding bookNode to the root Books
					root.appendChild(readerNode);

				}

				// add root to the document
				document.appendChild(root);

				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				String path = "RentedBooks.xml";
				File f = new File(path);
				StreamResult result = new StreamResult(new PrintWriter(new FileOutputStream(f, false)));
				DOMSource source = new DOMSource(document);
				transformer.transform(source, result);

			} catch (TransformerFactoryConfigurationError | FileNotFoundException | TransformerException
					| ParserConfigurationException e) {
				e.printStackTrace();
			}
		}

		if (fileToSave.equals("waiting list")) {
			try {
				dBuilder = dbFactory.newDocumentBuilder();

				Document document = dBuilder.newDocument();

				Element root = document.createElement("waitingList");

				// create book
				Element bookNode = null;

				// create readerID
				Element readerIDNode = null;

				// create a loop to create nodes while int i is not equal to array size

				for (int i = 0; i < bookList.size(); i++) {

					bookNode = document.createElement("book");

					// code to add text value
					bookNode.setAttribute("id", bookList.get(i).getId());
					// bookNode.appendChild(bookIDNode);

					/*
					 * second loop to iterate through the waiting list within each book, so I can
					 * get the reader id
					 */
					for (int j = 0; j < bookList.get(i).getWaitingList().size(); j++) {
						readerIDNode = document.createElement("readerID");
						Text readerIDText = document.createTextNode(
								bookList.get(i).getWaitingList().findElementByPosition(j).getElement().getId());

						// appending the values to readerIDNode
						readerIDNode.appendChild(readerIDText);
						bookNode.appendChild(readerIDNode);

						// adding bookNode to the root Books
						root.appendChild(bookNode);
					}
				}

				// add root to the document
				document.appendChild(root);

				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				String path = "WaitingList.xml";
				File f = new File(path);
				StreamResult result = new StreamResult(new PrintWriter(new FileOutputStream(f, false)));
				DOMSource source = new DOMSource(document);
				transformer.transform(source, result);

			} catch (TransformerFactoryConfigurationError | FileNotFoundException | TransformerException
					| ParserConfigurationException e) {

				e.printStackTrace();
			}
		}

	}

	public Document documentGenerator(String fileName) {

		/*
		 * This method is responsible for generating a Document as it will be used
		 * multiple times
		 */

		Document doc = null;
		try {
			File xmlDoc = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlDoc);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return doc;
	}

	public boolean searchRentedBooks(Reader user) {

		boolean hasRentedBook = false;
		for (Book.RentedBooks data : booksLog) {
			if (data.getReader().getId().equals(user.getId())) {

				System.out.println("------------------------------------------");
				System.out.println("Reader ID: " + data.getReader().getId());
				System.out.println("Book ID: " + data.getBook().getId());
				System.out.println("Date out: " + data.getDateOut());
				System.out.println("Date in: " + data.getDateIn());

				hasRentedBook = true;
			}

		}

		return hasRentedBook;

	}

	public void readRentedBooksFile() {

		/*
		 * This method will read from the Readers xml file and retrieve the information
		 * in it. Once I have the information of each reader I can create a reader
		 * object After the object is created I am going to add it into an array.
		 */

		Document doc = documentGenerator("RentedBooks.xml");

		String readerID = "";
		String bookId = "";
		String dateOut = "";
		String dateIn = "";

		NodeList readerNodeList = doc.getElementsByTagName("reader");

		for (int counter = 0; counter < readerNodeList.getLength(); counter++) {

			Node readerNode = readerNodeList.item(counter);

			if (readerNode.hasChildNodes() == true && readerNode.getNodeType() == Node.ELEMENT_NODE) {

				Element readerElement = (Element) readerNode;
				readerID = readerElement.getAttribute("id");

				NodeList bookList = readerElement.getElementsByTagName("book");

				for (int i = 0; i < bookList.getLength(); i++) {

					Node bookNode = bookList.item(i);
					if (bookNode.getNodeType() == Node.ELEMENT_NODE) {

						Element bookElement = (Element) bookNode;

						bookId = bookElement.getAttribute("id");
						dateOut = bookElement.getElementsByTagName("dateOut").item(0).getTextContent();
						dateIn = bookElement.getElementsByTagName("dateIn").item(0).getTextContent();

						book = searchBook(bookId);
						reader = searchReaders("", "", readerID);

						rentedBooks = book.new RentedBooks(book, reader, dateOut, dateIn);

						booksLog.add(rentedBooks);

					}

				}

			}

		}

	}

	public void readWaitingListFile() {

		/*
		 * This method will read from the Waiting List xml file and retrieve the
		 * information in it. Once I have the information of each reader I can create a
		 * reader object After the object is created I am going to add it into an array.
		 */

		Document doc = documentGenerator("WaitingList.xml");

		String bookID = "";
		String readerID = "";

		NodeList bookNodeList = doc.getElementsByTagName("book");

		for (int bookNodeListCounter = 0; bookNodeListCounter < bookNodeList.getLength(); bookNodeListCounter++) {

			Node bookNode = bookNodeList.item(bookNodeListCounter);

			if (bookNode.hasChildNodes() == true && bookNode.getNodeType() == Node.ELEMENT_NODE) {

				Element bookElement = (Element) bookNode;
				bookID = bookElement.getAttribute("id");

				NodeList readerIDNodeList = bookElement.getElementsByTagName("readerID");

				for (int readerIDNodeCounter = 0; readerIDNodeCounter < readerIDNodeList
						.getLength(); readerIDNodeCounter++) {

					Node readerIDNode = readerIDNodeList.item(readerIDNodeCounter);
					if (readerIDNode.getNodeType() == Node.ELEMENT_NODE) {

						Element readerIDElement = (Element) readerIDNode;
						readerID = readerIDElement.getTextContent();

						book = searchBook(bookID);
						reader = searchReaders("", "", readerID);

						book.getWaitingList().addLast(reader);

					}

				}

			}

		}

	}

	public void readBooksFile() {

		/*
		 * This method will read from the Books xml file and retrieve the information in
		 * it. Once I have the information of each reader I can create a reader object
		 * After the object is created I am going to add it into an array.
		 */

		Document doc = documentGenerator("Books.xml");

		// read array of reader elements
		// this array is called NodeList

		NodeList nodeList = doc.getElementsByTagName("book");

		for (int counter = 0; counter < nodeList.getLength(); counter++) {

			Node nNode = nodeList.item(counter);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				String id;
				String title;
				String author;
				String year;
				boolean available;

				Element eElement = (Element) nNode;
				id = eElement.getAttribute("id");
				title = eElement.getElementsByTagName("title").item(0).getTextContent();
				author = eElement.getElementsByTagName("author").item(0).getTextContent();
				year = eElement.getElementsByTagName("year").item(0).getTextContent();
				available = Boolean.parseBoolean(eElement.getElementsByTagName("available").item(0).getTextContent());

				book = new Book(id, title, author, year, available);
				bookList.add(book);

			}
		}

	}

	public void readReadersFile() {

		/*
		 * This method will read from the Readers xml file and retrieve the information
		 * in it. Once I have the information of each reader I can create a reader
		 * object After the object is created I am going to add it into an array.
		 */

		Document doc = documentGenerator("Readers.xml");

		// read array of reader elements
		// this array is called NodeList

		NodeList nodeList = doc.getElementsByTagName("reader");

		for (int counter = 0; counter < nodeList.getLength(); counter++) {

			Node nNode = nodeList.item(counter);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				String id;
				String fName;
				String lName;
				String email;
				String phone;

				Element eElement = (Element) nNode;
				id = eElement.getAttribute("id");
				fName = eElement.getElementsByTagName("fName").item(0).getTextContent();
				lName = eElement.getElementsByTagName("lName").item(0).getTextContent();
				email = eElement.getElementsByTagName("email").item(0).getTextContent();
				phone = eElement.getElementsByTagName("phone").item(0).getTextContent();

				reader = new Reader(id, fName, lName, email, phone);
				readerList.add(reader);

			}
		}

	}

	public void sortReaderList(String option) {

		/*
		 * linear search
		 * 
		 * The same method will check for id or name
		 */

		if (option.equals("id")) {

			int n = readerList.size();
			for (int i = 0; i < n - 1; i++) {

				for (int j = 0; j < n - 1; j++) {
					int readerID = Integer.parseInt(readerList.get(j).getId());
					int readerIDCompare = Integer.parseInt(readerList.get(j + 1).getId());

					if (readerID > readerIDCompare) {
						// swap arr[j+1] and arr[j]
						Reader temp = readerList.get(j);
						readerList.set(j, readerList.get(j + 1));
						readerList.set(j + 1, temp);
					}

				}

			}
			System.out.println("---------------------------------------------------------------");
			System.out.println("Here is the list of Readers in your File ordered by ID:");
			System.out.println("---------------------------------------------------------------");

			for (int count = 0; count < readerList.size(); count++) {
				System.out.println(readerList.get(count).getId() + "| " + readerList.get(count).getfName() + " "
						+ readerList.get(count).getlName());
			}

		}

		else if (option.equals("name")) {

			int n = readerList.size();
			for (int i = 0; i < n - 1; i++) {

				for (int j = 0; j < n - 1; j++)
					if (String.valueOf(readerList.get(j).getfName()).compareTo(readerList.get(j + 1).getfName()) > 0)

					{
						// swap arr[j+1] and arr[j]
						Reader temp = readerList.get(j);
						readerList.set(j, readerList.get(j + 1));
						readerList.set(j + 1, temp);
					}

			}

			System.out.println("---------------------------------------------------------------");
			System.out.println("Here is the list of Readers in your File ordered by Name:");
			System.out.println("---------------------------------------------------------------");

			for (int count = 0; count < readerList.size(); count++) {
				System.out.println(readerList.get(count).getId() + "| " + readerList.get(count).getfName() + " "
						+ readerList.get(count).getlName());
			}
		}

	}

	public void sortBooksList(String option) {

		/*
		 * linear search
		 * 
		 * The same method will check for title or author
		 */

		if (option.equals("title")) {

			int n = bookList.size();
			for (int i = 0; i < n - 1; i++) {

				for (int j = 0; j < n - 1; j++)
					if (String.valueOf(bookList.get(j).getTitle()).compareTo(bookList.get(j + 1).getTitle()) > 0)

					{
						// swap arr[j+1] and arr[j]
						Book temp = bookList.get(j);
						bookList.set(j, bookList.get(j + 1));
						bookList.set(j + 1, temp);
					}

			}

			System.out.println("---------------------------------------------------------------");
			System.out.println("Here is the list of Books in your File ordered by Title:");
			System.out.println("---------------------------------------------------------------");

			for (int i = 0; i < n; i++) {

				System.out.println(bookList.get(i).getId() + "| Book title: " + bookList.get(i).getTitle()
						+ "| Author: " + bookList.get(i).getAuthor());
			}

		} else if (option.equals("author")) {

			int n = bookList.size();
			for (int i = 0; i < n - 1; i++) {

				for (int j = 0; j < n - 1; j++)
					if (String.valueOf(bookList.get(j).getAuthor()).compareTo(bookList.get(j + 1).getAuthor()) > 0)

					{
						// swap arr[j+1] and arr[j]
						Book temp = bookList.get(j);
						bookList.set(j, bookList.get(j + 1));
						bookList.set(j + 1, temp);
					}

			}

			System.out.println("---------------------------------------------------------------");
			System.out.println("Here is the list of Books in your File ordered by Title:");
			System.out.println("---------------------------------------------------------------");
			for (int i = 0; i < n; i++) {

				System.out
						.println(bookList.get(i).getId() + "| Author: " + bookList.get(i).getAuthor() + "| Book title: "
								+ bookList.get(i).getTitle() + "| Available: " + bookList.get(i).isAvailable());
			}

		}

	}

	public Book searchBook(String userInput) {

		/*
		 * this method returns null if book has not been found. it means that the title,
		 * author or id inserted is invalid
		 */

		for (Book book : bookList) {
			if (book.getTitle().equalsIgnoreCase(userInput) || book.getAuthor().equalsIgnoreCase(userInput)
					|| book.getId().equals(userInput)) {
				return book;

			}
		}
		return null;
	}

	public Reader searchReaders(String firstName, String lastName, String userID) {

		/*
		 * this method returns null if reader has not been found. it means that the
		 * reader first Name, Last Name, or id inserted is invalid
		 */

		for (Reader reader : readerList) {

			if (reader.getId().equals(userID)
					|| reader.getfName().equalsIgnoreCase(firstName) && reader.getlName().equalsIgnoreCase(lastName)) {
				return reader;
			}

		}
		return null;
	}

}
