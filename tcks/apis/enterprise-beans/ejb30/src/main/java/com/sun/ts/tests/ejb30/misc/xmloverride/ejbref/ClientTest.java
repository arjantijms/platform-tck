package com.sun.ts.tests.ejb30.misc.xmloverride.ejbref;

import com.sun.ts.lib.harness.Fault;

import java.net.URL;

import com.sun.ts.lib.harness.SetupException;
import com.sun.ts.tests.common.base.EETest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import tck.arquillian.porting.lib.spi.TestArchiveProcessor;


@ExtendWith(ArquillianExtension.class)
@Tag("platform")
@Tag("ejb_3x_remote_optional")
@Tag("web_optional")
@Tag("tck-appclient")

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ClientTest extends com.sun.ts.tests.ejb30.misc.xmloverride.ejbref.Client {
    /**
        EE10 Deployment Descriptors:
        misc_xmloverride_ejbref: 
        misc_xmloverride_ejbref_client: META-INF/application-client.xml
        misc_xmloverride_ejbref_ejb: jar.sun-ejb-jar.xml

        Found Descriptors:
        Client:

        /com/sun/ts/tests/ejb30/misc/xmloverride/ejbref/misc_xmloverride_ejbref_client.xml
        Ejb:

        /com/sun/ts/tests/ejb30/misc/xmloverride/ejbref/misc_xmloverride_ejbref_ejb.jar.sun-ejb-jar.xml
        Ear:

        */
        @TargetsContainer("tck-appclient")
        @OverProtocol("appclient")
        @Deployment(name = "misc_xmloverride_ejbref", order = 2)
        public static EnterpriseArchive createDeployment(@ArquillianResource TestArchiveProcessor archiveProcessor) {
        // Client
            // the jar with the correct archive name
            JavaArchive misc_xmloverride_ejbref_client = ShrinkWrap.create(JavaArchive.class, "misc_xmloverride_ejbref_client.jar");
            // The class files
            misc_xmloverride_ejbref_client.addClasses(
            Fault.class,
            com.sun.ts.tests.ejb30.misc.xmloverride.ejbref.Client.class,
            EETest.class,
            SetupException.class
            );
            // The application-client.xml descriptor
            URL resURL = Client.class.getResource("/com/sun/ts/tests/ejb30/misc/xmloverride/ejbref/misc_xmloverride_ejbref_client.xml");
            if(resURL != null) {
              misc_xmloverride_ejbref_client.addAsManifestResource(resURL, "application-client.xml");
            }
            // The sun-application-client.xml file need to be added or should this be in in the vendor Arquillian extension?
            resURL = Client.class.getResource("/com/sun/ts/tests/ejb30/misc/xmloverride/ejbref/misc_xmloverride_ejbref_client.jar.sun-application-client.xml");
            if(resURL != null) {
              misc_xmloverride_ejbref_client.addAsManifestResource(resURL, "sun-application-client.xml");
            }
            misc_xmloverride_ejbref_client.addAsManifestResource(new StringAsset("Main-Class: " + Client.class.getName() + "\n"), "MANIFEST.MF");
            // Call the archive processor
            archiveProcessor.processClientArchive(misc_xmloverride_ejbref_client, Client.class, resURL);

        // Ejb
            // the jar with the correct archive name
            JavaArchive misc_xmloverride_ejbref_ejb = ShrinkWrap.create(JavaArchive.class, "misc_xmloverride_ejbref_ejb.jar");
            // The class files
            misc_xmloverride_ejbref_ejb.addClasses(
                com.sun.ts.tests.ejb30.misc.xmloverride.ejbref.XmlOverrideBean.class,
                com.sun.ts.tests.ejb30.misc.xmloverride.ejbref.XmlOverride2Bean.class
            );
            // The ejb-jar.xml descriptor
            URL ejbResURL = Client.class.getResource("/com/sun/ts/tests/ejb30/misc/xmloverride/ejbref/misc_xmloverride_ejbref_ejb.xml");
            if(ejbResURL != null) {
              misc_xmloverride_ejbref_ejb.addAsManifestResource(ejbResURL, "ejb-jar.xml");
            }
            // The sun-ejb-jar.xml file
            ejbResURL = Client.class.getResource("/com/sun/ts/tests/ejb30/misc/xmloverride/ejbref/misc_xmloverride_ejbref_ejb.jar.sun-ejb-jar.xml");
            if(ejbResURL != null) {
              misc_xmloverride_ejbref_ejb.addAsManifestResource(ejbResURL, "sun-ejb-jar.xml");
            }
            // Call the archive processor
            archiveProcessor.processEjbArchive(misc_xmloverride_ejbref_ejb, Client.class, ejbResURL);

        // Ear
            EnterpriseArchive misc_xmloverride_ejbref_ear = ShrinkWrap.create(EnterpriseArchive.class, "misc_xmloverride_ejbref.ear");

            // Any libraries added to the ear
                URL libURL;
                JavaArchive shared_lib = ShrinkWrap.create(JavaArchive.class, "shared.jar");
                    // The class files
                    shared_lib.addClasses(
                        com.sun.ts.tests.ejb30.common.lite.NumberEnum.class,
                        com.sun.ts.tests.ejb30.common.helper.Helper.class,
                        com.sun.ts.tests.ejb30.common.helper.TLogger.class,
                        com.sun.ts.tests.ejb30.common.helper.ServiceLocator.class,
                        com.sun.ts.tests.ejb30.common.lite.NumberIF.class
                    );


                misc_xmloverride_ejbref_ear.addAsLibrary(shared_lib);


            // The component jars built by the package target
            misc_xmloverride_ejbref_ear.addAsModule(misc_xmloverride_ejbref_ejb);
            misc_xmloverride_ejbref_ear.addAsModule(misc_xmloverride_ejbref_client);



            // The application.xml descriptor
            URL earResURL = Client.class.getResource("/com/sun/ts/tests/ejb30/misc/xmloverride/ejbref/application.xml");
            if(earResURL != null) {
              misc_xmloverride_ejbref_ear.addAsManifestResource(earResURL, "application.xml");
            }
            // The sun-application.xml descriptor
            earResURL = Client.class.getResource("/com/sun/ts/tests/ejb30/misc/xmloverride/ejbref/application.ear.sun-application.xml");
            if(earResURL != null) {
              misc_xmloverride_ejbref_ear.addAsManifestResource(earResURL, "sun-application.xml");
            }
            // Call the archive processor
            archiveProcessor.processEarArchive(misc_xmloverride_ejbref_ear, Client.class, earResURL);
        return misc_xmloverride_ejbref_ear;
        }

        @Test
        @Override
        public void resolveByEjbLinkInXml() {
            super.resolveByEjbLinkInXml();
        }

        @Test
        @Override
        public void overrideLookup() {
            super.overrideLookup();
        }

        @Test
        @Override
        public void overrideInterfaceType() {
            super.overrideInterfaceType();
        }

        @Test
        @Override
        public void overrideBeanName() {
            super.overrideBeanName();
        }


}