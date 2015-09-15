package com.dianping.open.async.jactor.demo.count;

import org.agilewiki.jactor.JAIterator;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.simpleMachine.ObjectFunc;
import org.agilewiki.jactor.simpleMachine.SimpleMachine;
import org.agilewiki.jactor.simpleMachine._Operation;


public class Driver extends JLPCActor implements SimpleRequestReceiver {

    SMBuilder smb = new SMBuilder();

    public void initialize(Mailbox mailbox, final CounterActor counterActor, final long runs) throws Exception {
        super.initialize(mailbox);
        smb.add(new _Operation() {
            @Override
            public void call(final SimpleMachine simpleMachine, final RP rp) throws Exception {
                JAIterator it = new JAIterator() {
                    long i = 0;

                    @Override
                    protected void process(RP rp) throws Exception {
                        if (i == runs)
                            rp.processResponse(this);
                        else {
                            i += 1;
                            AddCount addCount = new AddCount();
                            addCount.number = 100L;
                            addCount.send(Driver.this, counterActor, rp);
                        }
                    }
                };

                it.iterate(new RP() {

                    @Override
                    public void processResponse(Object o) throws Exception {
                        rp.processResponse(null);
                    }
                });
            }
        });

        smb._send(counterActor, new GetAndReset(), "count");
        smb._return(new ObjectFunc() {
            @Override
            public Object get(SimpleMachine simpleMachine) {
                return simpleMachine.get("count");
            }
        });

    }

    @Override
    public void processRequest(SimpleRequest request, RP rp) throws Exception {
        smb.call(rp);
    }
}
