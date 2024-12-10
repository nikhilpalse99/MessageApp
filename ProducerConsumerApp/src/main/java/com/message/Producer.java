package com.message;

public class Producer implements Runnable{
	
	private final MessageQueueHandler queueHandler;
    private int messageCount = 0;

    public Producer(MessageQueueHandler queueHandler) {
        this.queueHandler = queueHandler;
    }

    @Override
    public void run() {
        while (messageCount < 10) {
            String message = "Message " + messageCount++;
            queueHandler.addMessage(message);
            try {
                Thread.sleep(500); // Simulate time delay between message production
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
