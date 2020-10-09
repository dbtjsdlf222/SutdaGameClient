package server;
import java.util.LinkedList;

public class ThreadPoolQueue {

	/* item을 저장할 큐 선언 */
	private LinkedList<Object> queue = new LinkedList<Object>();

	/* 큐 최대 사이즈 선언 */
	private int MAX_QUEUE_SIZE = 5;

	public ThreadPoolQueue(int MAX_QUEUE_SIZE) {
		this.MAX_QUEUE_SIZE = MAX_QUEUE_SIZE;
	}

	/* 아이템 삽입 */
	public synchronized void enqueue(Object item) throws InterruptedException {
		// 현재 큐가 최대 사이즈면 멈춤
		while( queue.size() == MAX_QUEUE_SIZE ){
			wait();
		}

		// 현재 큐가 비어있으면 일어나라!! 일해!!
		if( queue.size() == 0 ){
			notifyAll();
		}

		queue.add(item);
	}

	/* 아이템 반환 */
	public synchronized Object dequeue() throws InterruptedException {
		// 반환할 아이템이 없으면 멈춤
		while( queue.size() == 0 ){
			wait();
		}

		// 반환할 아이템이 가득찼다? 일어나서 일해!!
		if( queue.size() == MAX_QUEUE_SIZE ){
			notifyAll();
		}

		return queue.remove(0);
	}
}