package ee.jakarta.tck.persistence.core.annotations.access.property;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import tck.arquillian.porting.lib.spi.TestArchiveProcessor;
import tck.arquillian.protocol.common.TargetVehicle;

import java.net.URL;



@ExtendWith(ArquillianExtension.class)
@Tag("persistence")
@Tag("platform")
@Tag("web")
@Tag("tck-javatest")

@TestMethodOrder(MethodOrderer.MethodName.class)
public class Client1PuservletTest extends ee.jakarta.tck.persistence.core.annotations.access.property.Client1 {
    static final String VEHICLE_ARCHIVE = "jpa_core_annotations_access_property_puservlet_vehicle";

        /**
        EE10 Deployment Descriptors:
        jpa_core_annotations_access_property: META-INF/persistence.xml
        jpa_core_annotations_access_property_appmanaged_vehicle_client: META-INF/application-client.xml
        jpa_core_annotations_access_property_appmanaged_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_annotations_access_property_appmanagedNoTx_vehicle_client: META-INF/application-client.xml
        jpa_core_annotations_access_property_appmanagedNoTx_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_annotations_access_property_pmservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_annotations_access_property_puservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_annotations_access_property_stateful3_vehicle_client: META-INF/application-client.xml
        jpa_core_annotations_access_property_stateful3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_annotations_access_property_stateless3_vehicle_client: META-INF/application-client.xml
        jpa_core_annotations_access_property_stateless3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_annotations_access_property_vehicles: 

        Found Descriptors:
        War:

        /com/sun/ts/tests/common/vehicle/puservlet/puservlet_vehicle_web.xml
        Ear:

        */
        @TargetsContainer("tck-javatest")
        @OverProtocol("javatest")
        @Deployment(name = VEHICLE_ARCHIVE, order = 2)
        public static WebArchive createDeploymentVehicle(@ArquillianResource TestArchiveProcessor archiveProcessor) {
        // War
            // the war with the correct archive name
            WebArchive jpa_core_annotations_access_property_puservlet_vehicle_web = ShrinkWrap.create(WebArchive.class, "jpa_core_annotations_access_property_puservlet_vehicle_web.war");
            // The class files
            jpa_core_annotations_access_property_puservlet_vehicle_web.addClasses(
            com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareBaseBean.class,
            com.sun.ts.tests.common.vehicle.VehicleRunnerFactory.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManager.class,
            com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareIF.class,
            com.sun.ts.tests.common.vehicle.puservlet.PUServletVehicle.class,
            com.sun.ts.lib.harness.EETest.Fault.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManagerFactory.class,
            ee.jakarta.tck.persistence.common.PMClientBase.class,
            ee.jakarta.tck.persistence.core.annotations.access.property.Client1.class,
            com.sun.ts.tests.common.vehicle.servlet.ServletVehicle.class,
            com.sun.ts.tests.common.vehicle.VehicleRunnable.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UserTransactionWrapper.class,
            com.sun.ts.lib.harness.EETest.class,
            com.sun.ts.lib.harness.ServiceEETest.class,
            com.sun.ts.tests.common.vehicle.ejb3share.EntityTransactionWrapper.class,
            com.sun.ts.lib.harness.EETest.SetupException.class,
            com.sun.ts.tests.common.vehicle.VehicleClient.class,
            com.sun.ts.tests.common.vehicle.ejb3share.NoopTransactionWrapper.class,
            ee.jakarta.tck.persistence.core.annotations.access.property.Client.class
            );
            // The web.xml descriptor
            URL warResURL = Client1.class.getResource("/com/sun/ts/tests/common/vehicle/puservlet/puservlet_vehicle_web.xml");
            if(warResURL != null) {
              jpa_core_annotations_access_property_puservlet_vehicle_web.addAsWebInfResource(warResURL, "web.xml");
            }
            // The sun-web.xml descriptor
            warResURL = Client1.class.getResource("/com/sun/ts/tests/common/vehicle/puservlet/puservlet_vehicle_web.war.sun-web.xml");
            if(warResURL != null) {
              jpa_core_annotations_access_property_puservlet_vehicle_web.addAsWebInfResource(warResURL, "sun-web.xml");
            }

            // Any libraries added to the war

            // Web content
            warResURL = Client1.class.getResource("/com/sun/ts/tests/jpa/core/annotations/access/property/jpa_core_annotations_access_property.jar");
            if(warResURL != null) {
              jpa_core_annotations_access_property_puservlet_vehicle_web.addAsWebResource(warResURL, "/WEB-INF/lib/jpa_core_annotations_access_property.jar");
            }
            warResURL = Client1.class.getResource("/com/sun/ts/tests/common/vehicle/puservlet/puservlet_vehicle_web.xml");
            if(warResURL != null) {
              jpa_core_annotations_access_property_puservlet_vehicle_web.addAsWebResource(warResURL, "/WEB-INF/puservlet_vehicle_web.xml");
            }

           // Call the archive processor
           archiveProcessor.processWebArchive(jpa_core_annotations_access_property_puservlet_vehicle_web, Client1.class, warResURL);


        // Par
            // the jar with the correct archive name
            JavaArchive jpa_core_annotations_access_property = ShrinkWrap.create(JavaArchive.class, "jpa_core_annotations_access_property.jar");
            // The class files
            jpa_core_annotations_access_property.addClasses(
                ee.jakarta.tck.persistence.core.annotations.access.property.DataTypes2.class,
                ee.jakarta.tck.persistence.core.annotations.access.property.DataTypes.class,
                ee.jakarta.tck.persistence.core.types.common.Grade.class
            );
            // The persistence.xml descriptor
            URL parURL = Client1.class.getResource("persistence.xml");
            if(parURL != null) {
              jpa_core_annotations_access_property.addAsManifestResource(parURL, "persistence.xml");
            }
            // Add the Persistence mapping-file
            URL mappingURL = Client1.class.getResource("myMappingFile.xml");
            if(mappingURL != null) {
              jpa_core_annotations_access_property.addAsResource(mappingURL, "myMappingFile.xml");
            }
            mappingURL = Client1.class.getResource("myMappingFile1.xml");
            if(mappingURL != null) {
              jpa_core_annotations_access_property.addAsResource(mappingURL, "myMappingFile1.xml");
            }
            mappingURL = Client1.class.getResource("myMappingFile2.xml");
            if(mappingURL != null) {
              jpa_core_annotations_access_property.addAsResource(mappingURL, "myMappingFile2.xml");
            }
            // Call the archive processor
            archiveProcessor.processParArchive(jpa_core_annotations_access_property, Client1.class, parURL);
            parURL = Client1.class.getResource("orm.xml");
            if(parURL != null) {
              jpa_core_annotations_access_property.addAsManifestResource(parURL, "orm.xml");
            }

            jpa_core_annotations_access_property_puservlet_vehicle_web.addAsLibrary(jpa_core_annotations_access_property);
            return jpa_core_annotations_access_property_puservlet_vehicle_web;
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest1() throws java.lang.Exception {
            super.propertyTypeTest1();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest2() throws java.lang.Exception {
            super.propertyTypeTest2();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest3() throws java.lang.Exception {
            super.propertyTypeTest3();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest4() throws java.lang.Exception {
            super.propertyTypeTest4();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest5() throws java.lang.Exception {
            super.propertyTypeTest5();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest6() throws java.lang.Exception {
            super.propertyTypeTest6();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest7() throws java.lang.Exception {
            super.propertyTypeTest7();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest8() throws java.lang.Exception {
            super.propertyTypeTest8();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest9() throws java.lang.Exception {
            super.propertyTypeTest9();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest10() throws java.lang.Exception {
            super.propertyTypeTest10();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest11() throws java.lang.Exception {
            super.propertyTypeTest11();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest12() throws java.lang.Exception {
            super.propertyTypeTest12();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void propertyTypeTest13() throws java.lang.Exception {
            super.propertyTypeTest13();
        }


}