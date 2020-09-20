package server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import client.RunClient;
import dao.PlayerDAO;

public class RunServer {

	private static final Logger logger = LogManager.getLogger();

	private int port = 4886;

	public static void main(String[] args) {
		new RunServer().run();
	}

	public void run() {
		logger.info("서버 실행");
//		ExecutorService pool = Executors.newFixedThreadPool(5); // 최대 스레드가 2개인 스레드풀 생성
//		PlayerDAO dao = new PlayerDAO();

		try (ServerSocket serverSocket = new ServerSocket(port)) {

//			dao.setServerIP();

//			List<Runnable> runnableTasks = new ArrayList<>();

			while (true) {
				Socket socket = serverSocket.accept(); // 접속한 소켓 받는다
				
//				new PrintWriter(new OutputStreamWriter(socket.getOutputStream())).println();
				
				new Thread(new ServerReceiver(socket)).start();

//				pool.execute(new ServerReceiver(socket));

//				Runnable task = () -> {
//					try {
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				};
//
//				runnableTasks.add(task);
//				runnableTasks.forEach(executor::executeTask);
//				executor.waitForAllThreadsToCompletion();

			} // while

		} catch (BindException e) {
			logger.error("서버가 이미 실행중입니다");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
//			pool.shutdown();
		}

	} // run();

//	private static final int DEFAULT_THREAD_COUNT = 2;
//
//	private final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors
//			.newFixedThreadPool(DEFAULT_THREAD_COUNT);
//
//	public void executeTask(Runnable runnable) {
//		executorService.execute(runnable);
//	}
//
//	public void waitForAllThreadsToCompletion() {
//		executorService.shutdown();
//		while (!executorService.isTerminated()) {
//			try {
//				new ServerReceiver(socket);
//			} catch (InterruptedException e) { }
//		}
//		executorService.shutdownNow();
//	}
//
//	public void awaitTermination() {
//		try {
//			executorService.shutdown();
//
//			if (!executorService.awaitTermination(3000, TimeUnit.MILLISECONDS)) {
//				executorService.shutdownNow();
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
} // Accept();