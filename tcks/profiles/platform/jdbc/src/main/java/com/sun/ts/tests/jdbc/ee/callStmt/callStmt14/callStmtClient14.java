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
 * @(#)callStmtClient14.java	1.19 03/05/16
 */

package com.sun.ts.tests.jdbc.ee.callStmt.callStmt14;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;

import com.sun.ts.tests.common.base.ServiceEETest;
import com.sun.ts.lib.harness.Status;
import com.sun.ts.lib.util.TSNamingContextInterface;
import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.jdbc.ee.common.DataSourceConnection;
import com.sun.ts.tests.jdbc.ee.common.DriverManagerConnection;
import com.sun.ts.tests.jdbc.ee.common.JDBCTestMsg;
import com.sun.ts.tests.jdbc.ee.common.rsSchema;

// Merant DataSource class
//import com.merant.sequelink.jdbcx.datasource.*;

/**
 * The callStmtClient14 class tests methods of CallableStatement interface (to
 * check the Support for IN, OUT and INOUT parameters of Stored Procedure) using
 * Sun's J2EE Reference Implementation.
 * 
 * @author
 * @version 1.7, 06/16/99
 */
@ExtendWith(ArquillianExtension.class)
@Tag("jdbc")
@Tag("platform")

public class callStmtClient14 extends ServiceEETest implements Serializable {
	private static final String testName = "jdbc.ee.callStmt.callStmt14";

	// Naming specific member variables
	private TSNamingContextInterface jc = null;

	// Harness requirements

	private transient Connection conn = null;

	private ResultSet rs = null;

	private rsSchema rsSch = null;

	private String drManager = null;

	private Properties sqlp = null;

	private transient DatabaseMetaData dbmd = null;

	private Statement stmt = null;

	private CallableStatement cstmt = null;

	private Properties props = null;

	private JDBCTestMsg msg = null;

	/* Run test in standalone mode */
	public static void main(String[] args) {
		callStmtClient14 theTests = new callStmtClient14();
		Status s = theTests.run(args, System.out, System.err);
		s.exit();
	}

	/* Test setup: */
	/*
	 * @class.setup_props: Driver, the Driver name; db1, the database name with url;
	 * user1, the database user name; password1, the database password; db2, the
	 * database name with url; user2, the database user name; password2, the
	 * database password; DriverManager, flag for DriverManager; ptable, the primary
	 * table; ftable, the foreign table; cofSize, the initial size of the ptable;
	 * cofTypeSize, the initial size of the ftable; binarySize, size of binary data
	 * type; varbinarySize, size of varbinary data type; longvarbinarySize, size of
	 * longvarbinary data type;
	 * 
	 * @class.testArgs: -ap tssql.stmt
	 */
	public void setup(String[] args, Properties p) throws Exception {
		try {
			try {
				drManager = p.getProperty("DriverManager", "");
				if (drManager.length() == 0)
					throw new Exception("Invalid DriverManager Name");
				sqlp = p;

				if (drManager.equals("yes")) {
					logTrace("Using DriverManager");
					DriverManagerConnection dmCon = new DriverManagerConnection();
					conn = dmCon.getConnection(p);
				} else {
					logTrace("Using DataSource");
					DataSourceConnection dsCon = new DataSourceConnection();
					conn = dsCon.getConnection(p);
				}
				dbmd = conn.getMetaData();
				stmt = conn.createStatement();
				rsSch = new rsSchema();
				msg = new JDBCTestMsg();
			} catch (SQLException ex) {
				logErr("SQL Exception : " + ex.getMessage(), ex);
			}
		} catch (Exception e) {
			logErr("Setup Failed!");
			TestUtil.printStackTrace(e);
		}
	}

	/*
	 * @testName: testSetObject121
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Min_Val of the Float_Tab with the maximum value of the
	 * Integer_Tab. Execute a query to retrieve the Min_Val from Float_Tab. Compare
	 * the returned value with the maximum value extracted from tssql.stmt file.
	 * Both of them should be equal.
	 */
	public void testSetObject121() throws Exception {
		Double maxFloatVal = null;
		Integer maxIntegerVal = null;
		Double rFloatVal = null;
		String smaxStringVal = null;
		try {

			rsSch.createTab("Float_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Float_In_Min(?)}");

			msg.setMsg("extract the Maximum Value of Float to be Updated");
			smaxStringVal = rsSch.extractVal("Integer_Tab", 1, sqlp, conn);
			maxIntegerVal = new Integer(smaxStringVal);
			maxFloatVal = new Double(smaxStringVal);
			cstmt.setObject(1, maxIntegerVal, java.sql.Types.FLOAT);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			String Min_Val_Query = sqlp.getProperty("Float_Query_Min", "");
			msg.setMsg(Min_Val_Query);
			rs = stmt.executeQuery(Min_Val_Query);
			rs.next();

			rFloatVal = (Double) rs.getObject(1);

			msg.addOutputMsg("" + maxFloatVal, "" + rFloatVal);
			if (rFloatVal.equals(maxFloatVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Float_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject122
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Float_Tab with the minimum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Float_Tab. Compare
	 * the returned value with the minimum value extracted from tssql.stmt file.
	 * Both of them should be equal.
	 */
	public void testSetObject122() throws Exception {
		Double minFloatVal = null;
		Integer minIntegerVal = null;
		Double rFloatVal = null;
		String sminStringVal = null;
		try {

			rsSch.createTab("Float_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Float_In_Null(?)}");

			msg.setMsg("extract the Minimum Value of Float to be Updated");
			sminStringVal = rsSch.extractVal("Integer_Tab", 2, sqlp, conn);
			minIntegerVal = new Integer(sminStringVal);
			minFloatVal = new Double(sminStringVal);
			cstmt.setObject(1, minIntegerVal, java.sql.Types.FLOAT);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			String Null_Val_Query = sqlp.getProperty("Float_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rFloatVal = (Double) rs.getObject(1);
			msg.addOutputMsg("" + minFloatVal, "" + rFloatVal);

			if (rFloatVal.equals(minFloatVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();

		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Float_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject123
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Min_Val of the Double_Tab with the maximum value of the
	 * Integer_Tab. Execute a query to retrieve the Min_Val from Double_Tab. Compare
	 * the returned value with the maximum value extracted from tssql.stmt file.
	 * Both of them should be equal.
	 */
	public void testSetObject123() throws Exception {
		Double maxDoubleVal = null;
		Integer maxIntegerVal = null;
		Double rDoubleVal = null;
		String smaxStringVal = null;
		try {

			rsSch.createTab("Double_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Double_In_Min(?)}");

			msg.setMsg("extract the Maximum Value of Double to be Updated");
			smaxStringVal = rsSch.extractVal("Integer_Tab", 1, sqlp, conn);
			maxIntegerVal = new Integer(smaxStringVal);
			maxDoubleVal = new Double(smaxStringVal);
			cstmt.setObject(1, maxIntegerVal, java.sql.Types.DOUBLE);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			String Min_Val_Query = sqlp.getProperty("Double_Query_Min", "");
			msg.setMsg(Min_Val_Query);
			rs = stmt.executeQuery(Min_Val_Query);
			rs.next();

			rDoubleVal = (Double) rs.getObject(1);
			msg.addOutputMsg("" + maxDoubleVal, "" + rDoubleVal);

			if (rDoubleVal.equals(maxDoubleVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Double_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject124
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Double_Tab with the minimum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Double_Tab.
	 * Compare the returned value with the minimum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject124() throws Exception {
		Double minDoubleVal = null;
		Integer minIntegerVal = null;
		Double rDoubleVal = null;
		String sminStringVal = null;
		try {

			rsSch.createTab("Double_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Double_In_Null(?)}");

			msg.setMsg("extract the Minimum Value of Double to be Updated");
			sminStringVal = rsSch.extractVal("Integer_Tab", 2, sqlp, conn);
			minIntegerVal = new Integer(sminStringVal);
			minDoubleVal = new Double(sminStringVal);
			cstmt.setObject(1, minIntegerVal, java.sql.Types.DOUBLE);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			String Null_Val_Query = sqlp.getProperty("Double_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rDoubleVal = (Double) rs.getObject(1);
			msg.addOutputMsg("" + minDoubleVal, "" + rDoubleVal);

			if (rDoubleVal.equals(minDoubleVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Double_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject125
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:692;
	 * JDBC:JAVADOC:693; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Decimal_Tab with the maximum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Decimal_Tab.
	 * Compare the returned value with the maximum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject125() throws Exception {
		BigDecimal maxDecimalVal = null;
		BigDecimal rDecimalVal = null;
		Integer maxIntegerVal = null;
		String smaxStringVal = null;
		try {

			rsSch.createTab("Decimal_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Decimal_In_Null(?)}");

			msg.setMsg("extract the Maximum Value of Decimal to be Updated");
			smaxStringVal = rsSch.extractVal("Integer_Tab", 1, sqlp, conn);
			maxIntegerVal = new Integer(smaxStringVal);
			maxDecimalVal = new BigDecimal(smaxStringVal);
			cstmt.setObject(1, maxIntegerVal, java.sql.Types.DECIMAL, 15);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			String Null_Val_Query = sqlp.getProperty("Decimal_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rDecimalVal = (BigDecimal) rs.getObject(1);
			msg.addOutputMsg("" + maxDecimalVal, "" + rDecimalVal);

			if ((rDecimalVal.compareTo(maxDecimalVal) == 0)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Decimal_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject126
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:692;
	 * JDBC:JAVADOC:693; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Decimal_Tab with the minimum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Decimal_Tab.
	 * Compare the returned value with the minimum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject126() throws Exception {
		BigDecimal minDecimalVal = null;
		BigDecimal rDecimalVal = null;
		Integer minIntegerVal = null;
		String sminStringVal = null;
		try {

			rsSch.createTab("Decimal_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Decimal_In_Max(?)}");

			msg.setMsg("extract the Minimum Value of Decimal to be Updated");
			sminStringVal = rsSch.extractVal("Integer_Tab", 2, sqlp, conn);
			minDecimalVal = new BigDecimal(sminStringVal);
			minIntegerVal = new Integer(sminStringVal);
			cstmt.setObject(1, minIntegerVal, java.sql.Types.DECIMAL, 15);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			String Null_Val_Query = sqlp.getProperty("Decimal_Query_Max", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rDecimalVal = (BigDecimal) rs.getObject(1);
			msg.addOutputMsg("" + minDecimalVal, "" + rDecimalVal);

			if ((rDecimalVal.compareTo(minDecimalVal) == 0)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Decimal_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject127
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:692;
	 * JDBC:JAVADOC:693; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Min_Val of the Numeric_Tab with the maximum value of the
	 * Integer_Tab. Execute a query to retrieve the Min_Val from Numeric_Tab.
	 * Compare the returned value with the maximum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject127() throws Exception {
		BigDecimal maxNumericVal = null;
		BigDecimal rNumericVal = null;
		Integer maxIntegerVal = null;
		String smaxStringVal = null;
		try {

			rsSch.createTab("Numeric_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Numeric_In_Null(?)}");

			msg.setMsg("extract the Maximum Value of Numeric to be Updated");
			smaxStringVal = rsSch.extractVal("Integer_Tab", 1, sqlp, conn);
			maxNumericVal = new BigDecimal(smaxStringVal);
			maxIntegerVal = new Integer(smaxStringVal);
			cstmt.setObject(1, maxIntegerVal, java.sql.Types.NUMERIC, 15);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			// to get the query string
			String Min_Val_Query = sqlp.getProperty("Numeric_Query_Null", "");
			msg.setMsg(Min_Val_Query);
			rs = stmt.executeQuery(Min_Val_Query);
			rs.next();

			rNumericVal = (BigDecimal) rs.getObject(1);
			msg.addOutputMsg("" + maxNumericVal, "" + rNumericVal);

			if ((rNumericVal.compareTo(maxNumericVal) == 0)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Numeric_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject128
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:692;
	 * JDBC:JAVADOC:693; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Numeric_Tab with the minimum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Numeric_Tab.
	 * Compare the returned value with the minimum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject128() throws Exception {

		BigDecimal minNumericVal = null;
		BigDecimal rNumericVal = null;
		Integer minIntegerVal = null;
		String sminStringVal = null;
		try {

			rsSch.createTab("Numeric_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Numeric_In_Max(?)}");

			msg.setMsg("extract the Minimum Value of Numeric to be Updated");
			sminStringVal = rsSch.extractVal("Integer_Tab", 2, sqlp, conn);
			minNumericVal = new BigDecimal(sminStringVal);
			minIntegerVal = new Integer(sminStringVal);
			cstmt.setObject(1, minIntegerVal, java.sql.Types.NUMERIC, 15);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Null_Val_Query = sqlp.getProperty("Numeric_Query_Max", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rNumericVal = (BigDecimal) rs.getObject(1);
			msg.addOutputMsg("" + minNumericVal, "" + rNumericVal);

			if ((rNumericVal.compareTo(minNumericVal) == 0)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Numeric_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject131
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Char_Tab with the maximum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Char_Tab. Compare
	 * the returned value with the maximum value extracted from tssql.stmt file.
	 * Both of them should be equal.
	 */
	public void testSetObject131() throws Exception {
		String maxStringVal = null;
		String rStringVal = null;
		Integer maxIntegerVal = null;
		try {
			rsSch.createTab("Char_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Char_In_Null(?)}");

			msg.setMsg("extract the Maximum Value of Integer, to be Updated for NULL value of Char Table");
			maxStringVal = rsSch.extractVal("Integer_Tab", 1, sqlp, conn);
			maxStringVal = maxStringVal.trim(); // substring(0,maxStringVal.length());
			maxIntegerVal = new Integer(maxStringVal);
			cstmt.setObject(1, maxIntegerVal, java.sql.Types.CHAR);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Null_Val_Query = sqlp.getProperty("Char_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rStringVal = rs.getObject(1).toString();
			rStringVal = rStringVal.trim();
			msg.addOutputMsg(maxStringVal, rStringVal);
			if (rStringVal.equals(maxStringVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Char_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject132
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Char_Tab with the minimum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Char_Tab. Compare
	 * the returned value with the minimum value extracted from tssql.stmt file.
	 * Both of them should be equal.
	 */
	public void testSetObject132() throws Exception {
		String maxStringVal = null;
		String rStringVal = null;
		Integer maxIntegerVal = null;
		try {
			rsSch.createTab("Char_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Char_In_Null(?)}");

			msg.setMsg("extract the Minimum Value of Integer, to be Updated for NULL value of Char Table");
			maxStringVal = rsSch.extractVal("Integer_Tab", 2, sqlp, conn);
			maxStringVal = maxStringVal.trim(); // substring(0,maxStringVal.length());
			maxIntegerVal = new Integer(maxStringVal);

			cstmt.setObject(1, maxIntegerVal, java.sql.Types.CHAR);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Null_Val_Query = sqlp.getProperty("Char_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rStringVal = rs.getObject(1).toString();
			rStringVal = rStringVal.trim();

			msg.addOutputMsg(maxStringVal, rStringVal);
			if (rStringVal.equals(maxStringVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Char_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject133
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Varchar_Tab with the maximum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Varchar_Tab.
	 * Compare the returned value with the maximum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject133() throws Exception {
		Integer maxIntegerVal = null;
		String maxStringVal = null;
		String rStringVal = null;
		try {
			rsSch.createTab("Varchar_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Varchar_In_Null(?)}");

			msg.setMsg("extract the Maximum Value of Integer, to be Updated for NULL value of Varchar Table");
			maxStringVal = rsSch.extractVal("Integer_Tab", 1, sqlp, conn);
			maxStringVal = maxStringVal.trim(); // substring(0,maxStringVal.length());
			maxIntegerVal = new Integer(maxStringVal);
			cstmt.setObject(1, maxIntegerVal, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			String Null_Val_Query = sqlp.getProperty("Varchar_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rStringVal = rs.getObject(1).toString();
			rStringVal = rStringVal.trim();
			msg.addOutputMsg(maxStringVal, rStringVal);
			if (rStringVal.equals(maxStringVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Varchar_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject134
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Varchar_Tab with the minimum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Varchar_Tab.
	 * Compare the returned value with the minimum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject134() throws Exception {
		Integer maxIntegerVal = null;
		String maxStringVal = null;
		String rStringVal = null;
		try {
			rsSch.createTab("Varchar_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Varchar_In_Null(?)}");

			msg.setMsg("extract the Minimum Value of Integer, to be Updated for NULL value of Varchar Table");
			maxStringVal = rsSch.extractVal("Integer_Tab", 2, sqlp, conn);
			maxStringVal = maxStringVal.trim(); // substring(0,maxStringVal.length());
			maxIntegerVal = new Integer(maxStringVal);

			cstmt.setObject(1, maxIntegerVal, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");
			String Null_Val_Query = sqlp.getProperty("Varchar_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rStringVal = rs.getObject(1).toString();
			rStringVal = rStringVal.trim();
			msg.addOutputMsg(maxStringVal, rStringVal);
			if (rStringVal.equals(maxStringVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Varchar_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject135
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Longvarchar_Tab with the maximum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Longvarchar_Tab.
	 * Compare the returned value with the maximum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject135() throws Exception {
		String maxStringVal = null;
		Integer maxIntegerVal = null;
		String rStringVal = null;
		try {
			rsSch.createTab("Longvarcharnull_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Lvarchar_In_Null(?)}");

			msg.setMsg("extract the Maximum Value of Integer, to be Updated for NULL value of Longvarchar Table");
			maxStringVal = rsSch.extractVal("Integer_Tab", 1, sqlp, conn);
			maxStringVal = maxStringVal.trim();
			maxIntegerVal = new Integer(maxStringVal);

			cstmt.setObject(1, maxIntegerVal, java.sql.Types.LONGVARCHAR);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Null_Val_Query = sqlp.getProperty("Longvarchar_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rStringVal = rs.getObject(1).toString();
			rStringVal = rStringVal.trim();

			msg.addOutputMsg(maxStringVal, rStringVal);
			if (rStringVal.equals(maxStringVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Longvarcharnull_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject136
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: This test case is meant for checking the support for IN
	 * parameter in CallableStatement Interface. Get a CallableStatement object from
	 * the connection to the database. Using the IN parameter of that object,update
	 * the column Null_Val of the Longvarchar_Tab with the minimum value of the
	 * Integer_Tab. Execute a query to retrieve the Null_Val from Longvarchar_Tab.
	 * Compare the returned value with the minimum value extracted from tssql.stmt
	 * file. Both of them should be equal.
	 */
	public void testSetObject136() throws Exception {
		String maxStringVal = null;
		Integer maxIntegerVal = null;
		String rStringVal = null;
		try {
			rsSch.createTab("Longvarcharnull_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Lvarchar_In_Null(?)}");

			msg.setMsg("extract the Minimum Value of Integer, to be Updated for NULL value of Longvarchar Table");
			maxStringVal = rsSch.extractVal("Integer_Tab", 2, sqlp, conn);
			maxStringVal = maxStringVal.trim();
			maxIntegerVal = new Integer(maxStringVal);
			cstmt.setObject(1, maxIntegerVal, java.sql.Types.LONGVARCHAR);
			cstmt.executeUpdate();

			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Null_Val_Query = sqlp.getProperty("Longvarchar_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();

			rStringVal = rs.getObject(1).toString();
			rStringVal = rStringVal.trim();
			msg.addOutputMsg(maxStringVal, rStringVal);
			if (rStringVal.equals(maxStringVal)) {
				msg.setMsg("setObject Method sets the designated parameter with the Object");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter with the Object",
						"test setObject failed");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Longvarcharnull_Tab", conn);
			} catch (Exception e) {
				TestUtil.printStackTrace(e);

				msg.setMsg("Exception in finally block" + e);
			}
		}
	}

	/*
	 * @testName: testSetObject137
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: Get a CallableStatement object from the connection to the
	 * database. Using the setObject(int parameterIndex, Object x, int
	 * targetSqlType) method,update the column Min_Val of Tinyint_Tab with the
	 * maximum value of Tinyint_Tab. Call the getObject(int columnno) method to
	 * retrieve this value. Extract the maximum value from the tssql.stmt file.
	 * Compare this value with the value returned by the getObject(int columnno)
	 * method. Both the values should be equal.
	 */
	public void testSetObject137() throws Exception {
		Long maxLongVal;
		Integer rIntegerVal;
		String smaxStringVal = null;
		try {
			rsSch.createTab("Tinyint_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Tinyint_In_Min(?)}");

			msg.setMsg("extract the Minimum Value to be Updated");
			smaxStringVal = rsSch.extractVal("Tinyint_Tab", 1, sqlp, conn);
			Integer maxIntegerVal = new Integer(smaxStringVal);
			maxLongVal = new Long(smaxStringVal);

			cstmt.setObject(1, maxLongVal, java.sql.Types.TINYINT);

			msg.setMsg("execute the procedure");
			cstmt.executeUpdate();
			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Max_Val_Query = sqlp.getProperty("Tinyint_Query_Min", "");
			msg.setMsg(Max_Val_Query);
			rs = stmt.executeQuery(Max_Val_Query);
			rs.next();
			Object oIntegerVal = rs.getObject(1);
			rIntegerVal = new Integer(oIntegerVal.toString());
			msg.addOutputMsg("" + maxIntegerVal, "" + rIntegerVal);

			if (rIntegerVal.compareTo(maxIntegerVal) == 0) {
				msg.setMsg("setObject Method sets the designated parameter value ");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter value",
						"test setObject failed!");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Tinyint_Tab", conn);
			} catch (Exception e) {

			}
		}
	}

	/*
	 * @testName: testSetObject138
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: Get a CallableStatement object from the connection to the
	 * database. Using the setObject(int parameterIndex, Object x,int targetSqlType)
	 * method,update the column Null_Val of Tinyint_Tab with the minimum value of
	 * Tinyint_Tab. Call the getObject(int columnno) method to retrieve this value.
	 * Extract the minimum value from the tssql.stmt file. Compare this value with
	 * the value returned by the getObject(int columnno) method. Both the values
	 * should be equal.
	 */
	public void testSetObject138() throws Exception {
		Long minLongVal;
		Integer rIntegerVal;
		String sminStringVal = null;
		try {
			rsSch.createTab("Tinyint_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Tinyint_In_Null(?)}");

			msg.setMsg("extract the Maximum Value to be Updated");
			sminStringVal = rsSch.extractVal("Tinyint_Tab", 2, sqlp, conn);
			Integer minIntegerVal = new Integer(sminStringVal);
			minLongVal = new Long(sminStringVal);

			cstmt.setObject(1, minLongVal, java.sql.Types.TINYINT);

			msg.setMsg("execute the procedure");
			cstmt.executeUpdate();
			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Null_Val_Query = sqlp.getProperty("Tinyint_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();
			Object oIntegerVal = rs.getObject(1);
			rIntegerVal = new Integer(oIntegerVal.toString());
			msg.addOutputMsg("" + minIntegerVal, "" + rIntegerVal);

			if (rIntegerVal.compareTo(minIntegerVal) == 0) {
				msg.setMsg("setObject Method sets the designated parameter value ");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter value ",
						"test setObject failed!");
				;

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Tinyint_Tab", conn);
			} catch (Exception e) {

			}
		}
	}

	/*
	 * @testName: testSetObject139
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: Get a CallableStatement object from the connection to the
	 * database. Using the setObject(int parameterIndex, Object x, int
	 * targetSqlType) method,update the column Min_Val of Smallint_Tab with the
	 * maximum value of Smallint_Tab. Call the getObject(int columnno) method to
	 * retrieve this value. Extract the maximum value from the tssql.stmt file.
	 * Compare this value with the value returned by the getObject(int columnno)
	 * method. Both the values should be equal.
	 */
	public void testSetObject139() throws Exception {
		Long maxLongVal;
		Integer rIntegerVal;
		String smaxStringVal = null;
		try {
			rsSch.createTab("Smallint_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Smallint_In_Min(?)}");

			msg.setMsg("extract the Minimum Value to be Updated");
			smaxStringVal = rsSch.extractVal("Smallint_Tab", 1, sqlp, conn);
			Integer maxIntegerVal = new Integer(smaxStringVal);
			maxLongVal = new Long(smaxStringVal);
			cstmt.setObject(1, maxLongVal, java.sql.Types.SMALLINT);

			msg.setMsg("execute the procedure");
			cstmt.executeUpdate();
			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Max_Val_Query = sqlp.getProperty("Smallint_Query_Min", "");
			msg.setMsg(Max_Val_Query);
			rs = stmt.executeQuery(Max_Val_Query);
			rs.next();
			Object oIntegerVal = rs.getObject(1);
			rIntegerVal = new Integer(oIntegerVal.toString());
			msg.addOutputMsg("" + maxIntegerVal, "" + rIntegerVal);

			if (rIntegerVal.compareTo(maxIntegerVal) == 0) {
				msg.setMsg("setObject Method sets the designated parameter value ");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter value ",
						"test setObject failed!");
				;

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Smallint_Tab", conn);
			} catch (Exception e) {

			}
		}
	}

	/*
	 * @testName: testSetObject140
	 * 
	 * @assertion_ids: JDBC:SPEC:9; JDBC:SPEC:10; JDBC:JAVADOC:694;
	 * JDBC:JAVADOC:695; JavaEE:SPEC:186;
	 *
	 * @test_Strategy: Get a CallableStatement object from the connection to the
	 * database. Using the setObject(int parameterIndex, Object x,int targetSqlType)
	 * method,update the column Null_Val of Smallint_Tab with the minimum value of
	 * Smallint_Tab. Call the getObject(int columnno) method to retrieve this value.
	 * Extract the minimum value from the tssql.stmt file. Compare this value with
	 * the value returned by the getObject(int columnno) method. Both the values
	 * should be equal.
	 */
	public void testSetObject140() throws Exception {
		Long minLongVal;
		Integer rIntegerVal;
		String sminStringVal = null;
		try {
			rsSch.createTab("Smallint_Tab", sqlp, conn);
			msg.setMsg("get the CallableStatement object");
			cstmt = conn.prepareCall("{call Smallint_In_Null(?)}");

			msg.setMsg("extract the Maximum Value to be Updated");
			sminStringVal = rsSch.extractVal("Smallint_Tab", 2, sqlp, conn);
			Integer minIntegerVal = new Integer(sminStringVal);
			minLongVal = new Long(sminStringVal);

			cstmt.setObject(1, minLongVal, java.sql.Types.SMALLINT);
			msg.setMsg("execute the procedure");
			cstmt.executeUpdate();
			msg.setMsg("to query from the database to check the call of cstmt.executeUpdate");

			String Null_Val_Query = sqlp.getProperty("Smallint_Query_Null", "");
			msg.setMsg(Null_Val_Query);
			rs = stmt.executeQuery(Null_Val_Query);
			rs.next();
			Object oIntegerVal = rs.getObject(1);
			rIntegerVal = new Integer(oIntegerVal.toString());
			msg.addOutputMsg("" + minIntegerVal, "" + rIntegerVal);

			if (rIntegerVal.compareTo(minIntegerVal) == 0) {
				msg.setMsg("setObject Method sets the designated parameter value ");
			} else {
				msg.printTestError("setObject Method does not set the designated parameter value ",
						"test setObject failed!");

			}
			msg.printTestMsg();
			msg.printOutputMsg();
		} catch (SQLException sqle) {
			msg.printSQLError(sqle, "Call to setObject is Failed!");

		} catch (Exception e) {
			msg.printError(e, "Call to setObject is Failed!");

		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cstmt != null) {
					cstmt.close();
					cstmt = null;
				}
				rsSch.dropTab("Smallint_Tab", conn);
			} catch (Exception e) {

			}
		}
	}

	/* cleanup */
	public void cleanup() throws Exception {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			// Close the database
			rsSch.dbUnConnect(conn);
			logMsg("Cleanup ok;");
		} catch (Exception e) {
			logErr("An error occurred while closing the database connection", e);
		}
	}
}
