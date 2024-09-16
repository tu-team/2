import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import mq.MessageQueuePersister;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageQueuePersisterTest {

    private static final Logger logger = LoggerFactory.getLogger(MessageQueuePersisterTest.class);

    private MessageQueuePersister messageQueuePersister;

    @Before
    public void setUp() {
        messageQueuePersister = new MessageQueuePersister();
        messageQueuePersister.init();
    }

    @After
    public void tearDown() {
        messageQueuePersister.shutDown();
    }

    @Test
    public void testSuccessfulReceive() throws Exception {
        // Prepare a listener that collects all payloads, in receive order.
        final Queue<String> received = new ConcurrentLinkedQueue<String>();
        messageQueuePersister.subscribeConsumer(String.class, payload -> {
            received.add(payload);
            return true;
        });

        // Send some payloads to the queue.
        int count = 1000;
        for (int i = 0; i < count; i++) {
            final int payload = i;
            messageQueuePersister.enqueue(() -> Integer.toString(payload));
        }

        // Wait for the payloads to go through.
        long startTime = System.currentTimeMillis();
        while (received.size() < count) {
            Thread.sleep(100);
            if (System.currentTimeMillis() > startTime + 10000) {
                break;
            }
        }

        // Check correctness.
        assertEquals(count, received.size());
        for (int i = 0; i < count; i++) {
            assertEquals(Integer.toString(i), received.remove());
        }
    }

    @Test
    public void testFailedReceive() throws Exception {
        final int rejectedCount = 20;
        // Prepare a consumer that rejects the first N messages, and keeps track
        // of received payloads.
        final AtomicInteger counter = new AtomicInteger(0);
        final Queue<String> received = new ConcurrentLinkedQueue<String>();
        messageQueuePersister.subscribeConsumer(String.class, payload -> {
            received.add(payload);
            int count = counter.getAndIncrement();
            boolean status = count >= rejectedCount;
            logger.debug("Status is {0} for payload {1}, count {2} and rejectedCount {3}", status, payload, count, rejectedCount);
            return status;
        });

        // Send two messages.
        messageQueuePersister.enqueue(() -> "hello");
        messageQueuePersister.enqueue(() -> "world");

        // Wait for the consumer to collect all data. N rejected, then 2 accepted
        // means N+2 in total, out of which N+1 should be "hello" and the last one
        // should be "world".
        long startTime = System.currentTimeMillis();
        while (received.size() < rejectedCount + 2) {
            Thread.sleep(100);
            // 1 sec redelivery delay
            if (System.currentTimeMillis() > startTime + rejectedCount * 1000 + 5000) {
                break;
            }
        }

        // Check correctness.
        assertEquals(rejectedCount + 2, received.size());
        for (int i = 0; i < rejectedCount + 1; i++) {
            assertEquals("hello", received.remove());
        }
        assertEquals("world", received.remove());
    }

    @Test
    public void performanceTest() throws Exception {
        final int howMany = 10000;
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        // Register a consumer that just counts the messages.
        final AtomicInteger counter = new AtomicInteger(0);
        messageQueuePersister.subscribeConsumer(String.class, payload -> {
            if (counter.incrementAndGet() == howMany) {
                countDownLatch.countDown();
            }
            return true;
        });

        Instant start = Instant.now();

        // Send the messages.
        for (int i = 0; i < howMany; i++) {
            final int payload = i;
            messageQueuePersister.enqueue(() -> Integer.toString(payload));
        }

        // Wait a bit more for the messages to be processed.
        countDownLatch.await(10, TimeUnit.SECONDS);
        assertEquals(howMany, counter.get());

        Instant stop = Instant.now();
        Duration delta = Duration.between(start, stop);
        System.out.println(delta.toMillis() + " ms total time");
        double throughput = 1000.0 * howMany / delta.toMillis();
        System.out.println(throughput + " messages/second");
    }
}