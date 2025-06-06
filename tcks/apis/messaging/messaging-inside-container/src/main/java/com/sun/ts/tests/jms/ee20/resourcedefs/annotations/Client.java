/*
 * Copyright (c) 2013, 2018, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2022 Contributors to Eclipse Foundation. All rights reserved.
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

package com.sun.ts.tests.jms.ee20.resourcedefs.annotations;

import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import com.sun.ts.lib.harness.Status;
import com.sun.ts.tests.common.base.EETest;
import com.sun.ts.lib.porting.TSURL;
import com.sun.ts.lib.util.TSNamingContext;
import com.sun.ts.lib.util.TestUtil;

import jakarta.ejb.EJB;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConnectionFactoryDefinition;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSProducer;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnectionFactory;

//-------------------------------------
// JMS Destination Resource Definitions
//-------------------------------------
@JMSDestinationDefinition(
      description="Define Queue AppClientMyTestQueue",
      interfaceName="jakarta.jms.Queue",
      name="java:global/env/AppClientMyTestQueue",
      destinationName="AppClientMyTestQueue"
 )

@JMSDestinationDefinition(
      description="Define Topic AppClientMyTestTopic",
      interfaceName="jakarta.jms.Topic",
      name="java:app/env/AppClientMyTestTopic",
      destinationName="AppClientMyTestTopic"
 )


//-------------------------------------------
// JMS ConnectionFactory Resource Definitions
//-------------------------------------------
@JMSConnectionFactoryDefinition(
      description="Define ConnectionFactory AppClientMyTestConnectionFactory",
      interfaceName="jakarta.jms.ConnectionFactory",
      name="java:global/AppClientMyTestConnectionFactory",
      user = "j2ee",
      password = "j2ee"
 )

@JMSConnectionFactoryDefinition(
      description="Define QueueConnectionFactory AppClientMyTestQueueConnectionFactory",
      interfaceName="jakarta.jms.QueueConnectionFactory",
      name="java:app/AppClientMyTestQueueConnectionFactory",
      user = "j2ee",
      password = "j2ee"
 )

@JMSConnectionFactoryDefinition(
      description="Define TopicConnectionFactory AppClientMyTestTopicConnectionFactory",
      interfaceName="jakarta.jms.TopicConnectionFactory",
      name="java:module/AppClientMyTestTopicConnectionFactory",
      user = "j2ee",
      password = "j2ee"
 )


@JMSConnectionFactoryDefinition(
      description="Define Durable TopicConnectionFactory AppClientMyTestDurableTopicConnectionFactory",
      interfaceName="jakarta.jms.TopicConnectionFactory",
      name="java:comp/env/jms/AppClientMyTestDurableTopicConnectionFactory",
      user = "j2ee",
      password = "j2ee",
      clientId = "MyClientID",
      properties = { "Property1=10", "Property2=20" },
      transactional = false,
      maxPoolSize = 30,
      minPoolSize = 20
 )

public class Client extends EETest {
  private static final long serialVersionUID = 1L;

  // JMS objects
  protected transient ConnectionFactory dcf = null;

  protected transient ConnectionFactory cf = null;

  protected transient ConnectionFactory cfra = null;

  protected transient QueueConnectionFactory qcf = null;

  protected transient TopicConnectionFactory tcf = null;

  protected transient TopicConnectionFactory dtcf = null;

  protected transient Topic topic = null;

  protected transient Topic topica = null;

  protected transient Queue queue = null;

  protected transient JMSContext context = null;

  protected transient JMSConsumer consumerQ = null;

  protected transient JMSProducer producerQ = null;

  protected transient JMSConsumer consumerT = null;

  protected transient JMSProducer producerT = null;

  protected boolean queueTest = false;

  // Harness req's
  protected Properties props = null;

  // properties read from ts.jte file
  protected long timeout;

  protected String user;

  protected String password;

  protected String mode;

  // The webserver defaults (overidden by harness properties)
  private static final String PROTOCOL = "http";

  private static final String HOSTNAME = "localhost";

  private static final int PORTNUM = 8000;

  private TSURL ctsurl = new TSURL();

  private String hostname = HOSTNAME;

  private int portnum = PORTNUM;

  // URL properties used by the test
  private URL url = null;

  private transient URLConnection urlConn = null;

  private String SERVLET = "/resourcedefs_annotations_web/ServletTest";

  private String JSP = "/resourcedefs_annotations_web/JspClient.jsp";

  @EJB(name = "ejb/JMSResourceDefsEjbClientBean")
  static EjbClientIF ejbclient;

  public static void main(String[] args) {
    Client theTests = new Client();
    Status s = theTests.run(args, System.out, System.err);
    s.exit();
  }

  /* Test setup */

  /*
   * @class.setup_props: jms_timeout; user; password; platform.mode;
   * webServerHost; webServerPort;
   */
  public void setup(String[] args, Properties p) throws Exception {
    props = p;
    boolean pass = true;
    try {
      // get props
      timeout = Integer.parseInt(p.getProperty("jms_timeout"));
      user = p.getProperty("user");
      password = p.getProperty("password");
      mode = p.getProperty("platform.mode");
      hostname = p.getProperty("webServerHost");

      // check props for errors
      if (timeout < 1) {
        throw new Exception(
            "'jms_timeout' (milliseconds) in ts.jte must be > 0");
      }
      if (user == null) {
        throw new Exception("'user' in ts.jte must not be null ");
      }
      if (password == null) {
        throw new Exception("'password' in ts.jte must not be null ");
      }
      if (mode == null) {
        throw new Exception("'platform.mode' in ts.jte must not be null");
      }
      if (hostname == null) {
        throw new Exception("'webServerHost' in ts.jte must not be null");
      }
      try {
        portnum = Integer.parseInt(p.getProperty("webServerPort"));
      } catch (Exception e) {
        throw new Exception("'webServerPort' in ts.jte must be a number");
      }
      if (ejbclient == null) {
        throw new Exception("setup failed: ejbclient injection failure");
      }
    } catch (Exception e) {
      throw new Exception("setup failed:", e);
    }
    TestUtil.logMsg("setup ok");
  }

  /*
   * cleanup() is called after each test
   * 
   * @exception Fault
   */
  public void cleanup() throws Exception {
    TestUtil.logMsg("cleanup ok");
  }

  /*
   * Lookup JMS Connection Factory and Destination Objects
   */
  private void doLookupJMSObjects() throws Exception {
    try {
      TestUtil.logMsg(
          "Lookup JMS factories defined in @JMSConnectionFactoryDefinitions");
      TestUtil.logMsg(
          "Lookup JMS destinations defined in @JMSDestinationDefinitions");
      TSNamingContext namingctx = new TSNamingContext();
      TestUtil.logMsg("Lookup java:comp/DefaultJMSConnectionFactory");
      dcf = (ConnectionFactory) namingctx
          .lookup("java:comp/DefaultJMSConnectionFactory");
      TestUtil.logMsg("Lookup java:global/AppClientMyTestConnectionFactory");
      cf = (ConnectionFactory) namingctx
          .lookup("java:global/AppClientMyTestConnectionFactory");
      TestUtil.logMsg("Lookup java:app/AppClientMyTestQueueConnectionFactory");
      qcf = (QueueConnectionFactory) namingctx
          .lookup("java:app/AppClientMyTestQueueConnectionFactory");
      TestUtil
          .logMsg("Lookup java:module/AppClientMyTestTopicConnectionFactory");
      tcf = (TopicConnectionFactory) namingctx
          .lookup("java:module/AppClientMyTestTopicConnectionFactory");
      TestUtil.logMsg(
          "Lookup java:comp/env/jms/AppClientMyTestDurableTopicConnectionFactory");
      dtcf = (TopicConnectionFactory) namingctx.lookup(
          "java:comp/env/jms/AppClientMyTestDurableTopicConnectionFactory");
      TestUtil.logMsg("Lookup java:global/env/AppClientMyTestQueue");
      queue = (Queue) namingctx.lookup("java:global/env/AppClientMyTestQueue");
      TestUtil.logMsg("Lookup java:app/env/AppClientMyTestTopic");
      topic = (Topic) namingctx.lookup("java:app/env/AppClientMyTestTopic");

      TestUtil.logMsg("Create JMSContext, JMSProducer's and JMSConsumer's");
      context = cf.createContext(user, password, JMSContext.AUTO_ACKNOWLEDGE);
      producerQ = context.createProducer();
      consumerQ = context.createConsumer(queue);
      producerT = context.createProducer();
      consumerT = context.createConsumer(topic);
    } catch (Exception e) {
      throw new Exception("doLookupJMSObjects failed:", e);
    }
  }

  /*
   * doCleanup()
   */
  private void doCleanup() throws Exception {
    try {
      if (queueTest && consumerQ != null) {
        TestUtil.logMsg("Flush any messages left on Queue");
        Message rmsg = consumerQ.receive(timeout);
        while (rmsg != null) {
          rmsg = consumerQ.receiveNoWait();
          if (rmsg == null) {
            rmsg = consumerQ.receiveNoWait();
          }
        }
        consumerQ.close();
      }
      if (consumerT != null)
        consumerT.close();
      TestUtil.logMsg("Close JMSContext Objects");
      if (context != null)
        context.close();
    } catch (Exception e) {
      TestUtil.logErr("Caught exception: " + e);
      throw new Exception("doCleanup failed!", e);
    }
  }

  /*
   * @testName: sendAndRecvQueueTestFromAppClient
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Send and receive a message to/from a Queue.
   *
   */
  public void sendAndRecvQueueTestFromAppClient() throws Exception {
    boolean pass = true;
    String message = "Where are you!";
    try {
      doLookupJMSObjects();
      queueTest = true;
      // send and receive TextMessage
      TestUtil.logMsg(
          "Creating TextMessage via JMSContext.createTextMessage(String)");
      TextMessage expTextMessage = context.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvQueueTestFromAppClient");
      TestUtil.logMsg(
          "Sending TextMessage via JMSProducer.send(Destination, Message)");
      producerQ.send(queue, expTextMessage);
      TestUtil.logMsg("Receive TextMessage via JMSconsumer.receive(long)");
      TextMessage actTextMessage = (TextMessage) consumerQ.receive(timeout);
      if (actTextMessage == null) {
        throw new Exception("Did not receive TextMessage");
      }
      TestUtil.logMsg("Check the value in TextMessage");
      if (actTextMessage.getText().equals(expTextMessage.getText())) {
        TestUtil.logMsg("TextMessage is correct");
      } else {
        TestUtil.logErr(
            "TextMessage is incorrect expected " + expTextMessage.getText()
                + ", received " + actTextMessage.getText());
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught exception: " + e);
      throw new Exception("sendAndRecvQueueTestFromAppClient", e);
    } finally {
      try {
        doCleanup();
      } catch (Exception e) {
        throw new Exception("doCleanup failed: ", e);
      }
    }

    if (!pass) {
      throw new Exception("sendAndRecvQueueTestFromAppClient failed");
    }
  }

  /*
   * @testName: sendAndRecvTopicTestFromAppClient
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Send and receive a message to/from a Topic.
   *
   */
  public void sendAndRecvTopicTestFromAppClient() throws Exception {
    boolean pass = true;
    String message = "Where are you!";
    try {
      doLookupJMSObjects();
      queueTest = false;
      // send and receive TextMessage
      TestUtil.logMsg(
          "Creating TextMessage via JMSContext.createTextMessage(String)");
      TextMessage expTextMessage = context.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvTopicTestFromAppClient");
      TestUtil.logMsg(
          "Sending TextMessage via JMSProducer.send(Destination, Message)");
      producerT.send(topic, expTextMessage);
      TestUtil.logMsg("Receive TextMessage via JMSconsumer.receive(long)");
      TextMessage actTextMessage = (TextMessage) consumerT.receive(timeout);
      if (actTextMessage == null) {
        throw new Exception("Did not receive TextMessage");
      }
      TestUtil.logMsg("Check the value in TextMessage");
      if (actTextMessage.getText().equals(expTextMessage.getText())) {
        TestUtil.logMsg("TextMessage is correct");
      } else {
        TestUtil.logErr(
            "TextMessage is incorrect expected " + expTextMessage.getText()
                + ", received " + actTextMessage.getText());
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught exception: " + e);
      throw new Exception("sendAndRecvTopicTestFromAppClient", e);
    } finally {
      try {
        doCleanup();
      } catch (Exception e) {
        throw new Exception("doCleanup failed: ", e);
      }
    }

    if (!pass) {
      throw new Exception("sendAndRecvTopicTestFromAppClient failed");
    }
  }

  /*
   * @testName: sendAndRecvQueueTestFromServletClient
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Send and receive a message to/from a Queue.
   *
   */
  public void sendAndRecvQueueTestFromServletClient() throws Exception {
    boolean pass = true;
    try {
      TestUtil.logMsg("-------------------------------------");
      TestUtil.logMsg("sendAndRecvQueueTestFromServletClient");
      TestUtil.logMsg("-------------------------------------");
      url = ctsurl.getURL("http", hostname, portnum, SERVLET);
      TestUtil.logMsg("Servlet URL: " + url);
      props.setProperty("TEST", "sendAndRecvQueueTestFromServletClient");
      urlConn = TestUtil.sendPostData(props, url);
      Properties p = TestUtil.getResponseProperties(urlConn);
      String passStr = p.getProperty("TESTRESULT");
      if (passStr.equals("fail")) {
        pass = false;
        TestUtil
            .logErr("JMSConnectionFactoryDefinitions test failed from Servlet");
        TestUtil.logErr("JMSDestinationDefinitions test failed from Servlet");
      } else {
        TestUtil
            .logMsg("JMSConnectionFactoryDefinitions test passed from Servlet");
        TestUtil.logMsg("JMSDestinationDefinitions test passed from Servlet");
      }
    } catch (Exception e) {
      TestUtil
          .logErr("JMSConnectionFactoryDefinitions test failed from Servlet");
      TestUtil.logErr("JMSDestinationDefinitions test failed from Servlet");
      pass = false;
    }

    if (!pass) {
      throw new Exception("sendAndRecvQueueTestFromServletClient failed");
    }
  }

  /*
   * @testName: sendAndRecvTopicTestFromServletClient
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Send and receive a message to/from a Topic.
   *
   */
  public void sendAndRecvTopicTestFromServletClient() throws Exception {
    boolean pass = true;
    try {
      TestUtil.logMsg("-------------------------------------");
      TestUtil.logMsg("sendAndRecvTopicTestFromServletClient");
      TestUtil.logMsg("-------------------------------------");
      url = ctsurl.getURL("http", hostname, portnum, SERVLET);
      TestUtil.logMsg("Servlet URL: " + url);
      props.setProperty("TEST", "sendAndRecvTopicTestFromServletClient");
      urlConn = TestUtil.sendPostData(props, url);
      Properties p = TestUtil.getResponseProperties(urlConn);
      String passStr = p.getProperty("TESTRESULT");
      if (passStr.equals("fail")) {
        pass = false;
        TestUtil
            .logErr("JMSConnectionFactoryDefinitions test failed from Servlet");
        TestUtil.logErr("JMSDestinationDefinitions test failed from Servlet");
      } else {
        TestUtil
            .logMsg("JMSConnectionFactoryDefinitions test passed from Servlet");
        TestUtil.logMsg("JMSDestinationDefinitions test passed from Servlet");
      }
    } catch (Exception e) {
      TestUtil
          .logErr("JMSConnectionFactoryDefinitions test failed from Servlet");
      TestUtil.logErr("JMSDestinationDefinitions test failed from Servlet");
      pass = false;
    }

    if (!pass) {
      throw new Exception("sendAndRecvTopicTestFromServletClient failed");
    }
  }

  /*
   * @testName: sendAndRecvQueueTestFromJspClient
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Send and receive a message to/from a Queue.
   *
   */
  public void sendAndRecvQueueTestFromJspClient() throws Exception {
    boolean pass = true;
    try {
      TestUtil.logMsg("---------------------------------");
      TestUtil.logMsg("sendAndRecvQueueTestFromJspClient");
      TestUtil.logMsg("---------------------------------");
      url = ctsurl.getURL("http", hostname, portnum, JSP);
      TestUtil.logMsg("Jsp URL: " + url);
      props.setProperty("TEST", "sendAndRecvQueueTestFromJspClient");
      urlConn = TestUtil.sendPostData(props, url);
      Properties p = TestUtil.getResponseProperties(urlConn);
      String passStr = p.getProperty("TESTRESULT");
      if (passStr.equals("fail")) {
        pass = false;
        TestUtil.logErr("JMSConnectionFactoryDefinitions test failed from Jsp");
        TestUtil.logErr("JMSDestinationDefinitions test failed from Jsp");
      } else {
        TestUtil.logMsg("JMSConnectionFactoryDefinitions test passed from Jsp");
        TestUtil.logMsg("JMSDestinationDefinitions test passed from Jsp");
      }
    } catch (Exception e) {
      TestUtil.logErr("JMSConnectionFactoryDefinitions test failed from Jsp");
      TestUtil.logErr("JMSDestinationDefinitions test failed from Jsp");
      pass = false;
    }

    if (!pass) {
      throw new Exception("sendAndRecvQueueTestFromJspClient failed");
    }
  }

  /*
   * @testName: sendAndRecvTopicTestFromJspClient
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Send and receive a message to/from a Topic.
   *
   */
  public void sendAndRecvTopicTestFromJspClient() throws Exception {
    boolean pass = true;
    try {
      TestUtil.logMsg("---------------------------------");
      TestUtil.logMsg("sendAndRecvTopicTestFromJspClient");
      TestUtil.logMsg("---------------------------------");
      url = ctsurl.getURL("http", hostname, portnum, JSP);
      TestUtil.logMsg("Jsp URL: " + url);
      props.setProperty("TEST", "sendAndRecvTopicTestFromJspClient");
      urlConn = TestUtil.sendPostData(props, url);
      Properties p = TestUtil.getResponseProperties(urlConn);
      String passStr = p.getProperty("TESTRESULT");
      if (passStr.equals("fail")) {
        pass = false;
        TestUtil.logErr("JMSConnectionFactoryDefinitions test failed from Jsp");
        TestUtil.logErr("JMSDestinationDefinitions test failed from Jsp");
      } else {
        TestUtil.logMsg("JMSConnectionFactoryDefinitions test passed from Jsp");
        TestUtil.logMsg("JMSDestinationDefinitions test passed from Jsp");
      }
    } catch (Exception e) {
      TestUtil.logErr("JMSConnectionFactoryDefinitions test failed from Jsp");
      TestUtil.logErr("JMSDestinationDefinitions test failed from Jsp");
      pass = false;
    }

    if (!pass) {
      throw new Exception("sendAndRecvTopicTestFromJspClient failed");
    }
  }

  /*
   * @testName: sendAndRecvQueueTestFromEjbClient
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Send and receive a message to/from a Queue.
   *
   */
  public void sendAndRecvQueueTestFromEjbClient() throws Exception {
    boolean pass = true;
    try {
      ejbclient.init(props);
      TestUtil.logMsg("---------------------------------");
      TestUtil.logMsg("sendAndRecvQueueTestFromEjbClient");
      TestUtil.logMsg("---------------------------------");
      boolean passEjb = ejbclient.echo("sendAndRecvQueueTestFromEjbClient");
      if (!passEjb) {
        pass = false;
        TestUtil.logErr("JMSConnectionFactoryDefinitions test failed from Ejb");
        TestUtil.logErr("JMSDestinationDefinitions test failed from Ejb");
      } else {
        TestUtil.logMsg("JMSConnectionFactoryDefinitions test passed from Ejb");
        TestUtil.logMsg("JMSDestinationDefinitions test passed from Ejb");
      }
    } catch (Exception e) {
      TestUtil.logErr("JMSConnectionFactoryDefinitions test failed from Ejb");
      TestUtil.logErr("JMSDestinationDefinitions test failed from Ejb");
      pass = false;
    }

    if (!pass) {
      throw new Exception("sendAndRecvQueueTestFromEjbClient failed");
    }
  }

  /*
   * @testName: sendAndRecvTopicTestFromEjbClient
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Send and receive a message to/from a Topic.
   *
   */
  public void sendAndRecvTopicTestFromEjbClient() throws Exception {
    boolean pass = true;
    try {
      ejbclient.init(props);
      TestUtil.logMsg("---------------------------------");
      TestUtil.logMsg("sendAndRecvTopicTestFromEjbClient");
      TestUtil.logMsg("---------------------------------");
      boolean passEjb = ejbclient.echo("sendAndRecvTopicTestFromEjbClient");
      if (!passEjb) {
        pass = false;
        TestUtil.logErr("JMSConnectionFactoryDefinitions test failed from Ejb");
        TestUtil.logErr("JMSDestinationDefinitions test failed from Ejb");
      } else {
        TestUtil.logMsg("JMSConnectionFactoryDefinitions test passed from Ejb");
        TestUtil.logMsg("JMSDestinationDefinitions test passed from Ejb");
      }
    } catch (Exception e) {
      TestUtil.logErr("JMSConnectionFactoryDefinitions test failed from Ejb");
      TestUtil.logErr("JMSDestinationDefinitions test failed from Ejb");
      pass = false;
    }

    if (!pass) {
      throw new Exception("sendAndRecvTopicTestFromEjbClient failed");
    }
  }

  /*
   * @testName: checkClientIDOnDurableConnFactoryTest
   *
   * @assertion_ids: JMS:JAVADOC:1324; JMS:JAVADOC:1325; JMS:JAVADOC:1327;
   * JMS:JAVADOC:1330; JMS:JAVADOC:1331; JMS:JAVADOC:1332; JMS:JAVADOC:1333;
   * JMS:JAVADOC:1334; JMS:JAVADOC:1335; JMS:JAVADOC:1336; JMS:JAVADOC:1338;
   * JMS:JAVADOC:1339; JMS:JAVADOC:1342; JMS:JAVADOC:1343; JMS:JAVADOC:1344;
   * JMS:JAVADOC:1345; JMS:JAVADOC:1346; JMS:JAVADOC:1347; JMS:JAVADOC:1348;
   * JMS:JAVADOC:1451; JMS:JAVADOC:1452;
   *
   * @test_Strategy: Check client id setting on durable connection factory
   *
   */
  public void checkClientIDOnDurableConnFactoryTest() throws Exception {
    boolean pass = true;
    JMSContext context = null;
    try {
      queueTest = false;
      doLookupJMSObjects();
      TestUtil.logMsg(
          "==============================================================");
      TestUtil.logMsg(
          "Verify admin configured client id is MyClientID from AppClient");
      TestUtil.logMsg(
          "==============================================================");
      TestUtil
          .logMsg("Create JMSContext from durable topic connection factory");
      TestUtil.logMsg(
          "Check the client id which is configured as MyClientID in the "
              + "JMSConnectionFactoryDefinition annotation");
      context = dtcf.createContext(user, password, JMSContext.AUTO_ACKNOWLEDGE);
      String clientid = context.getClientID();
      if (clientid == null) {
        TestUtil.logErr("Client ID value is null (expected MyClientID)");
        pass = false;
      } else if (clientid.equals("MyClientID")) {
        TestUtil.logMsg("Client ID value is correct (MyClientID)");
      } else {
        TestUtil
            .logErr("Client ID value is incorrect (expected MyClientID, got "
                + clientid + ")");
        pass = false;
      }
      context.close();
      context = null;
      try {
        doCleanup();
      } catch (Exception e) {
        TestUtil.logErr("Error in cleanup");
      }
      TestUtil.logMsg(
          "==================================================================");
      TestUtil.logMsg(
          "Verify admin configured client id is MyClientID from ServletClient");
      TestUtil.logMsg(
          "==================================================================");
      url = ctsurl.getURL("http", hostname, portnum, SERVLET);
      TestUtil.logMsg("Servlet URL: " + url);
      props.setProperty("TEST", "checkClientIDTestFromServletClient");
      urlConn = TestUtil.sendPostData(props, url);
      Properties p = TestUtil.getResponseProperties(urlConn);
      String passStr = p.getProperty("TESTRESULT");
      if (passStr.equals("fail")) {
        pass = false;
        TestUtil.logErr("Check ClientID test failed from Servlet");
      } else {
        TestUtil.logMsg("Check ClientID test passed from Servlet");
      }
      TestUtil.logMsg(
          "==============================================================");
      TestUtil.logMsg(
          "Verify admin configured client id is MyClientID from JspClient");
      TestUtil.logMsg(
          "==============================================================");
      url = ctsurl.getURL("http", hostname, portnum, JSP);
      TestUtil.logMsg("Jsp URL: " + url);
      props.setProperty("TEST", "checkClientIDTestFromJspClient");
      urlConn = TestUtil.sendPostData(props, url);
      p = TestUtil.getResponseProperties(urlConn);
      passStr = p.getProperty("TESTRESULT");
      if (passStr.equals("fail")) {
        pass = false;
        TestUtil.logErr("Check ClientID test failed from Jsp");
      } else {
        TestUtil.logMsg("Check ClientID test passed from Jsp");
      }
      TestUtil.logMsg(
          "==============================================================");
      TestUtil.logMsg(
          "Verify admin configured client id is MyClientID from EjbClient");
      TestUtil.logMsg(
          "==============================================================");
      boolean passEjb = ejbclient.echo("checkClientIDTestFromEjbClient");
      if (!passEjb) {
        pass = false;
        TestUtil.logErr("Check ClientID test failed from Ejb");
      } else {
        TestUtil.logMsg("Check ClientID test passed from Ejb");
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught exception: " + e);
      throw new Exception("checkClientIDOnDurableConnFactoryTest", e);
    } finally {
      try {
        if (context != null)
          context.close();
        doCleanup();
      } catch (Exception e) {
        TestUtil.logErr("Error cleanup " + e);
      }
    }

    if (!pass) {
      throw new Exception("checkClientIDOnDurableConnFactoryTest failed");
    }
  }

}
