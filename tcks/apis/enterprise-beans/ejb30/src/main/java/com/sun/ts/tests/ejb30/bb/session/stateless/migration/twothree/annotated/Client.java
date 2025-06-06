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

package com.sun.ts.tests.ejb30.bb.session.stateless.migration.twothree.annotated;

import com.sun.ts.lib.harness.Status;
import com.sun.ts.tests.ejb30.common.migration.twothree.ClientBase;
import com.sun.ts.tests.ejb30.common.migration.twothree.ThreeTestIF;

import com.sun.ts.tests.ejb30.common.migration.twothree.TwoTestRemoteIF;
import jakarta.ejb.EJB;

public class Client extends ClientBase {
  @EJB(name = "twoTestBean")
  private static TwoTestRemoteIF twoTestBean;

  @EJB(name = "threeTestBean")
  private static ThreeTestIF threeTestBean;

  @Override
  protected TwoTestRemoteIF getTwoTestBean() {
    return twoTestBean;
  }

  protected ThreeTestIF getThreeTestBean() {
    return threeTestBean;
  }

  public static void main(String[] args) {
    Client theTests = new Client();
    Status s = theTests.run(args, System.out, System.err);
    s.exit();
  }

  //////////////////////////////////////////////////////////////////////

  /*
   * @testName: callRemoteTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   *
   */

  /*
   * @testName: callLocalTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   *
   */

  /*
   * @testName: callRemoteSameTxContextTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   *
   */

  /*
   * @testName: callLocalSameTxContextTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   *
   */

  /*
   * @testName: callThreeRemoteTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   *
   */

  /*
   * @testName: callThreeLocalTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   *
   */

}
