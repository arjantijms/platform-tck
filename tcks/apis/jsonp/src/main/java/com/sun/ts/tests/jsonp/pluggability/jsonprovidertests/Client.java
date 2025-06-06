/*
 * Copyright (c) 2024 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.jsonp.pluggability.jsonprovidertests;

import com.sun.ts.lib.harness.Status;
import com.sun.ts.tests.common.base.ServiceEETest;
import com.sun.ts.tests.jsonp.common.JSONP_Util;
import com.sun.ts.tests.jsonp.provider.MyJsonGenerator;
import com.sun.ts.tests.jsonp.provider.MyJsonProvider;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonException;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonReaderFactory;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.spi.JsonProvider;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonGeneratorFactory;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

public class Client extends ServiceEETest {
    static final System.Logger logger = System.getLogger(Client.class.getName());
    private static final String MY_JSONPROVIDER_CLASS = "com.sun.ts.tests.jsonp.provider.MyJsonProvider";

    public static void main(String[] args) {
        Client theTests = new Client();
        Status s = theTests.run(args, System.out, System.err);
        s.exit();
    }

    /* Test setup */

    /*
     * @class.setup_props:
     * This is needed by the vehicle base classes
     */
    public void setup(String[] args, Properties p) throws Exception {
        // createProviderJar();
        logger.log(System.Logger.Level.INFO, "setup ok");
    }
    public void cleanup() throws Exception {
        // removeProviderJarFromCP();
        MyJsonProvider.clearCalls();
        MyJsonGenerator.clearCalls();
        logger.log(System.Logger.Level.INFO, "cleanup ok");

    }

    /* Tests */

    /*
     * @testName: jsonProviderTest1
     *
     * @assertion_ids: JSONP:JAVADOC:152;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * static JsonProvider provider()
     */
    public void jsonProviderTest1() throws Exception {
        boolean pass = true;
        try {
            // Load my provider
            JsonProvider provider = JsonProvider.provider();
            String providerClass = provider.getClass().getName();
            logger.log(System.Logger.Level.INFO, "provider class=" + providerClass);
            if (providerClass.equals(MY_JSONPROVIDER_CLASS))
                logger.log(System.Logger.Level.INFO, "Current provider is my provider - expected.");
            else {
                logger.log(System.Logger.Level.ERROR, "Current provider is not my provider - unexpected.");
                pass = false;
                ServiceLoader<JsonProvider> loader = ServiceLoader.load(JsonProvider.class);
                Iterator<JsonProvider> it = loader.iterator();
                List<JsonProvider> providers = new ArrayList<>();
                while(it.hasNext()) {
                    providers.add(it.next());
                }
                logger.log(System.Logger.Level.INFO, "Providers: "+providers);
            }
        } catch (Exception e) {
            throw new Exception("jsonProviderTest1 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest1 Failed");
    }

    /*
     * @testName: jsonProviderTest2
     *
     * @assertion_ids: JSONP:JAVADOC:144;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonGenerator createGenerator(Writer)
     */
    public void jsonProviderTest2() throws Exception {
        boolean pass = true;
        String expString = "public JsonGenerator createGenerator(Writer)";
        String expString2 = "public JsonGenerator writeStartArray()";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonGenerator generator = Json.createGenerator(new StringWriter());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
            generator.writeStartArray();
            String actString2 = MyJsonGenerator.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI generator method was called: " + expString2);
            pass = JSONP_Util.assertEquals(expString2, actString2);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest2 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest2 Failed");
    }

    /*
     * @testName: jsonProviderTest3
     *
     * @assertion_ids: JSONP:JAVADOC:192;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonGenerator createGenerator(OutputStream)
     */
    public void jsonProviderTest3() throws Exception {
        boolean pass = true;
        String expString = "public JsonGenerator createGenerator(OutputStream)";
        String expString2 = "public JsonGenerator writeStartObject()";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonGenerator generator = Json
                    .createGenerator(new ByteArrayOutputStream());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
            generator.writeStartObject();
            String actString2 = MyJsonGenerator.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI generator method was called: " + expString2);
            pass = JSONP_Util.assertEquals(expString2, actString2);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest3 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest3 Failed");
    }

    /*
     * @testName: jsonProviderTest4
     *
     * @assertion_ids: JSONP:JAVADOC:146;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonParser createParser(Reader)
     */
    public void jsonProviderTest4() throws Exception {
        boolean pass = true;
        String expString = "public JsonParser createParser(Reader)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonParser parser = Json.createParser(new StringReader("{}"));
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest4 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest4 Failed");
    }

    /*
     * @testName: jsonProviderTest5
     *
     * @assertion_ids: JSONP:JAVADOC:196;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonParser createParser(InputStream)
     */
    public void jsonProviderTest5() throws Exception {
        boolean pass = true;
        String expString = "public JsonParser createParser(InputStream)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonParser parser = Json
                    .createParser(JSONP_Util.getInputStreamFromString("{}"));
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest5 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest5 Failed");
    }

    /*
     * @testName: jsonProviderTest6
     *
     * @assertion_ids: JSONP:JAVADOC:465;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonParserFactory createParserFactory(Map<String, ?>)
     */
    public void jsonProviderTest6() throws Exception {
        boolean pass = true;
        String expString = "public JsonParserFactory createParserFactory(Map<String, ?>)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonParserFactory parserFactory = Json
                    .createParserFactory(JSONP_Util.getEmptyConfig());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest6 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest6 Failed");
    }

    /*
     * @testName: jsonProviderTest7
     *
     * @assertion_ids: JSONP:JAVADOC:426;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonParserFactory createParserFactory(Map<String, ?>)
     */
    public void jsonProviderTest7() throws Exception {
        boolean pass = true;
        String expString = "public JsonParserFactory createParserFactory(Map<String, ?>)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonParserFactory parserFactory = Json
                    .createParserFactory(new HashMap<String, Object>());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest7 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest7 Failed");
    }

    /*
     * @testName: jsonProviderTest8
     *
     * @assertion_ids: JSONP:JAVADOC:425;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonGeneratorFactory createGeneratorFactory(Map<String, ?>)
     */
    public void jsonProviderTest8() throws Exception {
        boolean pass = true;
        String expString = "public JsonGeneratorFactory createGeneratorFactory(Map<String, ?>)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonGeneratorFactory generatorFactory = Json
                    .createGeneratorFactory(new HashMap<String, Object>());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest8 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest8 Failed");
    }

    /*
     * @testName: jsonProviderTest9
     *
     * @assertion_ids: JSONP:JAVADOC:472;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonWriterFactory createWriterFactory(Map<String, ?>)
     */
    public void jsonProviderTest9() throws Exception {
        boolean pass = true;
        String expString = "public JsonWriterFactory createWriterFactory(Map<String, ?>)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonWriterFactory factory = Json
                    .createWriterFactory(JSONP_Util.getEmptyConfig());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest9 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest9 Failed");
    }

    /*
     * @testName: jsonProviderTest10
     *
     * @assertion_ids: JSONP:JAVADOC:223;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonParser createParser(InputStream) Tests the case where a JsonException
     * can be thrown. An InputStream of null will cause MyJsonProvider to throw
     * JsonException.
     */
    public void jsonProviderTest10() throws Exception {
        boolean pass = true;
        String expString = "public JsonParser createParser(InputStream)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            InputStream in = null;
            JsonParser parser = Json.createParser(in);
            pass = false;
        } catch (JsonException e) {
            logger.log(System.Logger.Level.INFO, "Caught expected JsonException: " + e);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest10 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest10 Failed");
    }

    /*
     * @testName: jsonProviderTest11
     *
     * @assertion_ids: JSONP:JAVADOC:464;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonArrayBuilder createArrayBuilder()
     */
    public void jsonProviderTest11() throws Exception {
        boolean pass = true;
        String expString = "public JsonArrayBuilder createArrayBuilder()";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest11 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest11 Failed");
    }

    /*
     * @testName: jsonProviderTest12
     *
     * @assertion_ids: JSONP:JAVADOC:466;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonObjectBuilder createObjectBuilder()
     */
    public void jsonProviderTest12() throws Exception {
        boolean pass = true;
        String expString = "public JsonObjectBuilder createObjectBuilder()";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest12 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest12 Failed");
    }

    /*
     * @testName: jsonProviderTest13
     *
     * @assertion_ids: JSONP:JAVADOC:465;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonBuilderFactory createBuilderFactory(Map<String, ?>)
     */
    public void jsonProviderTest13() throws Exception {
        boolean pass = true;
        String expString = "public JsonBuilderFactory createBuilderFactory(Map<String, ?>)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonBuilderFactory objectBuilder = Json
                    .createBuilderFactory(JSONP_Util.getEmptyConfig());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest13 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest13 Failed");
    }

    /*
     * @testName: jsonProviderTest14
     *
     * @assertion_ids: JSONP:JAVADOC:467;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonReader createReader(Reader)
     */
    public void jsonProviderTest14() throws Exception {
        boolean pass = true;
        String expString = "public JsonReader createReader(Reader)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonReader reader = Json.createReader(new StringReader("{}"));
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest14 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest14 Failed");
    }

    /*
     * @testName: jsonProviderTest15
     *
     * @assertion_ids: JSONP:JAVADOC:468;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonReader createReader(InputStream)
     */
    public void jsonProviderTest15() throws Exception {
        boolean pass = true;
        String expString = "public JsonReader createReader(InputStream)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonReader reader = Json
                    .createReader(JSONP_Util.getInputStreamFromString("{}"));
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest15 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest15 Failed");
    }

    /*
     * @testName: jsonProviderTest16
     *
     * @assertion_ids: JSONP:JAVADOC:470;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonWriter createWriter(Writer)
     */
    public void jsonProviderTest16() throws Exception {
        boolean pass = true;
        String expString = "public JsonWriter createWriter(Writer)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonWriter writer = Json.createWriter(new StringWriter());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest16 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest16 Failed");
    }

    /*
     * @testName: jsonProviderTest17
     *
     * @assertion_ids: JSONP:JAVADOC:471;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonWriter createWriter(OutputStream)
     */
    public void jsonProviderTest17() throws Exception {
        boolean pass = true;
        String expString = "public JsonWriter createWriter(OutputStream)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonWriter writer = Json.createWriter(new ByteArrayOutputStream());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest17 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest17 Failed");
    }

    /*
     * @testName: jsonProviderTest18
     *
     * @assertion_ids: JSONP:JAVADOC:469;
     *
     * @test_Strategy: Test call of SPI provider method with signature: o public
     * JsonReaderFactory createReaderFactory(Map<String, ?>)
     */
    public void jsonProviderTest18() throws Exception {
        boolean pass = true;
        String expString = "public JsonReaderFactory createReaderFactory(Map<String, ?>)";
        try {
            logger.log(System.Logger.Level.INFO, "Calling SPI provider method: " + expString);
            JsonReaderFactory factory = Json
                    .createReaderFactory(JSONP_Util.getEmptyConfig());
            String actString = MyJsonProvider.getCalls();
            logger.log(System.Logger.Level.INFO, "Verify SPI provider method was called: " + expString);
            pass = JSONP_Util.assertEquals(expString, actString);
        } catch (Exception e) {
            throw new Exception("jsonProviderTest18 Failed: ", e);
        }
        if (!pass)
            throw new Exception("jsonProviderTest18 Failed");
    }

}
