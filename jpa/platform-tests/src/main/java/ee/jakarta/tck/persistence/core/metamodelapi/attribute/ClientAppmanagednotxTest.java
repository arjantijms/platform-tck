package ee.jakarta.tck.persistence.core.metamodelapi.attribute;

import ee.jakarta.tck.persistence.core.metamodelapi.attribute.Client;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import tck.arquillian.porting.lib.spi.TestArchiveProcessor;
import tck.arquillian.protocol.common.TargetVehicle;



@ExtendWith(ArquillianExtension.class)
@Tag("persistence")
@Tag("platform")
@Tag("web")
@Tag("tck-appclient")

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ClientAppmanagednotxTest extends ee.jakarta.tck.persistence.core.metamodelapi.attribute.Client {
    static final String VEHICLE_ARCHIVE = "jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle";

        /**
        EE10 Deployment Descriptors:
        jpa_core_metamodelapi_attribute: META-INF/persistence.xml
        jpa_core_metamodelapi_attribute_appmanaged_vehicle_client: META-INF/application-client.xml
        jpa_core_metamodelapi_attribute_appmanaged_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client: META-INF/application-client.xml
        jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_metamodelapi_attribute_pmservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_metamodelapi_attribute_puservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_metamodelapi_attribute_stateful3_vehicle_client: META-INF/application-client.xml
        jpa_core_metamodelapi_attribute_stateful3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_metamodelapi_attribute_stateless3_vehicle_client: META-INF/application-client.xml
        jpa_core_metamodelapi_attribute_stateless3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_metamodelapi_attribute_vehicles: 

        Found Descriptors:
        Client:

        /com/sun/ts/tests/common/vehicle/appmanagedNoTx/appmanagedNoTx_vehicle_client.xml
        Ejb:

        Ear:

        */
        @TargetsContainer("tck-appclient")
        @OverProtocol("appclient")
        @Deployment(name = VEHICLE_ARCHIVE, order = 2)
        public static EnterpriseArchive createDeploymentVehicle(@ArquillianResource TestArchiveProcessor archiveProcessor) {
        // Client
            // the jar with the correct archive name
            JavaArchive jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client = ShrinkWrap.create(JavaArchive.class, "jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client.jar");
            // The class files
            jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client.addClasses(
            com.sun.ts.tests.common.vehicle.VehicleRunnerFactory.class,
            com.sun.ts.tests.common.vehicle.appmanagedNoTx.AppManagedNoTxVehicleIF.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManager.class,
            com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareIF.class,
            com.sun.ts.lib.harness.EETest.Fault.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManagerFactory.class,
            com.sun.ts.tests.common.vehicle.EmptyVehicleRunner.class,
            ee.jakarta.tck.persistence.common.PMClientBase.class,
            com.sun.ts.tests.common.vehicle.appmanagedNoTx.AppManagedNoTxVehicleRunner.class,
            com.sun.ts.tests.common.vehicle.VehicleRunnable.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UserTransactionWrapper.class,
            com.sun.ts.lib.harness.EETest.class,
            com.sun.ts.lib.harness.ServiceEETest.class,
            com.sun.ts.tests.common.vehicle.ejb3share.EntityTransactionWrapper.class,
            com.sun.ts.lib.harness.EETest.SetupException.class,
            com.sun.ts.tests.common.vehicle.VehicleClient.class,
            com.sun.ts.tests.common.vehicle.ejb3share.NoopTransactionWrapper.class
            );
            // The application-client.xml descriptor
            URL resURL = Client.class.getResource("/com/sun/ts/tests/common/vehicle/appmanagedNoTx/appmanagedNoTx_vehicle_client.xml");
            if(resURL != null) {
              jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client.addAsManifestResource(resURL, "application-client.xml");
            }
            // The sun-application-client.xml file need to be added or should this be in in the vendor Arquillian extension?
            resURL = Client.class.getResource("//com/sun/ts/tests/common/vehicle/appmanagedNoTx/appmanagedNoTx_vehicle_client.jar.sun-application-client.xml");
            if(resURL != null) {
              jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client.addAsManifestResource(resURL, "application-client.xml");
            }
            jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client.addAsManifestResource(new StringAsset("Main-Class: " + Client.class.getName() + "\n"), "MANIFEST.MF");
            // Call the archive processor
            archiveProcessor.processClientArchive(jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client, Client.class, resURL);

        // Ejb
            // the jar with the correct archive name
            JavaArchive jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_ejb = ShrinkWrap.create(JavaArchive.class, "jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_ejb.jar");
            // The class files
            jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_ejb.addClasses(
                com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareBaseBean.class,
                com.sun.ts.tests.common.vehicle.VehicleRunnerFactory.class,
                com.sun.ts.tests.common.vehicle.appmanagedNoTx.AppManagedNoTxVehicleIF.class,
                com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManager.class,
                com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareIF.class,
                com.sun.ts.lib.harness.EETest.Fault.class,
                com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManagerFactory.class,
                ee.jakarta.tck.persistence.common.PMClientBase.class,
                com.sun.ts.tests.common.vehicle.VehicleRunnable.class,
                com.sun.ts.tests.common.vehicle.ejb3share.UserTransactionWrapper.class,
                com.sun.ts.lib.harness.EETest.class,
                com.sun.ts.lib.harness.ServiceEETest.class,
                com.sun.ts.tests.common.vehicle.ejb3share.EntityTransactionWrapper.class,
                com.sun.ts.lib.harness.EETest.SetupException.class,
                com.sun.ts.tests.common.vehicle.VehicleClient.class,
                com.sun.ts.tests.common.vehicle.ejb3share.NoopTransactionWrapper.class,
                com.sun.ts.tests.common.vehicle.appmanagedNoTx.AppManagedNoTxVehicleBean.class,
                ee.jakarta.tck.persistence.core.metamodelapi.attribute.Client.class
            );
            // The ejb-jar.xml descriptor
            URL ejbResURL = Client.class.getResource("//vehicle/appmanagedNoTx/appmanagedNoTx_vehicle_ejb.xml");
            if(ejbResURL != null) {
              jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_ejb.addAsManifestResource(ejbResURL, "ejb-jar.xml");
            }
            // The sun-ejb-jar.xml file
            ejbResURL = Client.class.getResource("//vehicle/appmanagedNoTx/appmanagedNoTx_vehicle_ejb.jar.sun-ejb-jar.xml");
            if(ejbResURL != null) {
              jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_ejb.addAsManifestResource(ejbResURL, "sun-ejb-jar.xml");
            }
            // Call the archive processor
            archiveProcessor.processEjbArchive(jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_ejb, Client.class, ejbResURL);

        // Par
            // the jar with the correct archive name
            JavaArchive jpa_core_metamodelapi_attribute = ShrinkWrap.create(JavaArchive.class, "jpa_core_metamodelapi_attribute.jar");
            // The class files
            jpa_core_metamodelapi_attribute.addClasses(
                ee.jakarta.tck.persistence.core.metamodelapi.attribute.Order.class
            );
            // The persistence.xml descriptor
            URL parURL = Client.class.getResource("persistence.xml");
            if(parURL != null) {
              jpa_core_metamodelapi_attribute.addAsManifestResource(parURL, "persistence.xml");
            }
            // Call the archive processor
            archiveProcessor.processParArchive(jpa_core_metamodelapi_attribute, Client.class, parURL);
            // The orm.xml file
            parURL = Client.class.getResource("orm.xml");
            if(parURL != null) {
              jpa_core_metamodelapi_attribute.addAsManifestResource(parURL, "orm.xml");
            }

        // Ear
            EnterpriseArchive jpa_core_metamodelapi_attribute_vehicles_ear = ShrinkWrap.create(EnterpriseArchive.class, "jpa_core_metamodelapi_attribute_vehicles.ear");

            // Any libraries added to the ear

            // The component jars built by the package target
            jpa_core_metamodelapi_attribute_vehicles_ear.addAsModule(jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_ejb);
            jpa_core_metamodelapi_attribute_vehicles_ear.addAsModule(jpa_core_metamodelapi_attribute_appmanagedNoTx_vehicle_client);

            jpa_core_metamodelapi_attribute_vehicles_ear.addAsLibrary(jpa_core_metamodelapi_attribute);



            // The application.xml descriptor
            URL earResURL = Client.class.getResource("/com/sun/ts/tests/jpa/core/metamodelapi/attribute/");
            if(earResURL != null) {
              jpa_core_metamodelapi_attribute_vehicles_ear.addAsManifestResource(earResURL, "application.xml");
            }
            // The sun-application.xml descriptor
            earResURL = Client.class.getResource("/com/sun/ts/tests/jpa/core/metamodelapi/attribute/.ear.sun-application.xml");
            if(earResURL != null) {
              jpa_core_metamodelapi_attribute_vehicles_ear.addAsManifestResource(earResURL, "sun-application.xml");
            }
            // Call the archive processor
            archiveProcessor.processEarArchive(jpa_core_metamodelapi_attribute_vehicles_ear, Client.class, earResURL);
        return jpa_core_metamodelapi_attribute_vehicles_ear;
        }

        @Test
        @Override
        @TargetVehicle("appmanagedNoTx")
        public void getName() throws java.lang.Exception {
            super.getName();
        }

        @Test
        @Override
        @TargetVehicle("appmanagedNoTx")
        public void getPersistentAttributeType() throws java.lang.Exception {
            super.getPersistentAttributeType();
        }

        @Test
        @Override
        @TargetVehicle("appmanagedNoTx")
        public void getDeclaringType() throws java.lang.Exception {
            super.getDeclaringType();
        }

        @Test
        @Override
        @TargetVehicle("appmanagedNoTx")
        public void getJavaType() throws java.lang.Exception {
            super.getJavaType();
        }

        @Test
        @Override
        @TargetVehicle("appmanagedNoTx")
        public void getJavaMember() throws java.lang.Exception {
            super.getJavaMember();
        }

        @Test
        @Override
        @TargetVehicle("appmanagedNoTx")
        public void isAssociation() throws java.lang.Exception {
            super.isAssociation();
        }

        @Test
        @Override
        @TargetVehicle("appmanagedNoTx")
        public void isCollection() throws java.lang.Exception {
            super.isCollection();
        }


}