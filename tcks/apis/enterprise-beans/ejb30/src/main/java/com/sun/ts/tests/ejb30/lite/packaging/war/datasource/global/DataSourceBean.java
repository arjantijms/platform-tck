/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.ejb30.lite.packaging.war.datasource.global;

import static com.sun.ts.tests.ejb30.lite.packaging.war.datasource.common.DataSourceTest.verifyDataSource;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Local;
import jakarta.ejb.Startup;
import javax.sql.DataSource;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.transaction.UserTransaction;

import com.sun.ts.tests.ejb30.lite.packaging.war.datasource.common.DataSourceIF;
import com.sun.ts.tests.ejb30.lite.packaging.war.datasource.common.ComponentBase;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
@Local(DataSourceIF.class)
@DataSourceDefinition(name="java:global/jdbc/DB3",
        className="org.apache.derby.jdbc.ClientDataSource",
        portNumber=1527,
        serverName="localhost",
        databaseName="derbyDB;create=true",
        user="cts1",
        transactional=false,
        password="cts1",
        properties={}
) 
public class DataSourceBean extends ComponentBase {

    @Resource(lookup="java:global/jdbc/DB3")
    private DataSource db3; 

    @Resource
    private UserTransaction ut;

    @SuppressWarnings("unused")
    @PostConstruct
    private void postConstruct() {
        boolean c = true;
        getPostConstructRecords().append(String.format("In postConstruct of %s%n", this));
        
        verifyDataSource(getPostConstructRecords(), c, "java:global/jdbc/DB3");
        verifyDataSource(getPostConstructRecords(), c, db3);
      
    }
    
    @Override
    public StringBuilder getConnection() {
        StringBuilder sb = new StringBuilder();
        try {
            ut.begin();
            verifyDataSource(getPostConstructRecords(), true, db3);
            ut.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb;
    }

}
