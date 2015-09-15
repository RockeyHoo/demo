/*
 *
 * Copyright (c) 2010-2015 by Shanghai HanHai Information Co., Ltd.
 * All rights reserved.
 *
 */

package com.dianping.open.async.jactor;

import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jactor.lpc.JLPCActor;

/*
 * Create Author  : shuang.he
 * Create Date    : 2015-09-14
 * Project        : rpc
 * File Name      : JActorPool.java
 */
public class DefaultJActorPool implements JActorPool
{

    private MailboxFactory mailboxFactory = null;

    private DefaultJActor defaultJActor;

    public DefaultJActorPool()
    {
        try
        {
            // Create a mailbox factory with a pool of 10 threads.
            mailboxFactory = JAMailboxFactory.newMailboxFactory(10);
            JAFactory jaFactory = new JAFactory();
            jaFactory.initialize(mailboxFactory.createAsyncMailbox());
            jaFactory.registerActorFactory(new ActorFactory("DefaultJActor")
            {

                @Override
                protected JLPCActor instantiateActor() throws Exception
                {
                    return new DefaultJActor();
                }

            });
            defaultJActor = (DefaultJActor) JAFactory.newActor(jaFactory, "DefaultJActor");
        }
        catch (Exception e)
        {
            throw new RuntimeException("error while initializing jactor", e);
        }
    }

    @Override
    public void execute()
    {
        try
        {
            defaultJActor.onReceive();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void doStop()
    {
        if (mailboxFactory != null)
        {
            mailboxFactory.close();
        }
    }
}
