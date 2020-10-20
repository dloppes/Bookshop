import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Database {

	Reader reader = new Reader();
	protected ArrayList<Reader> readerList = new ArrayList<Reader>();
	protected Scanner myReader;

	public Database() {

		readReadersFile();
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

				}
			}

		} catch (Exception e) {

		}
	}
}
