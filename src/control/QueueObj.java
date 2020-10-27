package control;

public class QueueObj {
	private int maxsize;
	private int front;
	private int rear;
	private int nItems;
	private Object[] elements;

	public QueueObj(int maxSize) {
		this.maxsize = maxSize;
		front = 0;
		rear = -1;
		nItems = 0;
		elements = new Object[maxsize];
	}

	public void enqueue(Object x) {
		if (rear == maxsize - 1)
			rear = -1;

		elements[++rear] = x;
		nItems++;
	}

	public Object dequeue() {
		Object result = elements[front];
		front++;

		if (front == maxsize)
			front = 0;

		nItems--;
		return result;
	}

	public boolean isEmpty() {
		return (nItems == 0);
	}

	@SuppressWarnings("unused")
	public static String flip(String x) {
		String y = "";

		for (int i = 0; i < x.length(); i++) {
			y += flipp(x.charAt(i));
		}
		for (int i = y.length() - 1; i >= 0; i--) {
			if (y.charAt(i) == '1') {
				y = y.substring(0, i) + "0" + y.substring(i + 1);
			} else
				y = y.substring(0, i) + "1" + y.substring(i + 1);
			break;
		}
		return y;

	}

	public static char flipp(char c) {
		return (c == '0') ? '1' : '0';
	}

}
