package com.dianping.open.async.jactor.demo;

import org.agilewiki.jactor.*;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;

public class GettingStarted {

    public static void main(String[] args) throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(10);
        Mailbox mailbox = mailboxFactory.createMailbox();
        Test test = new Test();
        test.initialize(mailbox);

        JAFuture future = new JAFuture();
        System.out.println(Start.req.send(future, test));
        mailboxFactory.close();

    }
}

class Start extends Request {
    public static final Start req = new Start();


    @Override
    public boolean isTargetType(Actor actor) {
        return actor instanceof Test;
    }

    @Override
    public void processRequest(JLPCActor jlpcActor, RP rp) throws Exception {
        Test a = (Test) jlpcActor;
        a.start(rp);
    }
}

class Test extends JLPCActor {

    public void start(final RP rp) throws Exception {
        Greeter greeter = new Greeter();
        greeter.initialize(getMailboxFactory().createMailbox());
        Greet.req.send(this, greeter, new RP<String>() {

            @Override
            public void processResponse(String s) throws Exception {
                System.out.println(s);
                rp.processResponse(s);
            }
        });

    }
}

class Greeter extends JLPCActor {

    public String greet() throws Exception {
        return "Hello World";
    }
}

class Greet extends Request<String, Greeter> {
    public static final Greet req = new Greet();

    @Override
    public boolean isTargetType(Actor actor) {
        return actor instanceof Greeter;
    }

    @Override
    public void processRequest(JLPCActor jlpcActor, RP rp) throws Exception {
        Greeter a = (Greeter) jlpcActor;
        rp.processResponse(a.greet());
    }
}