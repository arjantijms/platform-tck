/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */

package com.sun.ts.tests.ejb30.common.migration.twothree;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.sun.ts.tests.ejb30.common.helper.TestFailedException;

import jakarta.ejb.CreateException;
import jakarta.ejb.SessionBean;
import jakarta.ejb.SessionContext;

public class TwoTestBean implements SessionBean {
  public static final String TWO_LOCAL_SHORT = "ejb/twolocal";

  public static final String TWO_LOCAL = "java:comp/env/ejb/twolocal";

  public static final String TWO_REMOTE_SHORT = "ejb/tworemote";

  public static final String TWO_REMOTE = "java:comp/env/ejb/tworemote";

  private SessionContext sessionContext;

  public TwoTestBean() {
  }

  public void ejbCreate() throws CreateException {

  }

  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }

  public void ejbRemove() {
  }

  public void ejbPassivate() {
  }

  public void ejbActivate() {
  }

  //////////////////////////////////////////////////////////////////////
  // business methods from TwoTestRemoteIF
  //////////////////////////////////////////////////////////////////////

  public void callRemote() throws TestFailedException {
    TwoRemoteIF twoRemote = null;
    try {
      twoRemote = getTwoRemoteBean();
      String result = twoRemote.from2RemoteClient();
      if ("from2RemoteClient".equals(result)) {
        // good
      } else {
        throw new TestFailedException("Expected from2RemoteClient() to return"
            + "from2RemoteClient, but actual '" + result + "'");
      }
    } catch (RemoteException e) {
      throw new TestFailedException(e);
    } finally {
    }

  }

  public void callRemoteSameTxContext() throws TestFailedException {
    TwoRemoteIF twoRemote = null;
    try {
      twoRemote = getTwoRemoteBean();
      twoRemote.remoteSameTxContext();
      if (sessionContext.getRollbackOnly()) {
        // expected
      } else {
        throw new TestFailedException(
            "Expected getRollbackOnly to return true," + " but got false.");
      }
    } catch (RemoteException e) {
      throw new TestFailedException(e);
    }
  }

  public void callLocal() throws TestFailedException {
    TwoLocalIF twoLocal = getTwoLocalBean();
      String result = twoLocal.from2LocalClient();
      if ("from2LocalClient".equals(result)) {
        // good
      } else {
        throw new TestFailedException("Expected from2LocalClient() to return"
            + "from2LocalClient, but actual '" + result + "'");
      }
  }

  public void callLocalSameTxContext() throws TestFailedException {
    TwoLocalIF twoLocal = null;
    try {
      twoLocal = getTwoLocalBean();
      twoLocal.localSameTxContext();
      if (sessionContext.getRollbackOnly()) {
        // expected
      } else {
        throw new TestFailedException(
            "Expected getRollbackOnly to return true," + " but got false.");
      }
    } finally {
    }
  }

  //////////////////////////////////////////////////////////////////////

  protected TwoRemoteIF getTwoRemoteBean() throws TestFailedException {
    TwoRemoteIF twoRemote = null;
    try {
      Object obj = sessionContext.lookup(TWO_REMOTE_SHORT);
      twoRemote = (TwoRemoteIF) obj;
    } catch (Exception e) {
      throw new TestFailedException(e);
    }
    return twoRemote;
  }

  protected TwoRemoteIF getTwoRemoteBeanJndi() throws TestFailedException {
    TwoRemoteIF twoRemote = null;
    try {
      Context ic = new InitialContext();
      Object obj = ic.lookup(TWO_REMOTE);
      twoRemote = (TwoRemoteIF) PortableRemoteObject.narrow(obj,
              TwoRemoteIF.class);
    } catch (NamingException e) {
      throw new TestFailedException(e);
    } catch (Exception e) {
      throw new TestFailedException(e);
    }
    return twoRemote;
  }

  protected TwoLocalIF getTwoLocalBean() throws TestFailedException {
    TwoLocalIF twoLocal = null;
    try {
      Object obj = sessionContext.lookup(TWO_LOCAL_SHORT);
      twoLocal = (TwoLocalIF) obj;
    } catch (Exception e) {
        throw new TestFailedException(e);
    }
    return twoLocal;
  }

  protected TwoLocalIF getTwoLocalBeanJndi() throws TestFailedException {
    TwoLocalIF twoLocal = null;
    try {
      Context ic = new InitialContext();
      twoLocal = (TwoLocalIF) ic.lookup(TWO_LOCAL);
    } catch (NamingException e) {
      throw new TestFailedException(e);
    }
    return twoLocal;
  }
}
