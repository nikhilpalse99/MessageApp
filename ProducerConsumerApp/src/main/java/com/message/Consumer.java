package com.message;

public class Consumer implements Runnable {

	private final MessageQueueHandler queueHandler;

    public Consumer(MessageQueueHandler queueHandler) {
        this.queueHandler = queueHandler;
    }

    @Override
    public void run() {
        queueHandler.processMessages();
    }
	
}
