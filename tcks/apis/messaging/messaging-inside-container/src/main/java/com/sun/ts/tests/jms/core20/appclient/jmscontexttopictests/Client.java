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

/*
 * $Id$
 */
package com.sun.ts.tests.jms.core20.appclient.jmscontexttopictests;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sun.ts.lib.harness.Status;
import com.sun.ts.tests.common.base.ServiceEETest;
import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.jms.common.JmsTool;

import jakarta.jms.BytesMessage;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.Destination;
import jakarta.jms.InvalidClientIDRuntimeException;
import jakarta.jms.InvalidDestinationRuntimeException;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.MapMessage;
import jakarta.jms.Message;
import jakarta.jms.MessageFormatRuntimeException;
import jakarta.jms.ObjectMessage;
import jakarta.jms.StreamMessage;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

public class Client extends ServiceEETest {
  private static final String testName = "com.sun.ts.tests.jms.core20.appclient.jmscontexttopictests.Client";

  private static final String testDir = System.getProperty("user.dir");

  private static final long serialVersionUID = 1L;

  // JMS tool which creates and/or looks up the JMS administered objects
  private transient JmsTool tool = null;

  private transient JmsTool tool2 = null;

  // JMS objects
  private transient ConnectionFactory cf = null;

  private transient ConnectionFactory cf2 = null;

  private transient Topic topic = null;

  private transient Destination destination = null;

  private transient JMSContext context = null;

  private transient JMSContext context2 = null;

  private transient JMSContext contextD = null;

  private transient JMSConsumer consumer = null;

  private transient JMSProducer producer = null;

  // Harness req's
  private Properties props = null;

  // properties read from ts.jte file
  long timeout;

  String user;

  String password;

  String mode;

  // used for tests
  private static final int numMessages = 3;

  private static final int iterations = 5;

  ArrayList connections = null;

  /* Run test in standalone mode */

  /**
   * Main method is used when not run from the JavaTest GUI.
   * 
   * @param args
   */
  public static void main(String[] args) {
    Client theTests = new Client();
    Status s = theTests.run(args, System.out, System.err);
    s.exit();
  }

  /*
   * Utility method to return the session mode as a String
   */
  private String printSessionMode(int sessionMode) {
    switch (sessionMode) {
    case JMSContext.SESSION_TRANSACTED:
      return "SESSION_TRANSACTED";
    case JMSContext.AUTO_ACKNOWLEDGE:
      return "AUTO_ACKNOWLEDGE";
    case JMSContext.CLIENT_ACKNOWLEDGE:
      return "CLIENT_ACKNOWLEDGE";
    case JMSContext.DUPS_OK_ACKNOWLEDGE:
      return "DUPS_OK_ACKNOWLEDGE";
    default:
      return "UNEXPECTED_SESSIONMODE";
    }
  }

  /* Test setup: */

  /*
   * setup() is called before each test
   * 
   * @class.setup_props: jms_timeout; user; password; platform.mode;
   * 
   * @exception Fault
   */
  public void setup(String[] args, Properties p) throws Exception {
    try {
      String lookupDurableTopicFactory = "DURABLE_SUB_CONNECTION_FACTORY";

      // get props
      timeout = Long.parseLong(p.getProperty("jms_timeout"));
      user = p.getProperty("user");
      password = p.getProperty("password");
      mode = p.getProperty("platform.mode");

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
      connections = new ArrayList(5);

      // set up JmsTool for COMMON_T setup
      TestUtil.logMsg("Setup JmsTool for COMMON_T setup");
      tool = new JmsTool(JmsTool.COMMON_T, user, password, mode);
      cf = tool.getConnectionFactory();
      destination = tool.getDefaultDestination();
      topic = (Topic) destination;
      tool.getDefaultConnection().close();
      TestUtil.logMsg("Create JMSContext with AUTO_ACKNOWLEDGE");
      context = cf.createContext(user, password, JMSContext.AUTO_ACKNOWLEDGE);

      // set up JmsTool for COMMON_T setup
      TestUtil.logMsg(
          "Setup JmsTool for COMMON_T and durable topic connection factory");
      tool2 = new JmsTool(JmsTool.COMMON_T, user, password,
          lookupDurableTopicFactory, mode);
      tool2.getDefaultConnection().close();
      cf2 = tool2.getConnectionFactory();
      contextD = cf2.createContext(user, password, JMSContext.AUTO_ACKNOWLEDGE);
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      throw new Exception("setup failed!", e);
    }
  }

  /* leanup */

  /*
   * cleanup() is called after each test
   * 
   * @exception Fault
   */
  public void cleanup() throws Exception {
    try {
      TestUtil.logMsg("Close JMSContext objects");
      if (context != null) {
        context.close();
        context = null;
      }
      if (contextD != null) {
        contextD.close();
        contextD = null;
      }
      TestUtil.logMsg("Close JMSConsumer objects");
      if (consumer != null) {
        consumer.close();
        consumer = null;
      }
      tool.closeAllResources();
      tool2.closeAllResources();
      producer = null;
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      throw new Exception("cleanup failed!", e);
    }
  }

