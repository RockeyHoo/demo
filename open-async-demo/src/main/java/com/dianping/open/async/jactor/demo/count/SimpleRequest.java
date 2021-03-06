package com.dianping.open.async.jactor.demo.count;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;


public class SimpleRequest extends Request<Object, SimpleRequestReceiver> {
    public final static SimpleRequest req = new SimpleRequest();
    @Override
    public boolean isTargetType(Actor targetActor) {
        return targetActor instanceof SimpleRequestReceiver;
    }
    @Override
    public void processRequest(JLPCActor targetActor, RP rp) throws Exception {
        SimpleRequestReceiver smDriver = (SimpleRequestReceiver) targetActor;
        smDriver.processRequest(this, rp);
    }
}
