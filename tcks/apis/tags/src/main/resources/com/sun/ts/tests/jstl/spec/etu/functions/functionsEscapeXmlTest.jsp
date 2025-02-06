<%--

    Copyright (c) 2003, 2018 Oracle and/or its affiliates. All rights reserved.

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

<%@ page contentType="text/plain" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<% pageContext.setAttribute("escString", "><&'\""); %>
<c:choose>
    <c:when test="${'&gt;&lt;&amp;&#039;&#034;' == fn:escapeXml(escString)}">
        Test PASSED
    </c:when>
    <c:otherwise>
        Test FAILED.  Expected the return value of escapeXml(String) to be
        '&gt;&lt;&amp;&#039;&#034;'.  Actual value was: ${fn:escapeXml(escString)}
    </c:otherwise>
</c:choose>
