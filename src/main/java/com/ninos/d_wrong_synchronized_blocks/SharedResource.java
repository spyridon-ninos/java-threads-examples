package com.ninos.d_wrong_synchronized_blocks;

import java.util.Random;

/*
 * although writing to the value is protected by the lockObject's lock,
 * reading the value is not. As such the synchronisation mechanism fails
 * to protect the resource
 */
public class SharedResource {
	private int value = 0;

	// only used to print the value in main
	public int getValue() {
		return value;
	}

	private Object lockObject = new Object();

	public void addOne() {
		int oldValue = value;
		synchronized (lockObject) {
			try {
				long timeout = Integer.toUnsignedLong(new Random().nextInt(100));
				System.out.println(Thread.currentThread().getName() + " old value: " + value + " sleeping for: " + timeout + " ms");
				Thread.sleep(timeout);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			value = oldValue + 1;
		}
	}
}
