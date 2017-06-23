package com.bgaborg.threadobjects;

import com.bgaborg.AppPart;

/**
 * Thread Objects
 * <p/>
 * Each thread is associated with an instance of the class Thread. There are two basic strategies for using Thread objects to create a concurrent application.
 * <p/>
 * * To directly control thread creation and management, simply instantiate Thread each time the application needs to initiate an asynchronous task.
 * * To abstract thread management from the rest of your application, pass the application's tasks to an executor.
 * <p/>
 * This section documents the use of Thread objects. Executors are discussed with other high-level concurrency objects.
 * <p/>
 * Created by bg
 */
public class ThreadObjectsPart implements AppPart {

    @Override
    public void startExample() throws Exception {
        threadMessage("----start of threadobjects examples----\n");
        Thread helloRunnable = new Thread(new HelloRunnable());
        Thread helloThread = new HelloThread();
        Thread sleepMessages = new SleepMessages();

        /**
         * Interrupts with some logic
         */
        Thread interruptThread = new Thread(new Interrupts());
        interruptThread.start();
        Thread.sleep(500);
        interruptThread.interrupt();
        Thread.sleep(1000);
        interruptThread.interrupt();

        /**
         * The join method allows one thread to wait for the completion of another.
         * If t is a Thread object whose thread is currently executing, causes the current
         * thread to pause execution until t's thread terminates. Overloads of join allow the programmer to
         * specify a waiting period. However, as with sleep, join is dependent on the OS for timing, so you
         * <b>should not assume that join will wait exactly as long as you specify</b>
         *
         * Like sleep, join responds to an interrupt by exiting with an InterruptedException.
         */
        interruptThread.join();

        helloRunnable.start();
        helloThread.start();

        helloRunnable.join();
        helloThread.join();

        /**
         * Testing sleepMessages with join and interrupt
         */
        sleepMessages.start();
        long startTime = System.currentTimeMillis();
        threadMessage("Waiting to sleepMessages to finish.");
        while (sleepMessages.isAlive()) {
            threadMessage("Still waiting...");
            sleepMessages.join(500);
            if (((System.currentTimeMillis() - startTime) > 2000) && sleepMessages.isAlive()) {
                threadMessage("Interrputing thread.");
                sleepMessages.interrupt();
                sleepMessages.join();
            }
        }
        threadMessage("SleepMessages stopped.");
        threadMessage("----end of threadobjects examples----\n\n");
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                threadName,
                message);
    }
}
