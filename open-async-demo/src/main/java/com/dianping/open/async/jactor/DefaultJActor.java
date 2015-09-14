/*
 *
 * Copyright (c) 2010-2015 by Shanghai HanHai Information Co., Ltd.
 * All rights reserved.
 *
 */

package com.dianping.open.async.jactor;

import org.agilewiki.jactor.lpc.JLPCActor;

/*
 * Create Author  : shuang.he
 * Create Date    : 2015-09-14
 * Project        : rpc
 * File Name      : DefaultActor.java
 */
public class DefaultJActor extends JLPCActor
{
    public void onReceive() throws Exception {
        System.out.println("haha");
    }
}
