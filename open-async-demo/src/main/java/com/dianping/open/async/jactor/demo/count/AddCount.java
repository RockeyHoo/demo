package com.dianping.open.async.jactor.demo.count;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;

public class AddCount extends Request<Object, CounterActor> {

    public long number;

    @Override
    public boolean isTargetType(Actor actor) {
        return actor instanceof CounterActor;
    }

    @Override
    public void processRequest(JLPCActor jlpcActor, RP rp) throws Exception {
        CounterActor ca = (CounterActor) jlpcActor;
        ca.processRequest(this, rp);
    }
}
