/*
 * Copyright (c) 2013, 2018, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.javamail.ee.getMessageContent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.sun.ts.lib.harness.Status;

import tck.arquillian.porting.lib.spi.TestArchiveProcessor;
import tck.arquillian.protocol.common.TargetVehicle;

@Tag("tck-javatest")

public class getMessageContentJSP_Test extends getMessageContent_Test {
  	
	  @TargetsContainer("tck-javatest")
	  @OverProtocol("javatest")
	@Deployment(name = "jsp", testable = true)
	public static WebArchive createDeploymentJSP(@ArquillianResource TestArchiveProcessor archiveProcessor) throws IOException {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "getMessageContent_jsp_vehicle_web.war");
		archive.addPackages(true, "com.sun.ts.tests.javamail.ee.common");
		archive.addPackages(false, "com.sun.ts.tests.common.vehicle");
		archive.addPackages(false, "com.sun.ts.tests.common.vehicle.jsp");
		archive.addPackages(true, "com.sun.ts.lib.harness");
		InputStream jspVehicle = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("com/sun/ts/tests/common/vehicle/jsp/contentRoot/jsp_vehicle.jsp");
        archive.add(new ByteArrayAsset(jspVehicle), "jsp_vehicle.jsp");
        InputStream clientHtml = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("com/sun/ts/tests/common/vehicle/jsp/contentRoot/client.html");
        archive.add(new ByteArrayAsset(clientHtml), "client.html");
		archive.addClasses(getMessageContentJSP_Test.class, getMessageContent_Test.class);

		// The jsp descriptor
        URL jspUrl = getMessageContentJSP_Test.class.getResource("jsp_vehicle_client.xml");
        if(jspUrl != null) {
        	archive.addAsWebInfResource(jspUrl, "web.xml");
        }
        // The sun jsp descriptor
        URL sunJSPUrl = getMessageContentJSP_Test.class.getResource("getMessageContent_jsp_vehicle_client.jar.sun-application-client.xml");
        if(sunJSPUrl != null) {
        	archive.addAsWebInfResource(sunJSPUrl, "sun-web.xml");
        }
        // Call the archive processor
        archiveProcessor.processWebArchive(archive, getMessageContentJSP_Test.class, sunJSPUrl);

		return archive;
	};


  public static void main(String[] args) {
    getMessageContentJSP_Test theTests = new getMessageContentJSP_Test();
    Status s = theTests.run(args, System.out, System.err);
    s.exit();
  }

  /*
   * @testName: test1
   * 
   * @assertion_ids: JavaEE:SPEC:235;
   * 
   * @test_Strategy: getMessageContent test
   */
  // derived from javamail suite getMessageContent_Test
	@Test
	@TargetVehicle("jsp")
  public void test1() throws Exception {
		super.test1();
  } // end of test1()


}