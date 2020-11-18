
public class MyLinkedList {

	private Node first;
	private Node last;
	private int size;

	public MyLinkedList() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	public void addFirst(Node newElement) {
		if (size == 0) {
			last = newElement;
		}
		newElement.setNext(first);
		first = newElement;
		size++;
	}

	public void addLast(Reader newElement) {
		
		Node node = new Node(newElement);
		if (this.size == 0) {
			first = node;
			last = node;
		}
		else {
		last.setNext(node);
		last = node;
		}
		size++;
	}

	public Node removeFirst() {
		if (size == 0) {
			return null;
		}
		if (size == 1) {
			last = null;
		}
		Node toReturn = first;
		first = first.getNext();
		size--;
		return toReturn;
	}

	public Node findElementByPosition(int position) {
		if (size == 0 || position >= size - 1) {
			return null;
		}
		Node current = first;
		int counter = 0;
		while (counter < position) {
			current = current.getNext();
			counter++;
		}
		return current;
	}

	public Node removeLast() {

		Node toReturn = last;
		if (size == 0) {
			return toReturn;
		}
		if (size == 1) {
			first = null;
			last = null;
			size--;
			return toReturn;
		}

		Node secondLast = findElementByPosition(size - 2);
		secondLast.setNext(null);
		last = secondLast;
		size--;
		return toReturn;

	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {

		String toReturn = "[ ";
		Node current = first;
		while (current != null) {
			toReturn += current.toString() + " ";
			current = current.getNext();
		}
		toReturn += "]";
		return toReturn;

	}
	
	public class Node {

		private Reader element;
		private Node next;
		
		public Node(Reader element) {
			this.element = element;
			this.next = null;
		}

		public Reader getElement() {
			return element;
		}

		public void setElement(Reader element) {
			this.element = element;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
		
//		@Override 
//		public String toString() {
//			return this.getElement().getEmail();
//		}
		
	}

}
