package com.ninos.i_stamped_lock;

public class App {
	public static void main(String[] args) throws InterruptedException {

		SharedResource sharedResource = new SharedResource();

		Runnable reader = () -> sharedResource.getValue();
		Runnable writer = () -> sharedResource.addOne();

		Thread t1 = new Thread(reader,"reader");
		Thread t2 = new Thread(writer, "writer");

		t2.start();
		t1.start();
	}
}

