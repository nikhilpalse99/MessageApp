package com.message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerConsumerAppApplication {

	public static void main(String[] args) {
        // Create MessageQueueHandler instance
        MessageQueueHandler queueHandler = new MessageQueueHandler();

        // Create ExecutorService for managing producer and consumer threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Create producer and consumer instances
        Producer producer = new Producer(queueHandler);
        Consumer consumer = new Consumer(queueHandler);

        // Submit tasks to the executor
        executorService.submit(producer);
        executorService.submit(consumer);

        // Allow the threads to run for some time before shutdown
        try {
            Thread.sleep(10000); // Let the system run for 10 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Shut down executor service
        executorService.shutdown();

        // Print statistics
        System.out.println("Total messages processed: " + queueHandler.getTotalMessagesProcessed());
        System.out.println("Total errors encountered: " + queueHandler.getTotalErrors());
    }

}
