import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

	Reader reader = new Reader();
	protected ArrayList<Reader> readerList = new ArrayList<Reader>();
	protected Scanner myReader;

	public Database() {

	}

	public void readBooksFile() {

		
		try {
			File myObj = new File("Readers");
			myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
