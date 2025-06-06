/*
 * Copyright (c) 2016, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jsonp.api.common;

import java.util.Properties;

import com.sun.ts.lib.harness.Fault;
import com.sun.ts.tests.common.base.ServiceEETest;

// $Id$
/**
 * Common JSON-P test.
 */
public class JsonPTest extends ServiceEETest {

  /**
   * Java VM code execution entry point.
   * 
   * @param args
   *          Command line arguments.
   */

  /**
   * Test setup.
   * 
   * @param args
   *          Command line arguments.
   * @param p
   *          Test properties.
   * @throws Exception
   */
  public void setup(String[] args, Properties p) throws Fault {
    // logMsg("setup ok");
  }

  /**
   * Test cleanup.
   * 
   * @throws Exception
   */
  public void cleanup() throws Fault {
    // logMsg("cleanup ok");
  }

}
