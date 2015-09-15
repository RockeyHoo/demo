package com.dianping.open.async.akka;

import akka.actor.UntypedActor;

public class HollywoodActor  extends UntypedActor{

    @Override
    public void onReceive(Object role) throws Exception {
        System.out.println("Playing"+role+"from Thread "+ Thread.currentThread().getName());
    }
}
