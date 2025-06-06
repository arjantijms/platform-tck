/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.ts.tests.ejb32.lite.timer.interceptor.business.common;

import com.sun.ts.tests.ejb30.timer.common.TimerBeanBase;
import com.sun.ts.tests.ejb30.timer.common.TimerUtil;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

abstract public class BusinessTimerBeanBase extends TimerBeanBase {
  @SuppressWarnings("unused")
  @AroundInvoke
  private Object aroundInvoke(InvocationContext inv) throws Exception {
    TimerUtil.createMillisecondLaterTimer(timerService,
        "BusinessTimerBeanBase.aroundInvoke", false);
    return inv.proceed();
  }
}
