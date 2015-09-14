/*
 *
 * Copyright (c) 2010-2015 by Shanghai HanHai Information Co., Ltd.
 * All rights reserved.
 *
 */

package com.dianping.open.async.jactor;

/*
 * Create Author  : shuang.he
 * Create Date    : 2015-09-14
 * Project        : rpc
 * File Name      : JActorTest.java
 */
public class JActorTest
{
   public static DefaultJActorPool pool = new DefaultJActorPool();

   private static void testJactor() {
       pool.execute();
   }

    public static void main(String[] args)
    {
        testJactor();
    }
}
