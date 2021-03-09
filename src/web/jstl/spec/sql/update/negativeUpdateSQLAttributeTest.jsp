<%--

    Copyright (c) 2003, 2020 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Public License v. 2.0, which is available at
    http://www.eclipse.org/legal/epl-2.0.

    This Source Code may also be made available under the following Secondary
    Licenses when the conditions for such availability set forth in the
    Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
    version 2 with the GNU Classpath Exception, which is available at
    https://www.gnu.org/software/classpath/license.html.

    SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

--%>

<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tck" uri="http://java.sun.com/jstltck/jstltck-util" %>
<%@ page import= "javax.sql.*, java.util.*" %>

<tck:test testName="negativeUpdateSQLAttributeTest">

   <%
      pageContext.setAttribute("invalidSQL", new Integer("-1"));
   %>


   <!-- Validate that a sql:update action which  passes an
              invalid Datatype to the sql attribute throws JspException -->

  <h1>Validate that a sql:update action which  passes an invalid Datatype to the
   sql attribute throws JspException </h1>
  <p>
  <tck:catch var="e" exception= "jakarta.servlet.jsp.JspException"  >
      <sql:update var="resultSet"
                  dataSource="${applicationScope.jstlDS}"
                  sql="${invalidSql}" />
   </tck:catch>

   <c:out value="${e}" escapeXml="false"/>




</tck:test>
