/*
 * Copyright (c) 2008, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jta.ee.transactional;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Logger;

public class EJBLiteServletVehicle extends Client implements Servlet, ServletConfig {

    private static Logger logger = Logger.getLogger(EJBLiteServletVehicle.class.getName());

    private HttpServletDelegate delegate = new HttpServletDelegate();

    @Override
    public void init(ServletConfig config) throws ServletException {
        delegate.init(config);
    }

    @Override
    public ServletConfig getServletConfig() {
        return delegate.getServletConfig();
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        delegate.service(request, response);

        setInjectionSupported(true);
        String tn = request.getParameter("testName");
        logger.fine("EJBLiteServletVehicle processing request testName=" + tn);
        setTestName(tn);
        setModuleName(getServletContext().getContextPath());
        String sta = getStatus(); // to trigger the test run

        PrintWriter pw = response.getWriter();
        pw.println(sta + " " + getReason());
        cleanup(); // need to reset all fields since servlet instances are shared

    }

    @Override
    public String getServletInfo() {
        return delegate.getServletInfo();
    }

    @Override
    public void destroy() {
        delegate.destroy();
        delegate = null;
    }

    @Override
    public String getServletName() {
        return delegate.getServletName();
    }

    @Override
    public ServletContext getServletContext() {
        return delegate.getServletContext();
    }

    @Override
    public String getInitParameter(String arg0) {
        return delegate.getInitParameter(arg0);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return delegate.getInitParameterNames();
    }

}
