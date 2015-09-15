package com.dianping.open.async.jactor.demo.count;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;


public class CounterActor extends JLPCActor {

    private long count = 0L;

    public void processRequest(AddCount request, RP rp) throws Exception {
        count += request.number;
        rp.processResponse(null);
    }

    public void processRequest(GetAndReset request, RP rp) throws Exception {
        Long current = new Long(count);
        count = 0;
        rp.processResponse(current);
    }


}
