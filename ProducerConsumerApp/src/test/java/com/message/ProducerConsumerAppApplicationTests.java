package com.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProducerConsumerAppApplicationTests {

	private MessageQueueHandler queueHandler;

    @BeforeEach
    void setUp() {
        queueHandler = new MessageQueueHandler();
    }

    @Test
    void testMessageProcessingSuccess() throws InterruptedException {
        Producer producer = new Producer(queueHandler);
        Consumer consumer = new Consumer(queueHandler);

        // Run the producer and consumer in separate threads
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        producerThread.join();  // Wait for producer to finish
        consumerThread.join();  // Wait for consumer to finish

        assertEquals(10, queueHandler.getTotalMessagesProcessed(), "Total messages should be processed correctly");
        assertEquals(0, queueHandler.getTotalErrors(), "There should be no errors");
    }

    @Test
    void testMessageProcessingFailure() {
        // Simulate an error scenario (e.g., queue size exceeds a limit)
        MessageQueueHandler faultyQueueHandler = new MessageQueueHandler();
        faultyQueueHandler.addMessage(null);  // Simulate error with null message

        assertEquals(0, faultyQueueHandler.getTotalMessagesProcessed(), "No messages should be processed if there's an error");
        assertEquals(1, faultyQueueHandler.getTotalErrors(), "One error should have been encountered");
    }

}
