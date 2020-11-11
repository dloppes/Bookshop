import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.omg.PortableServer.ServantActivatorHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Database {

	Reader reader;
	Book book;
	Book.RentedBooks rentedBooks;
	protected ArrayList<Reader> readerList = new ArrayList<Reader>();
	protected ArrayList<Book> bookList = new ArrayList<Book>();
	protected ArrayList<Book.RentedBooks> booksLog = new ArrayList<>();
	protected Scanner myReader;
	MyLinkedList waitingList;

	public Document documentGenerator(String fileName) {

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

	public boolean searchRentedBooksFile(Reader user) {

		/*
		 * This method checks for a specific readerID, if find it keeps going,
		 * else,returns false. Once found the ID the next step is to see its nodes First
		 * the books tag and its child is checked and subsequently the book tag and its
		 * child Lastly, if there is info it is retrieved, a object is created w/ the
		 * info and stored into an array list
		 */

		Document doc = documentGenerator("RentedBooks.xml");

		try {
			doc.normalize();

			XPath xPath = XPathFactory.newInstance().newXPath();
			String expr = String.format("/rentedList/reader[./id='%s']", user.getId());
			NodeList idNodeList = (NodeList) xPath.compile(expr).evaluate(doc, XPathConstants.NODESET);

			// first Node List is ID
			for (int counter = 0; counter < idNodeList.getLength(); counter++) {

				Node nNode = idNodeList.item(counter);

				Element eElement = (Element) nNode;

				// Now I have created a NodeList to find the books tags
				NodeList booksNodeList = eElement.getElementsByTagName("books");

				// checking the length and if the node has any child
				for (int i = 0; i < booksNodeList.getLength(); i++) {
					Node booksNode = booksNodeList.item(i);

					if (booksNode.hasChildNodes() == false) {
						return false;
					}

					else {

						// last NodeList to be created to check info is the bookNodeList
						NodeList bookNodeList = eElement.getElementsByTagName("book");

						for (int j = 0; j < bookNodeList.getLength(); j++) {
							// the same goes for bookNode. that is where the information we are looking for
							// is.
							Node bookNode = bookNodeList.item(j);

							if (bookNode.hasChildNodes() == false) {
								return false;
							}

							else {

								String bookID = "";
								String dateOut = "";
								String dateIn = "";

								Element eElement0 = (Element) bookNode;
								bookID = eElement0.getElementsByTagName("bookID").item(0).getTextContent();
								dateOut = eElement0.getElementsByTagName("dateOut").item(0).getTextContent();
								dateIn = eElement0.getElementsByTagName("dateIn").item(0).getTextContent();

								rentedBooks = book.new RentedBooks(user.getId(), bookID, dateOut, dateIn);

								ArrayList<Book.RentedBooks> booksLog = new ArrayList<>();

								booksLog.add(rentedBooks);

								for (int count = 0; count < booksLog.size(); count++) {

									System.out.println("------------------------------------------");
									System.out.println("Reader ID: " + booksLog.get(count).getReaderID());
									System.out.println("Book ID: " + booksLog.get(count).getBookID());
									System.out.println("Date out: " + booksLog.get(count).getDateOut());
									System.out.println("Date in: " + booksLog.get(count).getDateIn());

								}

							}
						}
					}
				}
			}
		} catch (Exception e) {

			System.err.println(e);

		}
		return true;

	}

	public void readWaitingListFile() {

		/*
		 * This method will read from the Readers xml file and retrieve the information
		 * in it. Once I have the information of each reader I can create a reader
		 * object After the object is created I am going to add it into an array.
		 */

		Document doc = documentGenerator("Books.xml");

		// read array of reader elements
		// this array is called NodeList

		NodeList nodeList = doc.getElementsByTagName("waitingList");

		for (int counter = 0; counter < nodeList.getLength(); counter++) {

			Node nNode = nodeList.item(counter);
			if (nNode.hasChildNodes() == false)
				;

			{
				String empty = "null";
			}
			if (nNode.hasChildNodes() == true && nNode.getNodeType() == Node.ELEMENT_NODE) {

				String readerID = "";

				Element eElement = (Element) nNode;
				readerID = eElement.getElementsByTagName("readerID").item(0).getTextContent();

				NodeList nlList = eElement.getElementsByTagName("readerId");

				for (int i = 0; i < nlList.getLength(); i++) {

					Node nNode1 = nlList.item(i);
					if (nNode1.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement0 = (Element) nNode1;

						readerID = eElement0.getTextContent();

						waitingList = new MyLinkedList();

						MyLinkedList.Node nodeElement = waitingList.new Node(searchReaders("","", readerID));
						book.getWaitingList().addLast(nodeElement);

					}

				}

			}

		}

	}

	public void readBooksFile() {

		/*
		 * This method will read from the Readers xml file and retrieve the information
		 * in it. Once I have the information of each reader I can create a reader
		 * object After the object is created I am going to add it into an array.
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

		if (option.equals("id")) {

			int n = readerList.size();
			for (int i = 0; i < n - 1; i++) {

				for (int j = 0; j < n - 1; j++)
					if (String.valueOf(readerList.get(j).getId()).compareTo(readerList.get(j + 1).getId()) > 0)

					{
						// swap arr[j+1] and arr[j]
						Reader temp = readerList.get(j);
						readerList.set(j, readerList.get(j + 1));
						readerList.set(j + 1, temp);
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

				System.out.println(bookList.get(i).getId() + "| Author: " + bookList.get(i).getAuthor()
						+ "| Book title: " + bookList.get(i).getTitle());
			}

		}

	}

	public boolean setBookAsAvailable(String bookName) {

		Document doc = documentGenerator("Books.xml");

		try {
			doc.normalize();

			XPath xPath = XPathFactory.newInstance().newXPath();
			String expr = String.format("/books/book[./title='%s']", bookName);
			NodeList nl = (NodeList) xPath.compile(expr).evaluate(doc, XPathConstants.NODESET);
			if (nl.getLength() <= 0) {

				return false;
			}
			for (int j = 0; j < nl.getLength(); j++) {
				Element e = (Element) nl.item(j);
				e.getElementsByTagName("available").item(0).setTextContent("true");
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("Books.xml"));
			transformer.transform(source, result);

		} catch (Exception e) {

			System.err.println(e);

		}
		return true;

	}

	public Book searchBook(String userInput) {

		for (Book book : bookList) {
			if (book.getTitle().equalsIgnoreCase(userInput) || book.getAuthor().equalsIgnoreCase(userInput)) {
				return book;

			}
		}
		return null;
	}

	public Reader searchReaders(String firstName, String lastName, String userID) {

		for (Reader reader : readerList) {

			if (reader.getId().equals(userID)
					|| reader.getfName().equalsIgnoreCase(firstName) && reader.getlName().equalsIgnoreCase(lastName)) {
				return reader;
			}

		}
		return null;
	}

}
