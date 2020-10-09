package server;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class TestMain {

	static int index = 1;

	public static void main(String[] args) {

		// 새로운 쓰레드 추가시 출력 문구를 위한 ThreadFactory
		ThreadFactory factory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				System.out.println("Runnable"+"["+ index++ +"]"+" is created.");
				return thread;
			}
		};

		// 쓰레드를 5개까지만 만드는 쓰레드풀 생성
		ExecutorService pool = Executors.newFixedThreadPool(5, factory);
		// TEST CODE
		try {

			// 아이템 100개를 쓰레드풀에 삽입 및 실행
			for(int i = 0 ; i < 100; i++){
				Runnable car = new Car(i);
				pool.execute(car);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.shutdown();
		}
	}
}

// 테스트할 용도의 Runnable 클래스
class Car implements Runnable{
	int index;
	public Car(int i) {
		index = i;
	}
	@Override
	public void run() {
		System.out.println(index);
	}
}