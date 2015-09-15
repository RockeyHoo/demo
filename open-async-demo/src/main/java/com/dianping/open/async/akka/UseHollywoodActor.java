package com.dianping.open.async.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class UseHollywoodActor {

    public static void main(String[] args) throws InterruptedException {
        final ActorSystem system = ActorSystem.create("MySystem");
        final ActorRef johnnyDepp = system.actorOf(Props.create(HollywoodActor.class));
        johnnyDepp.tell("Jack Sparrow", ActorRef.noSender());
        Thread.sleep(100);
        johnnyDepp.tell("Edward SSS",ActorRef.noSender());
        system.shutdown();

    }
}
