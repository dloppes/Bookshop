import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Database {

	Reader reader;
	Book book;
	protected ArrayList<Reader> readerList = new ArrayList<Reader>();
	protected ArrayList<Book> bookList = new ArrayList<Book>();
	QueueObject waitingListObject = new QueueObject(30);
	protected Scanner myReader;

	public void readWaitingListFile() {

		/*
		 * This method will read from the Readers xml file and retrieve the information
		 * in it. Once I have the information of each reader I can create a reader
		 * object After the object is created I am going to add it into an array.
		 */

		try {
			File xmlDoc = new File("WaitingList.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlDoc);

			// read array of reader elements
			// this array is called NodeList

			NodeList nodeList = doc.getElementsByTagName("list");
		

			for (int counter = 0; counter < nodeList.getLength(); counter++) {

				Node nNode = nodeList.item(counter);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					
					int bookID;
					int readerID=0;

					Element eElement = (Element) nNode;
					bookID = Integer.parseInt(eElement.getElementsByTagName("bookId").item(0).getTextContent());

					NodeList nlList = eElement.getElementsByTagName("readerId");
					
				
					for (int i = 0; i < nlList.getLength(); i++) {

						Node nNode1 = nlList.item(i);
						if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
							

							
							Element eElement0 = (Element) nNode1;

							
							readerID = Integer
									.parseInt(eElement0.getTextContent());

							waitingListObject.Enqueue(new WaitingListObject(bookID, readerID));

						}
						
					}
					

				}

			}
			
			while(waitingListObject.First() != null) {
				System.out.println((WaitingListObject) waitingListObject.Dequeue());
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void readReadersFile() {

		/*
		 * This method will read from the Readers xml file and retrieve the information
		 * in it. Once I have the information of each reader I can create a reader
		 * object After the object is created I am going to add it into an array.
		 */

		try {
			File xmlDoc = new File("Readers.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlDoc);

			// read array of reader elements
			// this array is called NodeList

			NodeList nodeList = doc.getElementsByTagName("reader");

			for (int counter = 0; counter < nodeList.getLength(); counter++) {

				Node nNode = nodeList.item(counter);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					int id;
					String fName;
					String lName;
					String email;
					String phone;

					Element eElement = (Element) nNode;
					id = Integer.parseInt(eElement.getAttribute("id"));
					fName = eElement.getElementsByTagName("fName").item(0).getTextContent();
					lName = eElement.getElementsByTagName("lName").item(0).getTextContent();
					email = eElement.getElementsByTagName("email").item(0).getTextContent();
					phone = eElement.getElementsByTagName("phone").item(0).getTextContent();

					reader = new Reader(id, fName, lName, email, phone);
					readerList.add(reader);

				}
			}

		} catch (Exception e) {

		}
	}

	public void readBooksFile() {

		/*
		 * This method will read from the Readers xml file and retrieve the information
		 * in it. Once I have the information of each reader I can create a reader
		 * object After the object is created I am going to add it into an array.
		 */

		try {
			File xmlDoc = new File("Books.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlDoc);

			// read array of reader elements
			// this array is called NodeList

			NodeList nodeList = doc.getElementsByTagName("book");

			for (int counter = 0; counter < nodeList.getLength(); counter++) {

				Node nNode = nodeList.item(counter);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					int id;
					String title;
					String author;
					String year;
					boolean available;

					Element eElement = (Element) nNode;
					id = Integer.parseInt(eElement.getAttribute("id"));
					title = eElement.getElementsByTagName("title").item(0).getTextContent();
					author = eElement.getElementsByTagName("author").item(0).getTextContent();
					year = eElement.getElementsByTagName("year").item(0).getTextContent();
					available = Boolean
							.parseBoolean(eElement.getElementsByTagName("available").item(0).getTextContent());

					book = new Book(id, title, author, year, available);
					bookList.add(book);

				}
			}

		} catch (Exception e) {

		}
	}

	public void sortReaderList(String option) {

		if (option.equals("id")) {

			Collections.sort(readerList, new Comparator<Reader>()

			{

				@Override
				public int compare(Reader o1, Reader o2) {

					return Integer.valueOf(o1.getId()).compareTo(o2.getId());
				}
			});

			System.out.println("---------------------------------------------------------------");
			System.out.println("Here is the list of Readers in your File ordered by ID");
			for (int count = 0; count < readerList.size(); count++) {
				System.out.println(readerList.get(count).getId() + "| " + readerList.get(count).getfName() + " "
						+ readerList.get(count).getlName());

			}

		}

		else if (option.equals("name")) {

			Collections.sort(readerList, new Comparator<Reader>()

			{

				@Override
				public int compare(Reader o1, Reader o2) {

					return String.valueOf(o1.getfName()).compareTo(o2.getfName());
				}
			});

			System.out.println("---------------------------------------------------------------");
			System.out.println("Here is the list of Readers in your File ordered by Name");
			for (

					int count = 0; count < readerList.size(); count++) {
				System.out.println(readerList.get(count).getId() + "| " + readerList.get(count).getfName() + " "
						+ readerList.get(count).getlName());

			}

		}

	}

	public void sortBooksList(String option) {

		if (option.equals("title")) {

			Collections.sort(bookList, new Comparator<Book>()

			{

				@Override
				public int compare(Book o1, Book o2) {
					return String.valueOf(o1.getTitle()).compareTo(o2.getTitle());
				}
			});

			System.out.println("---------------------------------------------------------------");
			System.out.println("Here is the list of Books in your File ordered by Title");
			for (int count = 0; count < readerList.size(); count++) {
				System.out.println(bookList.get(count).getId() + "| Book title: " + bookList.get(count).getTitle()
						+ "| Author: " + bookList.get(count).getAuthor());

			}

		}

		else if (option.equals("author")) {

			Collections.sort(bookList, new Comparator<Book>()

			{

				@Override
				public int compare(Book o1, Book o2) {
					return String.valueOf(o1.getAuthor()).compareTo(o2.getAuthor());
				}
			});

			System.out.println("---------------------------------------------------------------");
			System.out.println("Here is the list of Books in your File ordered by Author");
			for (

					int count = 0; count < readerList.size(); count++) {
				System.out.println(bookList.get(count).getId() + "| Author: " + bookList.get(count).getAuthor()
						+ "| Book title: " + bookList.get(count).getTitle());

			}

		}

	}

	public boolean setBookAsAvailable(String bookName) {

		try {
			File xmlDoc = new File("Books.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlDoc);
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

	public Book searchBook(String userInput, String searchBy) {

		if (searchBy.equals("title")) {
			for (Book book : bookList) {
				if (book.getTitle().equals(userInput) && book.isAvailable() == true) {
					return book;
				}

			}
			return null;
		} else if (searchBy.equals("author")) {
			for (Book book : bookList) {
				if (book.getAuthor().equals(userInput) && book.isAvailable() == true) {
					return book;
				}

			}
		}
		return null;
	}

}
