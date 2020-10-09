package server;
import java.util.ArrayList;
import java.util.List;

/* 쓰레드풀 클래스 */
public class ThreadPool {

	private ThreadPoolQueue queue;
	private List<ThreadPoolRunnable> runnableList = new ArrayList<ThreadPoolRunnable>();
	private volatile boolean running = true;

	/* 쓰레드풀 초기화 */
	public ThreadPool(int MAX_THREAD_NUM, int MAX_QUEUE_SIZE) {
		queue = new ThreadPoolQueue(MAX_QUEUE_SIZE);
		for(int i = 0 ; i < MAX_THREAD_NUM; i++){
			runnableList.add(new ThreadPoolRunnable(i, queue));
		}
		for( ThreadPoolRunnable r : runnableList ){
			new Thread(r).start();
		}
	}

	/* 쓰레드풀 실행 */
	public synchronized void execute(Runnable item) throws Exception{
		if( !running ){
			throw new Exception("Thread Pool is not running.");
		}
		queue.enqueue(item);
	}

	/* 쓰레드풀 정지 */
	public synchronized void stop() throws InterruptedException {
		running = false;
		for( ThreadPoolRunnable r : runnableList ){
			r.stop();
		}
	}

	/* Runnable console 디버깅 플래그 설정 */
	public void toggleDebugWithRunnable(boolean flag){
		for( ThreadPoolRunnable r : runnableList ){
			r.toggleDebug(flag);
		}
	}

	/* Queue console 디버깅 플래그 설정 */
	public void toggleDebugWithQueue(boolean flag){
		queue.toggleDebug(flag);
	}
}