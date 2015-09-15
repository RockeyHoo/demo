package com.dianping.open.async.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExecuteTaskActor extends UntypedActor
{

    ActorRef taskReturnActor = getContext().actorOf(Props.create(TaskReturnActor.class));

    private Random rnd = new Random();

    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println("starting..");
    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof Task) {
            Task task = (Task) o;
            System.out.println(task.getName());
            Result result = handler(task);
            taskReturnActor.tell(result, self());
        } else if (o instanceof Result) {
            taskReturnActor.tell("getResult", null);
        } else {
            unhandled(o);
        }
    }

    private Result handler(Task task) {
        System.out.println("任务处理中");
            return new Result(task.getName(), "success");
    }

    public static void main(String[] args){
        ActorSystem rootSystem =ActorSystem.create("yanshi");
        ActorRef executeTaskActorRef  = rootSystem.actorOf(Props.create(ExecuteTaskActor.class),"executeTask");
        for(int i=0;i<5;i++){
            executeTaskActorRef.tell(new Task("任务"+i),ActorRef.noSender());
        }
        rootSystem.shutdown();
    }
}

class TaskReturnActor extends UntypedActor {

    private Map<String, Object> results = new HashMap<String, Object>();

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof Result) {
            Result rs = (Result) o;
            if (rs.getResult().equals("success")) {
                results.put(rs.getTaskName(), rs.getResult());
            }

            if(results.size() == 5){
                getSender().tell(new Result(),null);
            }
        }else if(o instanceof  String){
            System.out.println(results.keySet().toString());
        }else{
            unhandled(o);
        }
    }
}

class Result implements Serializable {

    private String taskName;

    private String result;
    Result(){}

    Result(String taskName, String result) {
        this.taskName = taskName;
        this.result = result;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

class Task implements Serializable {

    private String name;

    Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}