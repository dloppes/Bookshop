
public class QueueObject {
	
	private Object[] data;
	private int size;
	private int front;
	private int rear;
	private int capacity;
	
	
	public QueueObject(int capacity) {
		this.capacity = capacity;
		this.data = new Object[capacity];
		this.front = -1;
		this.rear = -1;
		this.size = 0;
	}
	
	
	public boolean Enqueue(Object newElement) {
		
		if(rear >= capacity -1) {
			return false;
		}
		if(front == -1) {
			front++;
		}
		rear++;
		data[rear] = newElement;
		size++;
		return true;
	}

	
	public Object Dequeue() {

		if(size == 0) {
			return null;
		}
		Object toReturn = data[front];
		data[front] = null;
		front++;
		size--;
		return toReturn;
	}

	public Object First() {

		if(size == 0) {
			return null;
		}
		return data[front];
	}

	
	public Object Last() {

		if(size == 0) {
			return null;
		}
		return data[rear];
	}

	
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}


	public boolean isEmpty() {

		if(size == 0) {
			return true;
		}
		return false;
	}
	
	
	public String toString() {
		
		String toReturn = "[ ";
		
		for(int i = front; i<= rear; i++) {
			toReturn += data[i] + " ";
			
		}
		
		toReturn = " ]";
		
		return toReturn;
	}

}
