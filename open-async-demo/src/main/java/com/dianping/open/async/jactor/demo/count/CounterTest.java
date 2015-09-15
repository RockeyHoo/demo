package com.dianping.open.async.jactor.demo.count;

import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;


public class CounterTest {

    /**
     *
     shard
     [java-shared] Number of runs: 1000000000
     [java-shared] Count: 100000000000
     [java-shared] Test time in seconds: 44.167
     [java-shared] Messages per second: 2.2641338555935428E7

     async
     [java-shared] Number of runs: 1000000
     [java-shared] Count: 100000000
     [java-shared] Test time in seconds: 1.745
     [java-shared] Messages per second: 573065.9025787965

     unshard
     [java-shared] Number of runs: 1000000
     [java-shared] Count: 100000000
     [java-shared] Test time in seconds: 0.235
     [java-shared] Messages per second: 4255319.14893617

     Process finished with exit code 0

     1.526  14.457  0.583

     * @throws Exception
     */
    public void testShard() throws Exception {
        long runs = 1000000000;

        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(10);
        try {
            Mailbox sharedMailBox = mailboxFactory.createMailbox();

            CounterActor counterActor = new CounterActor();
            counterActor.initialize(sharedMailBox);

            Driver driver = new Driver();
            driver.initialize(sharedMailBox, counterActor, runs);

            JAFuture future = new JAFuture();

            long start = System.currentTimeMillis();
            Long count = (Long) SimpleRequest.req.send(future, driver);
            long finish = System.currentTimeMillis();

            double elapsedTime = (finish - start) / 1000.;
            System.out.println("[java-shared] Number of runs: " + runs);
            System.out.println("[java-shared] Count: " + count);
            System.out.println("[java-shared] Test time in seconds: " + elapsedTime);
            System.out.println("[java-shared] Messages per second: " + runs / elapsedTime);
        } finally {
            mailboxFactory.close();
        }
    }


    public static void main(String[] args) throws Exception {
               new CounterTest().testShard();
    }
}
