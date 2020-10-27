
public class WaitingListObject {

	protected int bookID;
	protected int readerID;

	public WaitingListObject(int bookID, int readerID) {
		super();
		this.bookID = bookID;
		this.readerID = readerID;
	}

	public int getBookID() {
		return bookID;
	}

	public int getReaderID() {
		return readerID;
	}

	@Override
	public String toString() {
		return "WaitingListObject [bookID=" + bookID + ", readerID=" + readerID + "]";
	}

}