  /*
   * @testName: setGetClientIDTest
   *
   * @assertion_ids: JMS:JAVADOC:970; JMS:JAVADOC:1040; JMS:SPEC:264.5;
   * JMS:SPEC:173; JMS:SPEC:198; JMS:SPEC:91;
   *
   * @test_Strategy: Test the following APIs:
   *
   * JMSContext.setClientID(String clientID) JMSContext.getClientID()
   */
  public void setGetClientIDTest() throws Exception {
    boolean pass = true;
    try {
      String clientid = "myclientid";
      TestUtil.logMsg("Calling setClientID(" + clientid + ")");
      context.setClientID(clientid);
      TestUtil.logMsg(
          "Calling getClientID and expect " + clientid + " to be returned");
      String cid = context.getClientID();
      if (!cid.equals(clientid)) {
        TestUtil
            .logErr("getClientID() returned " + cid + ", expected " + clientid);
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      throw new Exception("setGetClientIDTest");
    }

    if (!pass) {
      throw new Exception("setGetClientIDTest failed");
    }
  }

  /*
   * @testName: setClientIDLateTest
   *
   * @assertion_ids: JMS:SPEC:173; JMS:SPEC:198; JMS:SPEC:94; JMS:SPEC:91;
   * JMS:JAVADOC:1040; JMS:JAVADOC:1043; JMS:SPEC:264.5;
   *
   * @test_Strategy: Create a JMSContext, send and receive a message, then try
   * to set the ClientID. Verify that IllegalStateRuntimeException is thrown.
   *
   * JMSContext.setClientID(String clientID)
   */
  public void setClientIDLateTest() throws Exception {
    boolean pass = true;

    try {
      TextMessage messageSent;
      TextMessage messageReceived;
      String message = "This is my message.";

      // send and receive TextMessage
      TestUtil.logMsg("Creating JMSConsumer");
      consumer = context.createConsumer(destination);

      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      TestUtil.logMsg("Creating TextMessage");
      TextMessage expTextMessage = context.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "setClientIDLateTest");
      TestUtil.logMsg("Calling JMSProduce.send(Destination, Message)");
      producer.send(destination, expTextMessage);
      TestUtil.logMsg("Receive TextMessage");
      TextMessage actTextMessage = (TextMessage) consumer.receive(timeout);
      if (actTextMessage != null) {
        TestUtil.logMsg("actTextMessage=" + actTextMessage.getText());
      }

      TestUtil.logTrace(
          "Attempt to set Client ID too late (expect IllegalStateRuntimeException)");
      try {
        context.setClientID("setClientIDLateTest");
        pass = false;
        TestUtil.logErr("IllegalStateRuntimeException was not thrown");
      } catch (jakarta.jms.IllegalStateRuntimeException is) {
        TestUtil.logMsg("IllegalStateRuntimeException thrown as expected");
      } catch (Exception e) {
        TestUtil.logErr("Incorrect exception received: " + e);
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("setClientIDLateTest", e);
    }
    if (!pass) {
      throw new Exception("setClientIDLateTest failed");
    }
  }

  /*
   * @testName: setClientIDOnAdminConfiguredIDTest
   *
   * @assertion_ids: JMS:JAVADOC:970; JMS:JAVADOC:1040; JMS:SPEC:264.5;
   * JMS:SPEC:173; JMS:SPEC:198; JMS:SPEC:91;
   *
   * @test_Strategy: Test the following APIs:
   *
   * JMSContext.setClientID(String clientID)
   *
   * Expect IllegalStateRuntimeException
   */
  public void setClientIDOnAdminConfiguredIDTest() throws Exception {
    boolean pass = true;
    try {
      String clientid = "myclientid";
      TestUtil.logMsg("Try setting ClientID on JMSContext administratively "
          + "configured with ClientID (expect IllegalStateRuntimeException)");
      contextD.setClientID(clientid);
      TestUtil.logErr("Did not throw expected IllegalStateRuntimeException");
      pass = false;
      String cid = contextD.getClientID();
      if (cid == null) {
        TestUtil.logErr("getClientID returned null (expected cts)");
        pass = false;
      } else if (!cid.equals("cts")) {
        TestUtil.logErr("getClientID() returned " + cid + ", expected cts");
        pass = false;
      } else {
        TestUtil.logMsg("getClientID returned cts (Correct)");
      }
    } catch (jakarta.jms.IllegalStateRuntimeException is) {
      TestUtil.logMsg("IllegalStateRuntimeException thrown as expected");
    } catch (Exception e) {
      TestUtil.logErr("Incorrect exception received: " + e);
      throw new Exception("setClientIDOnAdminConfiguredIDTest");
    }

    if (!pass) {
      throw new Exception("setClientIDOnAdminConfiguredIDTest failed");
    }
  }

  /*
   * @testName: setGetChangeClientIDTest
   *
   * @assertion_ids: JMS:SPEC:93; JMS:SPEC:95; JMS:SPEC:198; JMS:JAVADOC:1040;
   * JMS:JAVADOC:970; JMS:JAVADOC:1042; JMS:JAVADOC:1043; JMS:SPEC:264.5;
   * 
   *
   * @test_Strategy: Test setClientID()/getClientID(). Make sure that the
   * clientID set is the clientID returned. Then try and reset the clientID.
   * Verify that the IllegalStateRuntimeException is thrown. 1) Use a JMSContext
   * that has no ClientID set, then call setClientID twice. 2) Try and set the
   * clientID on a second JMSContext to the clientID value of the first
   * JMSContext. Verify that InvalidClientIDRuntimeException is thrown.
   *
   * JMSContext.setClientID(String clientID) JMSContext.getClientID()
   */
  public void setGetChangeClientIDTest() throws Exception {
    boolean pass = true;
    String lookup = "MyTopicConnectionFactory";

    try {
      TestUtil.logMsg("Create second JMSContext with AUTO_ACKNOWLEDGE");
      context2 = cf.createContext(user, password, JMSContext.AUTO_ACKNOWLEDGE);

      TestUtil.logMsg("Setting clientID!");
      context.setClientID("ctstest");

      TestUtil.logMsg("Getting clientID!");
      String clientid = context.getClientID();

      if (!clientid.equals("ctstest")) {
        TestUtil.logErr(
            "getClientID() returned " + clientid + ", expected ctstest");
        pass = false;
      } else {
        TestUtil.logMsg("setClientID/getClientID correct");
      }

      TestUtil
          .logMsg("Resetting clientID! (expect IllegalStateRuntimeException)");
      context.setClientID("changeIt");
      TestUtil.logErr("No exception was thrown on ClientID reset");
      pass = false;
    } catch (jakarta.jms.IllegalStateRuntimeException e) {
      TestUtil.logMsg("IllegalStateRuntimeException thrown as expected");
    } catch (Exception e) {
      TestUtil.logErr("Incorrect exception received: " + e);
      pass = false;
    }

    try {
      TestUtil.logMsg(
          "Set clientID on second context to value of clientID on first "
              + "context (expect InvalidClientIDRuntimeException)");
      context2.setClientID("ctstest");
      TestUtil
          .logErr("No exception was thrown on ClientID that already exists");
      pass = false;
    } catch (InvalidClientIDRuntimeException e) {
      TestUtil.logMsg("InvalidClientIDRuntimeException thrown as expected");
    } catch (Exception e) {
      TestUtil.logErr("Incorrect exception received: " + e);
      pass = false;
    } finally {
      try {
        if (context2 != null) {
          context2.close();
          context2 = null;
        }
      } catch (Exception e) {
        TestUtil.logErr("Caught exception in finally block: " + e);
        throw new Exception("setGetChangeClientIDTest", e);
      }
    }

    if (!pass) {
      throw new Exception("setGetChangeClientIDTest");
    }
  }

  /*
   * @testName: setGetExceptionListenerTest
   *
   * @assertion_ids: JMS:JAVADOC:1052; JMS:JAVADOC:980;
   *
   * @test_Strategy: Test the following APIs:
   *
   * JMSContext.setExceptionListener(ExceptionListener).
   * JMSContext.getExceptionListener().
   */
  public void setGetExceptionListenerTest() throws Exception {
    boolean pass = true;
    try {
      MyExceptionListener expExceptionListener = new MyExceptionListener();
      TestUtil
          .logMsg("Calling setExceptionListener(" + expExceptionListener + ")");
      context.setExceptionListener(expExceptionListener);
      TestUtil.logMsg("Calling getExceptionListener and expect "
          + expExceptionListener + " to be returned");
      MyExceptionListener actExceptionListener = (MyExceptionListener) context
          .getExceptionListener();
      if (!actExceptionListener.equals(expExceptionListener)) {
        TestUtil.logErr("getExceptionListener() returned "
            + actExceptionListener + ", expected " + expExceptionListener);
        pass = false;
      }
      TestUtil.logMsg("Calling setExceptionListener(null)");
      context.setExceptionListener(null);
      if (context.getExceptionListener() != null) {
        TestUtil.logErr("getExceptionListener() returned "
            + context.getExceptionListener() + ", expected null");
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("setGetExceptionListenerTest");
    }

    if (!pass) {
      throw new Exception("setGetExceptionListenerTest failed");
    }
  }

  /*
   * @testName: setGetAsyncTest
   *
   * @assertion_ids: JMS:JAVADOC:1182; JMS:JAVADOC:1255;
   *
   * @test_Strategy: Test the following APIs:
   *
   * JMSContext.setAsync(CompletionListener). JMSContext.getAsync().
   */
  public void setGetAsyncTest() throws Exception {
    boolean pass = true;
    try {
      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      // Set and get asyncronous CompletionListener
      MyCompletionListener expCompletionListener = new MyCompletionListener();
      TestUtil.logMsg(
          "Calling JMSProducer.setAsync(" + expCompletionListener + ")");
      producer.setAsync(expCompletionListener);
      TestUtil.logMsg("Calling JMSProducer.getAsync() and expect "
          + expCompletionListener + " to be returned");
      MyCompletionListener actCompletionListener = (MyCompletionListener) producer
          .getAsync();
      if (!actCompletionListener.equals(expCompletionListener)) {
        TestUtil.logErr("getAsync() returned " + actCompletionListener
            + ", expected " + expCompletionListener);
        pass = false;
      }

      // Now set and get null asyncronous CompletionListener
      TestUtil.logMsg("Calling setAsync(null)");
      producer.setAsync(null);
      TestUtil.logMsg(
          "Calling JMSProducer.getAsync() and expect NULL to be returned");
      if (producer.getAsync() != null) {
        TestUtil.logErr(
            "getAsync() returned " + producer.getAsync() + ", expected null");
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("setGetAsyncTest");
    }

    if (!pass) {
      throw new Exception("setGetAsyncTest failed");
    }
  }

  /*
   * @testName: startStopTest
   *
   * @assertion_ids: JMS:JAVADOC:1234; JMS:JAVADOC:1076; JMS:JAVADOC:1078;
   * JMS:JAVADOC:942; JMS:JAVADOC:1102; JMS:SPEC:264; JMS:SPEC:264.5;
   *
   * @test_Strategy: Test the following APIs:
   *
   * ConnectionFactory.createContext(String, String, int) JMSContext.start()
   * JMSContext.stop() JMSContext.createConsumer(Destination)
   * JMSContext.createProducer() JMSProducer.send(Destination, Message)
   * JMSConsumer.receive(long timeout)
   *
   * 1. Create JMSContext with AUTO_ACKNOWLEDGE. This is done in the setup()
   * routine. 2. Call stop. 3. Send x messages to a Topic. 4. Create a
   * JMSConsumer to consume the messages in the Topic. 5. Try consuming messages
   * from the Topic. Should not receive any messages since the connection has
   * been stopped. 6. Call start. 7. Try consuming messages from the Topic.
   * Should receive all messages from the Topic since the connection has been
   * started.
   * 
   */
  public void startStopTest() throws Exception {
    boolean pass = true;
    try {
      TextMessage tempMsg = null;

      // Create JMSConsumer from JMSContext
      TestUtil.logMsg("Create JMSConsumer");
      consumer = context.createConsumer(destination);

      // Stop delivery of incoming messages on JMSContext's connection
      TestUtil.logMsg("Call stop() to stop delivery of incoming messages");
      context.stop();

      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      // Send "numMessages" messages to Topic
      TestUtil.logMsg("Send " + numMessages + " messages to Topic");
      for (int i = 1; i <= numMessages; i++) {
        tempMsg = context.createTextMessage("Message " + i);
        tempMsg.setStringProperty("COM_SUN_JMS_TESTNAME", "startStopTest" + i);
        producer.send(destination, tempMsg);
        TestUtil.logMsg("Message " + i + " sent");
      }

      // Try consuming a message from the Topic (should not receive a message)
      TestUtil.logMsg("Try consuming a message on a STOPPED connection");
      tempMsg = (TextMessage) consumer.receive(timeout);
      if (tempMsg != null) {
        TestUtil.logErr("Received a message on a STOPPED connection");
        TestUtil.logErr("Message is: " + tempMsg.getText());
        pass = false;
      }

      // Start delivery of incoming messages on JMSContext's connection
      TestUtil.logMsg("Call start() to start delivery of incoming messages");
      context.start();

      TestUtil.logMsg(
          "Consume all the messages in the Topic on a STARTED connection");
      for (int msgCount = 1; msgCount <= numMessages; msgCount++) {
        tempMsg = (TextMessage) consumer.receive(timeout);
        if (tempMsg == null) {
          TestUtil.logErr("JMSConsumer.receive() returned NULL");
          TestUtil.logErr("Message " + msgCount + " missing from Topic");
          pass = false;
        } else if (!tempMsg.getText().equals("Message " + msgCount)) {
          TestUtil.logErr("Received [" + tempMsg.getText()
              + "] expected [Message " + msgCount + "]");
          pass = false;
        } else {
          TestUtil.logMsg("Received message: " + tempMsg.getText());
        }
      }

      // Try to receive one more message (should return null for no more
      // messages)
      TestUtil.logMsg("Topic should now be empty");
      TestUtil.logMsg("Try consuming one more message should return NULL");
      tempMsg = (TextMessage) consumer.receive(timeout);
      if (tempMsg != null) {
        TestUtil.logErr("JMSConsumer.receive() did not return NULL");
        TestUtil
            .logErr("JMSConsumer.receive() returned a message (unexpected)");
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("startStopTest");
    }

    if (!pass) {
      throw new Exception("startStopTest failed");
    }
  }

  /*
   * @testName: createContextTest
   *
   * @assertion_ids: JMS:JAVADOC:931; JMS:SPEC:265.3;
   *
   * @test_Strategy: Creates a JMSContext with the default user identity and the
   * specified sessionMode. Tests API:
   *
   * JMSContext.createContext(int)
   */
  public void createContextTest() throws Exception {
    boolean pass = true;
    try {

      JMSContext newContext = null;

      // Test all possible session modes
      int expSessionMode[] = { JMSContext.SESSION_TRANSACTED,
          JMSContext.AUTO_ACKNOWLEDGE, JMSContext.CLIENT_ACKNOWLEDGE,
          JMSContext.DUPS_OK_ACKNOWLEDGE, };

      // Cycle through all session modes
      for (int i = 0; i < expSessionMode.length; i++) {
        TestUtil.logMsg("Creating context with session mode ("
            + printSessionMode(expSessionMode[i]) + ")");
        TestUtil.logMsg("Call API TopicConnectionFactory.createContext(int)");
        newContext = context.createContext(expSessionMode[i]);
        TestUtil.logMsg("Now call API JMSContext.getSessionMode()");
        TestUtil.logMsg("Calling getSessionMode and expect "
            + printSessionMode(expSessionMode[i]) + " to be returned");
        int actSessionMode = newContext.getSessionMode();
        if (actSessionMode != expSessionMode[i]) {
          TestUtil.logErr(
              "getSessionMode() returned " + printSessionMode(actSessionMode)
                  + ", expected " + printSessionMode(expSessionMode[i]));
          pass = false;
        }
        newContext.close();
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("createContextTest");
    }

    if (!pass) {
      throw new Exception("createContextTest failed");
    }
  }

  /*
   * @testName: sendAndRecvCLTest1
   *
   * @assertion_ids: JMS:JAVADOC:1234; JMS:JAVADOC:835; JMS:JAVADOC:1177;
   * JMS:JAVADOC:1255; JMS:SPEC:275.1; JMS:SPEC:275.5; JMS:SPEC:275.8;
   *
   * @test_Strategy: Send a message using the following API method and verify
   * the send and recv of data as well as onCompletion() being called. Set some
   * properties on JMSProducer and check that these properties exist on the
   * returned message after the CompletionListener's onCompletion() method has
   * been called.
   *
   * JMSContext.createProducer() JMSProducer.setAsync(CompletionListener)
   * JMSProducer.send(Destination, Message)
   */
  public void sendAndRecvCLTest1() throws Exception {
    boolean pass = true;
    String message = "Where are you!";
    boolean bool = true;
    byte bValue = 127;
    short nShort = 10;
    int nInt = 5;
    long nLong = 333;
    float nFloat = 1;
    double nDouble = 100;
    String testString = "test";

    try {
      // send and receive TextMessage
      TestUtil.logMsg("Creating TextMessage");
      TextMessage expTextMessage = context.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvCLTest1");

      // Create CompletionListener for Message to be sent
      MyCompletionListener listener = new MyCompletionListener();

      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      // ------------------------------------------------------------------------------
      // Set JMSProducer message properties
      // Set properties for boolean, byte, short, int, long, float, double, and
      // String.
      // ------------------------------------------------------------------------------
      TestUtil.logMsg("Set primitive property types on JMSProducer");
      producer.setProperty("TESTBOOLEAN", bool);
      producer.setProperty("TESTBYTE", bValue);
      producer.setProperty("TESTDOUBLE", nDouble);
      producer.setProperty("TESTFLOAT", nFloat);
      producer.setProperty("TESTINT", nInt);
      producer.setProperty("TESTLONG", nLong);
      producer.setProperty("TESTSHORT", nShort);
      producer.setProperty("TESTSTRING", "test");

      // ------------------------------------------------------------------------------
      // Set JMSProducer message properties
      // Set properties for Boolean, Byte, Short, Int, Long, Float, Double, and
      // String.
      // ------------------------------------------------------------------------------
      TestUtil.logMsg("Set Object property types on JMSProducer");
      producer.setProperty("OBJTESTBOOLEAN", Boolean.valueOf(bool));
      producer.setProperty("OBJTESTBYTE", Byte.valueOf(bValue));
      producer.setProperty("OBJTESTDOUBLE", Double.valueOf(nDouble));
      producer.setProperty("OBJTESTFLOAT", Float.valueOf(nFloat));
      producer.setProperty("OBJTESTINT", Integer.valueOf(nInt));
      producer.setProperty("OBJTESTLONG", Long.valueOf(nLong));
      producer.setProperty("OBJTESTSHORT", Short.valueOf(nShort));
      producer.setProperty("OBJTESTSTRING", "test");

      TestUtil.logMsg("Calling JMSProducer.setAsync(CompletionListener)");
      producer.setAsync(listener);
      TestUtil.logMsg("Calling JMSProducer.send(Destination,Message)");
      producer.send(destination, expTextMessage);
      TestUtil.logMsg("Poll listener until we receive TextMessage");
      TextMessage actTextMessage = null;
      for (int i = 0; !listener.isComplete() && i < 60; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for messages to arrive at listener");
        TestUtil.sleepSec(2);
      }
      if (listener.isComplete())
        actTextMessage = (TextMessage) listener.getMessage();

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

      // --------------------------------------------------------------------------------------
      // Retrieve the properties from the received TextMessage and verify that
      // they are correct
      // Get properties for boolean, byte, short, int, long, float, double, and
      // String.
      // -------------------------------------------------------------------------------------
      TestUtil.logMsg(
          "Retrieve and verify that TextMessage message properties were set correctly");
      if (actTextMessage.getBooleanProperty("TESTBOOLEAN") == bool) {
        TestUtil.logMsg("Pass: getBooleanProperty returned correct value");
      } else {
        TestUtil
            .logMsg("Fail: incorrect value returned from getBooleanProperty");
        pass = false;
      }
      if (actTextMessage.getByteProperty("TESTBYTE") == bValue) {
        TestUtil.logMsg("Pass: getByteProperty returned correct value");
      } else {
        TestUtil.logMsg("Fail: incorrect value returned from getByteProperty");
        pass = false;
      }
      if (actTextMessage.getLongProperty("TESTLONG") == nLong) {
        TestUtil.logMsg("Pass: getLongProperty returned correct value");
      } else {
        TestUtil.logMsg("Fail: incorrect value returned from getLongProperty");
        pass = false;
      }
      if (actTextMessage.getStringProperty("TESTSTRING").equals(testString)) {
        TestUtil.logMsg("Pass: getStringProperty returned correct value");
      } else {
        TestUtil
            .logMsg("Fail: incorrect value returned from getStringProperty");
        pass = false;
      }
      if (actTextMessage.getDoubleProperty("TESTDOUBLE") == nDouble) {
        TestUtil.logMsg("Pass: getDoubleProperty returned correct value");
      } else {
        TestUtil
            .logMsg("Fail: incorrect value returned from getDoubleProperty");
        pass = false;
      }
      if (actTextMessage.getFloatProperty("TESTFLOAT") == nFloat) {
        TestUtil.logMsg("Pass: getFloatProperty returned correct value");
      } else {
        TestUtil.logMsg("Fail: incorrect value returned from getFloatProperty");
        pass = false;
      }
      if (actTextMessage.getIntProperty("TESTINT") == nInt) {
        TestUtil.logMsg("Pass: getIntProperty returned correct value");
      } else {
        TestUtil.logMsg("Fail: incorrect value returned from getIntProperty");
        pass = false;
      }
      if (actTextMessage.getShortProperty("TESTSHORT") == nShort) {
        TestUtil.logMsg("Pass: getShortProperty returned correct value");
      } else {
        TestUtil.logMsg("Fail: incorrect value returned from getShortProperty");
        pass = false;
      }

      // --------------------------------------------------------------------------------------
      // Retrieve the properties from the received TextMessage and verify that
      // they are correct
      // Get properties for Boolean, Byte, Short, Integer, Long, Float, Double,
      // and String.
      // --------------------------------------------------------------------------------------
      if (((Boolean) actTextMessage.getObjectProperty("OBJTESTBOOLEAN"))
          .booleanValue() == bool) {
        TestUtil
            .logMsg("Pass: getObjectProperty returned correct Boolean value");
      } else {
        TestUtil.logMsg(
            "Fail: incorrect Boolean value returned from getObjectProperty");
        pass = false;
      }
      if (((Byte) actTextMessage.getObjectProperty("OBJTESTBYTE"))
          .byteValue() == bValue) {
        TestUtil.logMsg("Pass: getObjectProperty returned correct Byte value");
      } else {
        TestUtil.logMsg(
            "Fail: incorrect Byte value returned from getObjectProperty");
        pass = false;
      }
      if (((Long) actTextMessage.getObjectProperty("OBJTESTLONG"))
          .longValue() == nLong) {
        TestUtil.logMsg("Pass: getObjectProperty returned correct Long value");
      } else {
        TestUtil.logMsg(
            "Fail: incorrect Long value returned from getObjectProperty");
        pass = false;
      }
      if (((String) actTextMessage.getObjectProperty("OBJTESTSTRING"))
          .equals(testString)) {
        TestUtil
            .logMsg("Pass: getObjectProperty returned correct String value");
      } else {
        TestUtil.logMsg(
            "Fail: incorrect String value returned from getObjectProperty");
        pass = false;
      }
      if (((Double) actTextMessage.getObjectProperty("OBJTESTDOUBLE"))
          .doubleValue() == nDouble) {
        TestUtil
            .logMsg("Pass: getObjectProperty returned correct Double value");
      } else {
        TestUtil.logMsg(
            "Fail: incorrect Double value returned from getObjectProperty");
        pass = false;
      }
      if (((Float) actTextMessage.getObjectProperty("OBJTESTFLOAT"))
          .floatValue() == nFloat) {
        TestUtil.logMsg("Pass: getObjectProperty returned correct Float value");
      } else {
        TestUtil.logMsg(
            "Fail: incorrect Float value returned from getObjectProperty");
        pass = false;
      }
      if (((Integer) actTextMessage.getObjectProperty("OBJTESTINT"))
          .intValue() == nInt) {
        TestUtil
            .logMsg("Pass: getObjectProperty returned correct Integer value");
      } else {
        TestUtil.logMsg(
            "Fail: incorrect Integer value returned from getObjectProperty");
        pass = false;
      }
      if (((Short) actTextMessage.getObjectProperty("OBJTESTSHORT"))
          .shortValue() == nShort) {
        TestUtil.logMsg("Pass: getObjectProperty returned correct Short value");
      } else {
        TestUtil.logMsg(
            "Fail: incorrect Short value returned from getObjectProperty");
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("sendAndRecvCLTest1", e);
    }

    if (!pass) {
      throw new Exception("sendAndRecvCLTest1 failed");
    }
  }

  /*
   * @testName: sendAndRecvCLTest2
   *
   * @assertion_ids: JMS:JAVADOC:1234; JMS:JAVADOC:835; JMS:JAVADOC:1177;
   * JMS:JAVADOC:1255; JMS:JAVADOC:1259; JMS:JAVADOC:1303;
   *
   * @test_Strategy: Send a message using the following API method and verify
   * the send and recv of data as well as onCompletion() being called.
   *
   * JMSContext.createProducer() JMSProducer.setDeliveryMode(int)
   * JMSProducer.setPriority(int) JMSProducer.setTimeToLive(long)
   * JMSProducer.setAsync(CompletionListener) JMSProducer.send(Destination,
   * Message)
   */
  public void sendAndRecvCLTest2() throws Exception {
    boolean pass = true;
    String message = "Where are you!";
    try {
      // send and receive TextMessage
      TestUtil.logMsg("Creating TextMessage");
      TextMessage expTextMessage = context.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvCLTest2");

      // Create CompletionListener for Message to be sent
      MyCompletionListener listener = new MyCompletionListener();

      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      TestUtil.logMsg("Calling JMSProducer.setAsync(CompletionListener)");
      producer.setAsync(listener);
      TestUtil.logMsg("Set delivery mode, priority, and time to live");
      producer.setDeliveryMode(DeliveryMode.PERSISTENT);
      producer.setPriority(Message.DEFAULT_PRIORITY);
      producer.setTimeToLive(0L);
      TestUtil.logMsg("Calling JMSProducer.send(Destination,Message)");
      producer.send(destination, expTextMessage);
      TestUtil.logMsg("Poll listener until we receive TextMessage");
      TextMessage actTextMessage = null;
      for (int i = 0; !listener.isComplete() && i < 60; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for messages to arrive at listener");
        TestUtil.sleepSec(2);
      }
      if (listener.isComplete())
        actTextMessage = (TextMessage) listener.getMessage();

      if (actTextMessage == null) {
        throw new Exception("Did not receive TextMessage");
      }
      TestUtil.logMsg(
          "Check the values in TextMessage, deliverymode, priority, time to live");
      if (!actTextMessage.getText().equals(expTextMessage.getText())
          || actTextMessage.getJMSDeliveryMode() != DeliveryMode.PERSISTENT
          || actTextMessage.getJMSPriority() != Message.DEFAULT_PRIORITY
          || actTextMessage.getJMSExpiration() != 0L) {
        TestUtil.logErr("Didn't get the right message.");
        TestUtil.logErr("text=" + actTextMessage.getText() + ", expected "
            + expTextMessage.getText());
        TestUtil.logErr("DeliveryMode=" + actTextMessage.getJMSDeliveryMode()
            + ", expected " + expTextMessage.getJMSDeliveryMode());
        TestUtil.logErr("Priority=" + actTextMessage.getJMSPriority()
            + ", expected " + expTextMessage.getJMSPriority());
        TestUtil.logErr("TimeToLive=" + actTextMessage.getJMSExpiration()
            + ", expected " + expTextMessage.getJMSExpiration());
        pass = false;
      } else {
        TestUtil.logMsg("TextMessage is correct");
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("sendAndRecvCLTest2", e);
    }

    if (!pass) {
      throw new Exception("sendAndRecvCLTest2 failed");
    }
  }

  /*
   * @testName: sendAndRecvMsgOfEachTypeCLTest
   *
   * @assertion_ids: JMS:JAVADOC:1234; JMS:JAVADOC:835; JMS:JAVADOC:1177;
   * JMS:JAVADOC:1255; JMS:JAVADOC:1259; JMS:JAVADOC:1303;
   *
   * @test_Strategy: Send and receive messages of each message type: Message,
   * BytesMessage, MapMessage, ObjectMessage, StreamMessage, TextMessage. Verify
   * the send and recv of data as well as onCompletion() being called in
   * CompletionListener.
   *
   * JMSContext.createProducer() JMSProducer.setAsync(CompletionListener)
   * JMSContext.createMessage() JMSContext.createBytesMessage()
   * JMSContext.createMapMessage() JMSContext.createObjectMessage()
   * JMSContext.createStreamMessage() JMSContext.createTextMessage(String)
   * JMSProducer.send(Destination, Message)
   */
  public void sendAndRecvMsgOfEachTypeCLTest() throws Exception {
    boolean pass = true;
    String message = "Where are you!";
    try {

      // send and receive Message
      TestUtil.logMsg("Send and receive Message");
      Message msg = context.createMessage();
      TestUtil.logMsg("Set some values in Message");
      msg.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvMsgOfEachTypeCLTest");
      msg.setBooleanProperty("booleanProperty", true);
      MyCompletionListener2 listener = new MyCompletionListener2();
      producer = context.createProducer();
      producer.setAsync(listener);
      producer.send(destination, msg);
      TestUtil.logMsg("Poll listener until we receive Message");
      for (int i = 0; !listener.isComplete() && i < 15; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for message to arrive at listener");
        TestUtil.sleepSec(2);
      }
      Message actMessage = null;
      if (listener.isComplete())
        actMessage = (Message) listener.getMessage();

      if (actMessage == null) {
        TestUtil.logErr("Did not receive Message");
        pass = false;
      } else {
        TestUtil.logMsg("Check the values in Message");
        if (actMessage.getBooleanProperty("booleanProperty") == true) {
          TestUtil.logMsg("booleanproperty is correct");
        } else {
          TestUtil.logMsg("booleanproperty is incorrect");
          pass = false;
        }
      }

      // send and receive BytesMessage
      TestUtil.logMsg("Send and receive BytesMessage");
      BytesMessage bMsg = context.createBytesMessage();
      TestUtil.logMsg("Set some values in BytesMessage");
      bMsg.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvMsgOfEachTypeCLTest");
      bMsg.writeByte((byte) 1);
      bMsg.writeInt((int) 22);
      bMsg.reset();
      listener = new MyCompletionListener2();
      producer = context.createProducer();
      producer.setAsync(listener);
      producer.send(destination, bMsg);
      TestUtil.logMsg("Poll listener until we receive BytesMessage");
      for (int i = 0; !listener.isComplete() && i < 15; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for message to arrive at listener");
        TestUtil.sleepSec(2);
      }
      BytesMessage bMsgRecv = null;
      if (listener.isComplete())
        bMsgRecv = (BytesMessage) listener.getMessage();
      if (bMsgRecv == null) {
        TestUtil.logErr("Did not receive BytesMessage");
        pass = false;
      } else {
        TestUtil.logMsg("Check the values in BytesMessage");
        if (bMsgRecv.readByte() == (byte) 1) {
          TestUtil.logMsg("bytevalue is correct");
        } else {
          TestUtil.logMsg("bytevalue is incorrect");
          pass = false;
        }
        if (bMsgRecv.readInt() == (int) 22) {
          TestUtil.logMsg("intvalue is correct");
        } else {
          TestUtil.logMsg("intvalue is incorrect");
          pass = false;
        }
      }

      // send and receive MapMessage
      TestUtil.logMsg("Send and receive MapMessage");
      MapMessage mMsg = context.createMapMessage();
      TestUtil.logMsg("Set some values in MapMessage");
      mMsg.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvMsgOfEachTypeCLTest");
      mMsg.setBoolean("booleanvalue", true);
      mMsg.setInt("intvalue", (int) 10);
      listener = new MyCompletionListener2();
      producer = context.createProducer();
      producer.setAsync(listener);
      producer.send(destination, mMsg);
      TestUtil.logMsg("Poll listener until we receive MapMessage");
      for (int i = 0; !listener.isComplete() && i < 15; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for message to arrive at listener");
        TestUtil.sleepSec(2);
      }
      MapMessage mMsgRecv = null;
      if (listener.isComplete())
        mMsgRecv = (MapMessage) listener.getMessage();
      if (mMsgRecv == null) {
        TestUtil.logErr("Did not receive MapMessage");
        pass = false;
      } else {
        TestUtil.logMsg("Check the values in MapMessage");
        Enumeration list = mMsgRecv.getMapNames();
        String name = null;
        while (list.hasMoreElements()) {
          name = (String) list.nextElement();
          if (name.equals("booleanvalue")) {
            if (mMsgRecv.getBoolean(name) == true) {
              TestUtil.logMsg("booleanvalue is correct");
            } else {
              TestUtil.logErr("booleanvalue is incorrect");
              pass = false;
            }
          } else if (name.equals("intvalue")) {
            if (mMsgRecv.getInt(name) == 10) {
              TestUtil.logMsg("intvalue is correct");
            } else {
              TestUtil.logErr("intvalue is incorrect");
              pass = false;
            }
          } else {
            TestUtil.logErr("Unexpected name of [" + name + "] in MapMessage");
            pass = false;
          }
        }
      }

      // send and receive ObjectMessage
      TestUtil.logMsg("Send and receive ObjectMessage");
      StringBuffer sb1 = new StringBuffer("This is a StringBuffer");
      TestUtil.logMsg("Set some values in ObjectMessage");
      ObjectMessage oMsg = context.createObjectMessage();
      oMsg.setObject(sb1);
      oMsg.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvMsgOfEachTypeCLTest");
      listener = new MyCompletionListener2();
      producer = context.createProducer();
      producer.setAsync(listener);
      producer.send(destination, oMsg);
      TestUtil.logMsg("Poll listener until we receive ObjectMessage");
      for (int i = 0; !listener.isComplete() && i < 15; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for message to arrive at listener");
        TestUtil.sleepSec(2);
      }
      ObjectMessage oMsgRecv = null;
      if (listener.isComplete())
        oMsgRecv = (ObjectMessage) listener.getMessage();
      if (oMsgRecv == null) {
        TestUtil.logErr("Did not receive ObjectMessage");
        pass = false;
      } else {
        TestUtil.logMsg("Check the values in ObjectMessage");
        StringBuffer sb2 = (StringBuffer) oMsgRecv.getObject();
        if (sb2.toString().equals(sb1.toString())) {
          TestUtil.logMsg("objectvalue is correct");
        } else {
          TestUtil.logErr("objectvalue is incorrect");
          pass = false;
        }
      }

      // send and receive StreamMessage
      TestUtil.logMsg("Send and receive StreamMessage");
      StreamMessage sMsg = context.createStreamMessage();
      TestUtil.logMsg("Set some values in StreamMessage");
      sMsg.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvMsgOfEachTypeCLTest");
      sMsg.writeBoolean(true);
      sMsg.writeInt((int) 22);
      sMsg.reset();
      listener = new MyCompletionListener2();
      producer = context.createProducer();
      producer.setAsync(listener);
      producer.send(destination, sMsg);
      TestUtil.logMsg("Poll listener until we receive StreamMessage");
      for (int i = 0; !listener.isComplete() && i < 15; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for message to arrive at listener");
        TestUtil.sleepSec(2);
      }
      StreamMessage sMsgRecv = null;
      if (listener.isComplete())
        sMsgRecv = (StreamMessage) listener.getMessage();
      if (sMsgRecv == null) {
        TestUtil.logErr("Did not receive StreamMessage");
        pass = false;
      } else {
        TestUtil.logMsg("Check the values in StreamMessage");
        if (sMsgRecv.readBoolean() == true) {
          TestUtil.logMsg("booleanvalue is correct");
        } else {
          TestUtil.logMsg("booleanvalue is incorrect");
          pass = false;
        }
        if (sMsgRecv.readInt() == (int) 22) {
          TestUtil.logMsg("intvalue is correct");
        } else {
          TestUtil.logMsg("intvalue is incorrect");
          pass = false;
        }
      }

      // send and receive TextMessage
      TestUtil.logMsg("Send and receive TextMessage");
      TextMessage expTextMessage = context.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "sendAndRecvMsgOfEachTypeCLTest");
      TestUtil.logMsg("Calling JMSProducer.send(Destination,Message)");
      listener = new MyCompletionListener2();
      producer = context.createProducer();
      producer.setAsync(listener);
      producer.send(destination, expTextMessage);
      TestUtil.logMsg("Poll listener until we receive TextMessage");
      for (int i = 0; !listener.isComplete() && i < 15; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for message to arrive at listener");
        TestUtil.sleepSec(2);
      }
      TextMessage actTextMessage = null;
      if (listener.isComplete())
        actTextMessage = (TextMessage) listener.getMessage();

      if (actTextMessage == null) {
        throw new Exception("Did not receive TextMessage");
      }
      TestUtil.logMsg("Check the values in TextMessage");
      if (!actTextMessage.getText().equals(expTextMessage.getText())) {
        TestUtil.logErr("Didn't get the right message.");
        TestUtil.logErr("text=" + actTextMessage.getText() + ", expected "
            + expTextMessage.getText());
        pass = false;
      } else {
        TestUtil.logMsg("TextMessage is correct");
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("sendAndRecvMsgOfEachTypeCLTest", e);
    }

    if (!pass) {
      throw new Exception("sendAndRecvMsgOfEachTypeCLTest failed");
    }
  }

  /*
   * @testName: messageOrderCLTopicTest
   * 
   * @assertion_ids: JMS:SPEC:275.2; JMS:SPEC:275.7;
   * 
   * @test_Strategy: Send async messages to a topic and receive them. Verify
   * that the text of each matches the order of the text in the sent messages.
   */
  public void messageOrderCLTopicTest() throws Exception {
    boolean pass = true;
    try {
      TextMessage sentTextMessage;
      String text[] = new String[numMessages];

      // Create CompletionListener for Message to be sent
      TestUtil.logMsg("Creating MyCompletionListener");
      MyCompletionListener listener = new MyCompletionListener(numMessages);

      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      TestUtil.logMsg("Calling JMSProducer.setAsync(CompletionListener)");
      producer.setAsync(listener);

      // create and send async messages to topic
      TestUtil
          .logMsg("Sending " + numMessages + " asynchronous messages to topic");
      for (int i = 0; i < numMessages; i++) {
        text[i] = "message order test " + i;
        sentTextMessage = context.createTextMessage();
        sentTextMessage.setText(text[i]);
        sentTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
            "messageOrderCLTopicTest" + i);
        TestUtil.logMsg("Sending TextMessage: " + sentTextMessage.getText());
        producer.send(destination, sentTextMessage);
      }

      TestUtil.logMsg("Poll listener until we receive all " + numMessages
          + " TextMessage's from topic");
      for (int i = 0; !listener.gotAllMsgs() && i < 60; i++) {
        TestUtil.logMsg("Loop " + i
            + ": sleep 2 seconds waiting for messages to arrive at listener");
        TestUtil.sleepSec(2);
      }

      for (int i = 0; i < numMessages; i++) {
        TextMessage actTextMessage = null;
        if (listener.haveMsg(i))
          actTextMessage = (TextMessage) listener.getMessage(i);
        if (actTextMessage == null) {
          TestUtil.logMsg("Did not receive TextMessage " + i + " (unexpected)");
          pass = false;
        } else {
          TestUtil.logMsg("Received message: " + actTextMessage.getText());
          if (!actTextMessage.getText().equals(text[i])) {
            TestUtil.logMsg("Received message: " + actTextMessage.getText());
            TestUtil.logMsg("Should have received: " + text[i]);
            TestUtil.logErr("Received wrong message (wrong order)");
            pass = false;
          }
        }
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("messageOrderCLTopicTest", e);
    }

    if (!pass)
      throw new Exception("messageOrderCLTopicTest failed");
  }

  /*
   * @testName: acknowledgeTest
   *
   * @assertion_ids: JMS:JAVADOC:909;
   *
   * @test_Strategy: Send and receive a message from JMSContext with
   * CLIENT_ACKNOWLEDGE and call the acknowlege method to acknowledge the
   * message consumed.
   *
   */
  public void acknowledgeTest() throws Exception {
    boolean pass = true;
    String message = "Where are you!";
    JMSContext msgcontext = null;
    JMSProducer msgproducer = null;
    JMSConsumer msgconsumer = null;
    try {
      msgcontext = cf.createContext(user, password,
          JMSContext.CLIENT_ACKNOWLEDGE);
      msgproducer = msgcontext.createProducer();
      msgconsumer = msgcontext.createConsumer(topic);

      // send and receive TextMessage
      TestUtil.logMsg("Creating TextMessage");
      TextMessage expTextMessage = msgcontext.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "acknowledgeTest");
      TestUtil.logMsg(
          "Sending TextMessage via JMSProducer.send(Destination, Message)");
      msgproducer.send(destination, expTextMessage);
      TestUtil.logMsg("Receive TextMessage");
      TextMessage actTextMessage = (TextMessage) msgconsumer.receive(timeout);
      TestUtil.logMsg("Received message: " + actTextMessage.getText());
      TestUtil.logMsg("Calling acknowlege to acknowledge message.");
      msgcontext.acknowledge();
    } catch (Exception e) {
      TestUtil.logErr("Caught exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("acknowledgeTest", e);
    } finally {
      try {
        if (msgconsumer != null)
          msgconsumer.close();
        if (msgcontext != null)
          msgcontext.close();
        msgproducer = null;
      } catch (Exception e) {
      }
    }

    if (!pass) {
      throw new Exception("acknowledgeTest failed");
    }
  }

  /*
   * @testName: commitRollbackTest
   *
   * @assertion_ids: JMS:JAVADOC:1234; JMS:JAVADOC:914; JMS:JAVADOC:995;
   * JMS:JAVADOC:942; JMS:JAVADOC:1102; JMS:JAVADOC:847; JMS:SPEC:275.3;
   *
   * @test_Strategy: Test the following APIs:
   *
   * ConnectionFactory.createContext(String, String, int)
   * JMSProducer.send(Destination, Message) JMSContext.commit()
   * JMSContext.rollback() JMSContext.createConsumer(Destination)
   * JMSContext.receive(long timeout)
   * 
   * 1. Create JMSContext with SESSION_TRANSACTED. This is done in the setup()
   * routine. 2. Send x messages to a Topic. 3. Call rollback() to rollback the
   * sent messages. 4. Create a JMSConsumer to consume the messages in the
   * Topic. Should not receive any messages since the sent messages were rolled
   * back. Verify that no messages are received. 5. Send x messages to a Topic.
   * 6. Call commit() to commit the sent messages. 7. Create a JMSConsumer to
   * consume the messages in the Topic. Should receive all the messages since
   * the sent messages were committed. Verify that all messages are received.
   */
  public void commitRollbackTest() throws Exception {
    boolean pass = true;
    try {
      TextMessage tempMsg = null;

      // Close conttext created in setup()
      context.close();

      // create JMSContext with SESSION_TRANSACTED then create consumer/producer
      TestUtil
          .logMsg("Create transacted JMSContext, JMSConsumer and JMSProducer");
      context = cf.createContext(user, password, JMSContext.SESSION_TRANSACTED);
      producer = context.createProducer();
      consumer = context.createConsumer(destination);

      // Send "numMessages" messages to Topic and call rollback
      TestUtil.logMsg(
          "Send " + numMessages + " messages to Topic and call rollback()");
      for (int i = 1; i <= numMessages; i++) {
        tempMsg = context.createTextMessage("Message " + i);
        tempMsg.setStringProperty("COM_SUN_JMS_TESTNAME",
            "commitRollbackTest" + i);
        producer.send(destination, tempMsg);
        TestUtil.logMsg("Message " + i + " sent");
      }

      // Call rollback() to rollback the sent messages
      TestUtil.logMsg("Calling rollback() to rollback the sent messages");
      context.rollback();

      TestUtil.logMsg(
          "Should not consume any messages in Topic since rollback() was called");
      tempMsg = (TextMessage) consumer.receive(timeout);
      if (tempMsg != null) {
        TestUtil.logErr("Received message " + tempMsg.getText()
            + ", expected NULL (NO MESSAGES)");
        pass = false;
      }

      // Send "numMessages" messages to Topic and call commit
      TestUtil.logMsg(
          "Send " + numMessages + " messages to Topic and call commit()");
      for (int i = 1; i <= numMessages; i++) {
        tempMsg = context.createTextMessage("Message " + i);
        tempMsg.setStringProperty("COM_SUN_JMS_TESTNAME",
            "commitRollbackTest" + i);
        producer.send(destination, tempMsg);
        TestUtil.logMsg("Message " + i + " sent");
      }

      // Call commit() to commit the sent messages
      TestUtil.logMsg("Calling commit() to commit the sent messages");
      context.commit();

      TestUtil.logMsg(
          "Should consume all messages in Topic since commit() was called");
      for (int msgCount = 1; msgCount <= numMessages; msgCount++) {
        tempMsg = (TextMessage) consumer.receive(timeout);
        if (tempMsg == null) {
          TestUtil.logErr("JMSConsumer.receive() returned NULL");
          TestUtil.logErr("Message " + msgCount + " missing from Topic");
          pass = false;
        } else if (!tempMsg.getText().equals("Message " + msgCount)) {
          TestUtil.logErr("Received [" + tempMsg.getText()
              + "] expected [Message " + msgCount + "]");
          pass = false;
        } else {
          TestUtil.logMsg("Received message: " + tempMsg.getText());
        }
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught exception: " + e);
      TestUtil.printStackTrace(e);
      throw new Exception("commitRollbackTest");
    }

    if (!pass) {
      throw new Exception("commitRollbackTest failed");
    }
  }

  /*
   * @testName: invalidDestinationRuntimeExceptionTests
   *
   * @assertion_ids: JMS:JAVADOC:1237;
   *
   * @test_Strategy: Test InvalidDestinationRuntimeException conditions from API
   * methods with CompletionListener.
   *
   * Tests the following exception conditions:
   *
   * InvalidDestinationRuntimeException
   *
   */
  public void invalidDestinationRuntimeExceptionTests() throws Exception {
    boolean pass = true;
    Destination invalidDestination = null;
    Topic invalidTopic = null;
    String message = "Where are you!";
    Map<String, Object> mapMsgSend = new HashMap<String, Object>();
    mapMsgSend.put("StringValue", "sendAndRecvTest7");
    mapMsgSend.put("BooleanValue", true);
    mapMsgSend.put("IntValue", (int) 10);
    try {

      // Create JMSConsumer from JMSContext
      TestUtil.logMsg("Creating JMSConsumer");
      consumer = context.createConsumer(destination);

      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      // send to an invalid destination
      TestUtil.logMsg("Creating TextMessage");
      TextMessage expTextMessage = context.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "invalidDestinationRuntimeExceptionTests");

      // Create CompetionListener
      MyCompletionListener listener = new MyCompletionListener();

      TestUtil.logMsg(
          "Testing JMSProducer.send(Destination, Message) for InvalidDestinationRuntimeException");
      try {
        TestUtil.logMsg("Calling JMSProducer.setAsync(CompletionListener)");
        producer.setAsync(listener);
        TestUtil.logMsg(
            "Calling JMSProducer.send(Destination, Message) -> expect InvalidDestinationRuntimeException");
        producer.send(invalidDestination, expTextMessage);
        TestUtil.logMsg("Poll listener until we receive exception");
        Exception exception = null;
        for (int i = 0; i < 30; i++) {
          if (listener.isComplete()) {
            break;
          } else {
            TestUtil.sleepSec(2);
          }
        }
        exception = listener.getException();

        if (exception == null) {
          pass = false;
          TestUtil.logErr("Didn't throw and exception");
        } else {
          TestUtil.logMsg("Check the value in Exception");
          if (exception instanceof InvalidDestinationRuntimeException) {
            TestUtil.logMsg(
                "Exception is expected InvalidDestinationRuntimeException");
          } else {
            TestUtil.logErr(
                "Exception is incorrect expected InvalidDestinationRuntimeException, received "
                    + exception.getCause());
            pass = false;
          }
        }
      } catch (InvalidDestinationRuntimeException e) {
        TestUtil.logMsg("Got InvalidDestinationRuntimeException as expected.");
      } catch (Exception e) {
        TestUtil.logErr(
            "Expected InvalidDestinationRuntimeException, received " + e);
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      throw new Exception("invalidDestinationRuntimeExceptionTests", e);
    }

    if (!pass) {
      throw new Exception("invalidDestinationRuntimeExceptionTests failed");
    }
  }

  /*
   * @testName: messageFormatRuntimeExceptionTests
   *
   * @assertion_ids: JMS:JAVADOC:1236;
   *
   * @test_Strategy: Test MessageFormatRuntimeException conditions from API
   * methods with CompletionListener.
   *
   * Tests the following exception conditions:
   *
   * MessageFormatRuntimeException
   *
   */
  public void messageFormatRuntimeExceptionTests() throws Exception {
    boolean pass = true;
    try {
      // Create JMSConsumer from JMSContext
      TestUtil.logMsg("Creating JMSConsumer");
      consumer = context.createConsumer(destination);

      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      // Create CompetionListener
      MyCompletionListener listener = new MyCompletionListener();

      TestUtil.logMsg(
          "Testing JMSProducer.send(Destination, Message) for MessageFormatRuntimeException");
      try {
        TestUtil.logMsg("Calling JMSProducer.setAsync(CompletionListener)");
        producer.setAsync(listener);
        TestUtil.logMsg(
            "Calling JMSProducer.send(Destination, Message) -> expect MessageFormatRuntimeException");
        producer.send(destination, (Message) null);
        TestUtil.logMsg("Poll listener until we receive exception");
        Exception exception = null;
        for (int i = 0; i < 30; i++) {
          if (listener.isComplete()) {
            break;
          } else {
            TestUtil.sleepSec(2);
          }
        }

        if (exception == null) {
          pass = false;
          TestUtil.logErr("Didn't throw and exception");
        } else {
          TestUtil.logMsg("Check the value in Exception");
          if (exception instanceof MessageFormatRuntimeException) {
            TestUtil
                .logMsg("Exception is expected MessageFormatRuntimeException");
          } else {
            TestUtil.logErr(
                "Exception is incorrect expected MessageFormatRuntimeException, received "
                    + exception.getCause());
            pass = false;
          }
        }
      } catch (MessageFormatRuntimeException e) {
        TestUtil.logMsg("Got MessageFormatRuntimeException as expected.");
      } catch (Exception e) {
        TestUtil
            .logErr("Expected MessageFormatRuntimeException, received " + e);
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      throw new Exception("messageFormatRuntimeExceptionTests", e);
    }

    if (!pass) {
      throw new Exception("messageFormatRuntimeExceptionTests failed");
    }
  }

  /*
   * @testName: jMSRuntimeExceptionTests
   *
   * @assertion_ids: JMS:JAVADOC:1235;
   *
   * @test_Strategy: Test JMSRuntimeException conditions from API methods with
   * CompletionListener.
   *
   * Tests the following exception conditions:
   *
   * Set delivery mode to -1 on JMSProducer and then try and send async message
   * to CompletionListener. The CompletionListener MUST throw
   * JMSRuntimeException.
   *
   * Set priority to -1 on JMSProducer and then try and send async message to
   * CompletionListener. The CompletionListener MUST throw JMSRuntimeException.
   */
  public void jMSRuntimeExceptionTests() throws Exception {
    boolean pass = true;
    String message = "Where are you!";
    try {
      // Create JMSConsumer from JMSContext
      TestUtil.logMsg("Creating JMSConsumer");
      consumer = context.createConsumer(destination);

      // Create JMSProducer from JMSContext
      TestUtil.logMsg("Creating JMSProducer");
      producer = context.createProducer();

      // Create CompletionListener for Message to be sent
      MyCompletionListener listener = new MyCompletionListener();

      // send and receive TextMessage
      TestUtil.logMsg("Creating TextMessage");
      TextMessage expTextMessage = context.createTextMessage(message);
      TestUtil.logMsg("Set some values in TextMessage");
      expTextMessage.setStringProperty("COM_SUN_JMS_TESTNAME",
          "jMSRuntimeExceptionTests");
      try {
        TestUtil.logMsg("Set completion listener");
        TestUtil.logMsg("Calling JMSProducer.setAsync(CompletionListener)");
        producer.setAsync(listener);
        TestUtil.logMsg("Try and set an invalid delivery mode of -1 on send");
        producer.setDeliveryMode(-1);
        TestUtil.logMsg(
            "Calling JMSProducer.send(Destination, Message) -> expect JMSRuntimeException");
        producer.send(destination, expTextMessage);
        TestUtil.logMsg("Poll listener until we receive exception");
        Exception exception = null;
        for (int i = 0; i < 30; i++) {
          if (listener.isComplete()) {
            break;
          } else {
            TestUtil.sleepSec(2);
          }
        }

        if (exception == null) {
          pass = false;
          TestUtil.logErr("Didn't throw and exception");
        } else {
          TestUtil.logMsg("Check the value in Exception");
          if (exception instanceof JMSRuntimeException) {
            TestUtil.logMsg("Exception is expected JMSRuntimeException");
          } else {
            TestUtil.logErr(
                "Exception is incorrect expected JMSRuntimeException, received "
                    + exception.getCause());
            pass = false;
          }
        }
      } catch (JMSRuntimeException e) {
        TestUtil.logMsg("Caught expected JMSRuntimeException");
      } catch (Exception e) {
        TestUtil.logErr("Expected JMSRuntimeException, received " + e);
        pass = false;
      }
      try {
        TestUtil.logMsg("Set completion listener");
        TestUtil.logMsg("Calling JMSProducer.setAsync(CompletionListener)");
        producer.setAsync(listener);
        TestUtil.logMsg("Try and set an invalid priority of -1 on send");
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.setPriority(-1);
        TestUtil.logMsg(
            "Calling JMSProducer.send(Destination, Message) -> expect JMSRuntimeException");
        producer.send(destination, expTextMessage);
        TestUtil.logMsg("Poll listener until we receive exception");
        Exception exception = null;
        for (int i = 0; i < 30; i++) {
          if (listener.isComplete()) {
            break;
          } else {
            TestUtil.sleepSec(2);
          }
        }

        if (exception == null) {
          pass = false;
          TestUtil.logErr("Didn't throw and exception");
        } else {
          TestUtil.logMsg("Check the value in Exception");
          if (exception instanceof JMSRuntimeException) {
            TestUtil.logMsg("Exception is expected JMSRuntimeException");
          } else {
            TestUtil.logErr(
                "Exception is incorrect expected JMSRuntimeException, received "
                    + exception.getCause());
            pass = false;
          }
        }
      } catch (JMSRuntimeException e) {
        TestUtil.logMsg("Caught expected JMSRuntimeException");
      } catch (Exception e) {
        TestUtil.logErr("Expected JMSRuntimeException, received " + e);
        pass = false;
      }
    } catch (Exception e) {
      TestUtil.logErr("Caught unexpected exception: " + e);
      throw new Exception("jMSRuntimeExceptionTests", e);
    }

    if (!pass) {
      throw new Exception("jMSRuntimeExceptionTests failed");
    }
  }
}
