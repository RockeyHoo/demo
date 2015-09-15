package com.dianping.open.async.jactor.demo.count;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.TargetActor;


public interface SimpleRequestReceiver extends TargetActor {
    public void processRequest(SimpleRequest request, RP rp)
            throws Exception;
}
