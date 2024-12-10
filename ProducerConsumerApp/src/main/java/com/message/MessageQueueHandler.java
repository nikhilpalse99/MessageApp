package com.message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class MessageQueueHandler {

	private static final Logger logger = Logger.getLogger(MessageQueueHandler.class.getName());
    private final BlockingQueue<String> queue;
    private int totalMessagesProcessed = 0;
    private int totalErrors = 0;

    public MessageQueueHandler() {
        this.queue = new LinkedBlockingQueue<>();
    }

    // Method for adding messages to the queue
    public void addMessage(String message) {
        try {
            queue.put(message);
            logger.info("Message added to queue: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            totalErrors++;
            logger.warning("Error while adding message: " + e.getMessage());
        }
    }

    // Method for processing messages from the queue
    public void processMessages() {
        while (true) {
            try {
                String message = queue.take();
                logger.info("Processing message: " + message);
                totalMessagesProcessed++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                totalErrors++;
                logger.warning("Error while processing message: " + e.getMessage());
            }
        }
    }

    // Getters for tracking success and failure
    public int getTotalMessagesProcessed() {
        return totalMessagesProcessed;
    }

    public int getTotalErrors() {
        return totalErrors;
    }
}
