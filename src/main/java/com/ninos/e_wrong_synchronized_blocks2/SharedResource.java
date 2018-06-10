package com.ninos.e_wrong_synchronized_blocks2;

import java.util.Random;

/*
 * reading from and writing to the value are protected
 * by different locks. Thus, reading and writing are not
 * synchronised with each other
 */
public class SharedResource {
	private int value = 0;

	// only used to print the value in main
	public int getValue() {
		return value;
	}

	private Object firstLockObject = new Object();
	private Object secondLockObject = new Object();

	public void addOne() {
		int oldValue;

		synchronized (firstLockObject) {
			oldValue = value;
		}

		synchronized (secondLockObject) {
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
