package ee.jakarta.tck.persistence.core.entityManager;

import ee.jakarta.tck.persistence.core.entityManager.Client1;
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
@Tag("tck-javatest")

@TestMethodOrder(MethodOrderer.MethodName.class)
public class Client1PuservletTest extends ee.jakarta.tck.persistence.core.entityManager.Client1 {
    static final String VEHICLE_ARCHIVE = "jpa_core_entityManager_puservlet_vehicle";

        /**
        EE10 Deployment Descriptors:
        jpa_core_entityManager: META-INF/persistence.xml
        jpa_core_entityManager_appmanaged_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManager_appmanaged_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManager_appmanagedNoTx_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManager_appmanagedNoTx_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManager_pmservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_entityManager_puservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_entityManager_stateful3_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManager_stateful3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManager_stateless3_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManager_stateless3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManager_vehicles: 
        jpa_core_entityManager2: META-INF/persistence.xml
        jpa_core_entityManager2_appmanagedNoTx_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManager2_appmanagedNoTx_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManager2_pmservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_entityManager2_puservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_entityManager2_stateless3_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManager2_stateless3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManager2_vehicles: 
        jpa_core_entityManagerFactory: META-INF/persistence.xml
        jpa_core_entityManagerFactory_appmanaged_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManagerFactory_appmanaged_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManagerFactory_appmanagedNoTx_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManagerFactory_appmanagedNoTx_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManagerFactory_pmservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_entityManagerFactory_puservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_entityManagerFactory_stateful3_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManagerFactory_stateful3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManagerFactory_stateless3_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManagerFactory_stateless3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManagerFactory_vehicles: 
        jpa_core_entityManagerFactoryCloseException: META-INF/persistence.xml
        jpa_core_entityManagerFactoryCloseException_appmanaged_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManagerFactoryCloseException_appmanaged_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManagerFactoryCloseException_appmanagedNoTx_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManagerFactoryCloseException_appmanagedNoTx_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManagerFactoryCloseException_pmservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_entityManagerFactoryCloseException_puservlet_vehicle_web: WEB-INF/web.xml
        jpa_core_entityManagerFactoryCloseException_stateful3_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManagerFactoryCloseException_stateful3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManagerFactoryCloseException_stateless3_vehicle_client: META-INF/application-client.xml
        jpa_core_entityManagerFactoryCloseException_stateless3_vehicle_ejb: jar.sun-ejb-jar.xml
        jpa_core_entityManagerFactoryCloseException_vehicles: 

        Found Descriptors:
        War:

        /com/sun/ts/tests/common/vehicle/puservlet/puservlet_vehicle_web.xml
        Ear:

        */
        @TargetsContainer("tck-javatest")
        @OverProtocol("javatest")
        @Deployment(name = VEHICLE_ARCHIVE, order = 2)
        public static EnterpriseArchive createDeploymentVehicle(@ArquillianResource TestArchiveProcessor archiveProcessor) {
        // War
            // the war with the correct archive name
            WebArchive jpa_core_entityManager_puservlet_vehicle_web = ShrinkWrap.create(WebArchive.class, "jpa_core_entityManager_puservlet_vehicle_web.war");
            // The class files
            jpa_core_entityManager_puservlet_vehicle_web.addClasses(
            com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareBaseBean.class,
            com.sun.ts.tests.common.vehicle.VehicleRunnerFactory.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManager.class,
            com.sun.ts.tests.common.vehicle.ejb3share.EJB3ShareIF.class,
            com.sun.ts.tests.common.vehicle.puservlet.PUServletVehicle.class,
            com.sun.ts.lib.harness.EETest.Fault.class,
            ee.jakarta.tck.persistence.core.entityManager.Client1.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UseEntityManagerFactory.class,
            ee.jakarta.tck.persistence.common.PMClientBase.class,
            com.sun.ts.tests.common.vehicle.servlet.ServletVehicle.class,
            com.sun.ts.tests.common.vehicle.VehicleRunnable.class,
            com.sun.ts.tests.common.vehicle.ejb3share.UserTransactionWrapper.class,
            com.sun.ts.lib.harness.EETest.class,
            com.sun.ts.lib.harness.ServiceEETest.class,
            com.sun.ts.tests.common.vehicle.ejb3share.EntityTransactionWrapper.class,
            com.sun.ts.lib.harness.EETest.SetupException.class,
            com.sun.ts.tests.common.vehicle.VehicleClient.class,
            com.sun.ts.tests.common.vehicle.ejb3share.NoopTransactionWrapper.class
            );
            // The web.xml descriptor
            URL warResURL = Client1.class.getResource("/com/sun/ts/tests/common/vehicle/puservlet/puservlet_vehicle_web.xml");
            if(warResURL != null) {
              jpa_core_entityManager_puservlet_vehicle_web.addAsWebInfResource(warResURL, "web.xml");
            }
            // The sun-web.xml descriptor
            warResURL = Client1.class.getResource("//com/sun/ts/tests/common/vehicle/puservlet/puservlet_vehicle_web.war.sun-web.xml");
            if(warResURL != null) {
              jpa_core_entityManager_puservlet_vehicle_web.addAsWebInfResource(warResURL, "sun-web.xml");
            }

            // Any libraries added to the war

            // Web content
            warResURL = Client1.class.getResource("/com/sun/ts/tests/jpa/core/entityManager/jpa_core_entityManager.jar");
            if(warResURL != null) {
              jpa_core_entityManager_puservlet_vehicle_web.addAsWebResource(warResURL, "/WEB-INF/lib/jpa_core_entityManager.jar");
            }
            warResURL = Client1.class.getResource("/com/sun/ts/tests/common/vehicle/puservlet/puservlet_vehicle_web.xml");
            if(warResURL != null) {
              jpa_core_entityManager_puservlet_vehicle_web.addAsWebResource(warResURL, "/WEB-INF/puservlet_vehicle_web.xml");
            }

           // Call the archive processor
           archiveProcessor.processWebArchive(jpa_core_entityManager_puservlet_vehicle_web, Client1.class, warResURL);

        // Par
            // the jar with the correct archive name
            JavaArchive jpa_core_entityManager = ShrinkWrap.create(JavaArchive.class, "jpa_core_entityManager.jar");
            // The class files
            jpa_core_entityManager.addClasses(
                ee.jakarta.tck.persistence.core.entityManager.Employee.class,
                ee.jakarta.tck.persistence.core.entityManager.Order.class
            );
            // The persistence.xml descriptor
            URL parURL = Client1.class.getResource("persistence.xml");
            if(parURL != null) {
              jpa_core_entityManager.addAsManifestResource(parURL, "persistence.xml");
            }
            // Call the archive processor
            archiveProcessor.processParArchive(jpa_core_entityManager, Client1.class, parURL);
            // The orm.xml file
            parURL = Client1.class.getResource("orm.xml");
            if(parURL != null) {
              jpa_core_entityManager.addAsManifestResource(parURL, "orm.xml");
            }

        // Ear
            EnterpriseArchive jpa_core_entityManager_vehicles_ear = ShrinkWrap.create(EnterpriseArchive.class, "jpa_core_entityManager_vehicles.ear");

            // Any libraries added to the ear

            // The component jars built by the package target
            jpa_core_entityManager_vehicles_ear.addAsModule(jpa_core_entityManager_puservlet_vehicle_web);

            jpa_core_entityManager_vehicles_ear.addAsLibrary(jpa_core_entityManager);



            // The application.xml descriptor
            URL earResURL = Client1.class.getResource("/com/sun/ts/tests/jpa/core/entityManager/");
            if(earResURL != null) {
              jpa_core_entityManager_vehicles_ear.addAsManifestResource(earResURL, "application.xml");
            }
            // The sun-application.xml descriptor
            earResURL = Client1.class.getResource("/com/sun/ts/tests/jpa/core/entityManager/.ear.sun-application.xml");
            if(earResURL != null) {
              jpa_core_entityManager_vehicles_ear.addAsManifestResource(earResURL, "sun-application.xml");
            }
            // Call the archive processor
            archiveProcessor.processEarArchive(jpa_core_entityManager_vehicles_ear, Client1.class, earResURL);
        return jpa_core_entityManager_vehicles_ear;
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void autoCloseableTest() throws java.lang.Exception {
            super.autoCloseableTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void mergeTest() throws java.lang.Exception {
            super.mergeTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void mergeExceptionsTest() throws java.lang.Exception {
            super.mergeExceptionsTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void removeExceptionsTest() throws java.lang.Exception {
            super.removeExceptionsTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void lockIllegalStateExceptionTest() throws java.lang.Exception {
            super.lockIllegalStateExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void refreshInvalidObjectIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.refreshInvalidObjectIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void refreshNonManagedObjectIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.refreshNonManagedObjectIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void refreshInvalidObjectMapIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.refreshInvalidObjectMapIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void refreshNonManagedObjectMapIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.refreshNonManagedObjectMapIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void refreshInvalidObjectLockModeTypeIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.refreshInvalidObjectLockModeTypeIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void refreshNonManagedObjectLockModeTypeIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.refreshNonManagedObjectLockModeTypeIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void refreshInvalidObjectLockModeTypeMapIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.refreshInvalidObjectLockModeTypeMapIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void refreshNonManagedObjectLockModeTypeMapIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.refreshNonManagedObjectLockModeTypeMapIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void containsIllegalArgumentException() throws java.lang.Exception {
            super.containsIllegalArgumentException();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void createNamedQueryIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.createNamedQueryIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void createQueryIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.createQueryIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void detachIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.detachIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void getEntityManagerFactoryTest() throws java.lang.Exception {
            super.getEntityManagerFactoryTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void emGetMetamodelTest() throws java.lang.Exception {
            super.emGetMetamodelTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void setPropertyTest() throws java.lang.Exception {
            super.setPropertyTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void getCriteriaBuilderTest() throws java.lang.Exception {
            super.getCriteriaBuilderTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void isJoinedToTransactionTest() throws java.lang.Exception {
            super.isJoinedToTransactionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void createStoredProcedureQueryStringIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.createStoredProcedureQueryStringIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void createStoredProcedureQueryStringClassArrayIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.createStoredProcedureQueryStringClassArrayIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void createStoredProcedureQueryStringStringArrayIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.createStoredProcedureQueryStringStringArrayIllegalArgumentExceptionTest();
        }

        @Test
        @Override
        @TargetVehicle("puservlet")
        public void createNamedStoredProcedureQueryStringIllegalArgumentExceptionTest() throws java.lang.Exception {
            super.createNamedStoredProcedureQueryStringIllegalArgumentExceptionTest();
        }


}