
public class tester {
	
	public static void main(String[] args) {
		
		QueueObject wlObject = new QueueObject(10);
		
		wlObject.Enqueue("Jack");
		wlObject.Enqueue(new Integer(1));
		
		System.out.println(wlObject.Dequeue());
		System.out.println(wlObject.Dequeue());
		
		
	}

}
