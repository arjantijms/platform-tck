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

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>

<%@ taglib prefix="tck" uri="http://java.sun.com/jstltck/jstltck-util" %>
<tck:test testName="negativeCWOWhenSelectFailureTest">

    <!-- If the XPath expression provided to the select attribute
             of the when action fails to evaluate, an instance of
             jakarta.servlet.jsp.JspException must be thrown. -->
    <tck:catch var="rex" exception="jakarta.servlet.jsp.JspException">
        <x:choose>
            <x:when select="$doc//a">
                Body content improperly processed.<br>
            </x:when>
        </x:choose>
    </tck:catch>
    <c:out value="${rex}" default="Test FAILED" escapeXml="false"/>
</tck:test>
