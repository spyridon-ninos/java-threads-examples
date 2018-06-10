package com.ninos.j_optimistic_stamped_lock;

import java.util.concurrent.locks.StampedLock;

/*
 * reading from and writing to the value are protected by a read and a write lock
 *
 * see https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html
 */
public class SharedResource {
	private int value = 0;

	private StampedLock stampedLock = new StampedLock();

	public int getValue() {
		long stamp = stampedLock.tryOptimisticRead();
		int myValue = value;

		System.out.println(Thread.currentThread().getName() + " got an optimistic lock. Value: " + myValue);

		// I really hate C-style check for NOT (i.e. using the !)
		if (stampedLock.validate(stamp) == false) {
			System.out.println(Thread.currentThread().getName() + " someone else has the write lock. Trying to get a lock.");

			stamp = stampedLock.readLock();
			try {
				myValue = value;
			} finally {
				stampedLock.unlock(stamp);
			}
		}

		System.out.println(Thread.currentThread().getName() + " value is: " + myValue);
		return myValue;
	}

	public void addOne() {

		long stamped = stampedLock.writeLock();

		try {
			try {
				long timeout = 1000L;
				System.out.println(Thread.currentThread().getName() + " old value: " + value + " sleeping for: " + timeout + " ms");
				Thread.sleep(timeout);
				value++;
				System.out.println(Thread.currentThread().getName() + " new value: " + value );
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		} finally {
			stampedLock.unlock(stamped);
		}
	}
}
