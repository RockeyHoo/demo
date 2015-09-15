package com.dianping.open.async.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class FortuneTeller extends UntypedActor {
    @Override
    public void onReceive(Object o) throws Exception {
        getContext().sender().tell(String.format("%s you'll rock", o), ActorRef.noSender());
    }

    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("FOR");
        ActorRef fortuneTeller = system.actorOf(Props.create(FortuneTeller.class));
        fortuneTeller.tell("joe",ActorRef.noSender());
        system.shutdown();
    }
}
