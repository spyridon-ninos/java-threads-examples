package com.ninos.d_wrong_synchronized_blocks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class App {
	public static void main(String[] args) {
		final int numOfThreads = 10;

		// shared resource is protected
		SharedResource sharedResource = new SharedResource();

		// we create a thread pool which will create the multithreading context
		ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);

		// this is what we want to run with each thread
		Runnable runnable = () -> sharedResource.addOne();

		// add one to the variable inside sharedResource, numOfThread times
		IntStream.range(0, numOfThreads).forEach(i -> executorService.submit(runnable));

		// we wait for all the threads to finish their execution
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " value: " + sharedResource.getValue());
		executorService.shutdownNow();
	}
}

