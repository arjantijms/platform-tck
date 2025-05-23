/*
 * Copyright (c) 2018, 2022 Oracle and/or its affiliates. All rights reserved.
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

env.label = "jakartaee-tck-pod-${UUID.randomUUID().toString()}"

default_suites=[ "samples", "signaturetest/javaee" ] 
default_tcks=["caj", "connector", "el", "jms", "jpa", "jsp", "jstl", "jta", "saaj", "servlet",  "websocket"]

def cts_suites = params.test_suites != null ? params.test_suites.split() : default_suites
def tcks = params.standalone_tcks != null ? params.standalone_tcks.split() : default_tcks
 
def parallelCTSSuitesMap = cts_suites.collectEntries {
  ["${it}": generateCTSStage(it)]
}

def generateCTSStage(job) {
  if( job == "javamail" || job == "samples" || job == "servlet" || job == "ejb" ) {
    return {
      podTemplate(label: env.label) {
        node(label) {
          stage("${job}") {
            container('james-mail') {
             sh """
               cd /root 
               /root/startup.sh | tee /root/mailserver.log &
               sleep 120
               bash -x /root/create_users.sh 2>&1 | tee /root/create_users.log
               echo "Mail server setup complete"
             """
            }
            container('jakartaeetck-ci') {
              unstash 'jakartaeetck-bundles'
              sh """
                id && ant -version && mvn -version && ls -la ${HOME}/ && cat /etc/hosts && (env|sort)
                unzip -q -o ${WORKSPACE}/jakartaeetck-bundles/*jakartaeetck*.zip -d ${CTS_HOME}
                bash -x ${CTS_HOME}/jakartaeetck/docker/run_jakartaeetck.sh ${job} 2>&1 | tee ${CTS_HOME}/run_jakartaeetck.log
              """
              archiveArtifacts artifacts: "*-results.tar.gz,*-junitreports.tar.gz", allowEmptyArchive: true
              junit testResults: 'results/junitreports/*.xml', allowEmptyResults: true
            }
          }
        }
      }
    }
  } else {
    return {
      podTemplate(label: env.label) {
        node(label) {
          stage("${job}") {
            container('jakartaeetck-ci') {
              unstash 'jakartaeetck-bundles'
              sh """
                id && ant -version && mvn -version && ls -la ${HOME}/ && cat /etc/hosts && (env|sort)
                unzip -q -o ${WORKSPACE}/jakartaeetck-bundles/*jakartaeetck*.zip -d ${CTS_HOME}
                bash -x ${CTS_HOME}/jakartaeetck/docker/run_jakartaeetck.sh ${job} 2>&1 | tee ${CTS_HOME}/run_cts.log
              """
              archiveArtifacts artifacts: "*-results.tar.gz,*-junitreports.tar.gz", allowEmptyArchive: true
              junit testResults: 'results/junitreports/*.xml', allowEmptyResults: true
            }
          }
        }
      }
    }
  }
}
 
def parallelStandaloneTCKMap = tcks.collectEntries {
  ["${it}": generateStandaloneTCKStage(it)]
}
 
def generateStandaloneTCKStage(job) {
  return {
    podTemplate(label: env.label) {
      node(label) {
        stage("${job}") {
          container('jakartaeetck-ci') {
            checkout scm
            unstash 'standalone-bundles'
            sh """
              id && ant -version && mvn -version && ls -la ${HOME}/ && cat /etc/hosts && (env|sort)
              bash -x ${WORKSPACE}/docker/${job}tck.sh 2>&1 | tee ${WORKSPACE}/${job}tck.log
            """
            archiveArtifacts artifacts: "*tck-results.tar.gz,*-junitreports.tar.gz,${job}tck.log",allowEmptyArchive: true
            junit testResults: 'results/junitreports/*.xml', allowEmptyResults: true
          }
        }
      }
    }
  }
}
 
pipeline {
  options {
    durabilityHint('PERFORMANCE_OPTIMIZED')
    buildDiscarder(logRotator(numToKeepStr: '15', artifactDaysToKeepStr: '15'))
    timeout(time: 15, unit: 'HOURS')
  }
  agent {
    kubernetes {
      label "${env.label}"
      defaultContainer 'jakartaeetck-ci'
      yaml """
apiVersion: v1
kind: Pod
metadata:
spec:
  hostAliases:
  - ip: "127.0.0.1"
    hostnames:
    - "localhost.localdomain"
    - "james.local"
  containers:
  - name: jnlp
    env:
      - name: JNLP_PROTOCOL_OPTS
        value: "-XshowSettings:vm -Xmx3072m -Dsun.zip.disableMemoryMapping=true -Dorg.jenkinsci.remoting.engine.JnlpProtocol3.disabled=true"
    resources:
      limits:
        memory: "4Gi"
  - name: jakartaeetck-ci
    image: jakartaee/cts-base:0.4
    command:
    - cat
    tty: true
    imagePullPolicy: Always
    resources:
      limits:
        memory: "10Gi"
        cpu: "2.0"
    securityContext:
      runAsNonRoot: true
      runAsUser: 1003270000
      runAsGroup: 0
      fsGroup: 1003270000
  - name: james-mail
    image: jakartaee/cts-mailserver:0.1
    command:
    - cat
    ports:
    - containerPort: 1025
    - containerPort: 1143
    tty: true
    imagePullPolicy: Always
    resources:
      limits:
        memory: "2Gi"
        cpu: "0.5"
"""
    }
  }
  parameters {
    string(name: 'GF_BUNDLE_URL', 
           defaultValue: 'https://download.eclipse.org/ee4j/glassfish/glassfish-7.0.0-SNAPSHOT-nightly.zip', 
           description: 'URL required for downloading GlassFish Full/Web profile bundle' )
    string(name: 'GF_VERSION_URL', 
           defaultValue: '', 
           description: 'URL required for downloading GlassFish version details' )
    string(name: 'OLD_GF_BUNDLE_URL',
           defaultValue: '', 
           description: 'URL required for downloading Old GlassFish Full/Web profile bundle' )
    string(name: 'TCK_BUNDLE_BASE_URL', 
           defaultValue: '',
           description: 'Base URL required for downloading prebuilt binary TCK Bundle from a hosted location' )
    string(name: 'TCK_BUNDLE_FILE_NAME', 
           defaultValue: 'jakartaeetck.zip',
           description: 'Name of bundle file to be appended to the base url' )
    string(name: 'STANDALONE_TCK_BUNDLES_FILE_NAME_LIST',
           defaultValue: '',
           description: 'List of standalone TCK bundle file names to be appended to the base url' )
    choice(name: 'PROFILE', choices: 'FULL\nWEB', 
           description: 'Profile to be used for running CTS either web/full' )
    choice(name: 'JDK', choices: 'JDK11\nJDK17',
           description: 'Java SE Version to be used for running TCK either JDK11/JDK17' )
    choice(name: 'LICENSE', choices: 'EPL\nEFTL',
           description: 'License file to be used to build the TCK bundle(s) either EPL(default) or Eclipse Foundation TCK License' )
    choice(name: 'AS_TRACE', choices: 'false\ntrue',
           description: 'Verbose logging of the asadmin command' )
    choice(name: 'DATABASE', choices: 'JavaDB\nOracle\nMySQL', 
           description: 'Database to be used for running CTS. Currently only JavaDB is supported.' )
    choice(name: 'BUILD_TYPE', choices: 'CTS\nSTANDALONE-TCK', 
           description: 'Run the full EE compliance testsuite or a standalone tck' )
    string(name: 'test_suites', defaultValue: 'connector ejb ejb30/bb ejb30/lite/appexception ejb30/lite/async ejb30/lite/basic ejb30/lite/ejbcontext ejb30/lite/enventry ejb30/lite/interceptor ejb30/lite/lookup ejb30/lite/naming ejb30/lite/nointerface  ejb30/lite/packaging ejb30/lite/singleton ejb30/lite/stateful ejb30/lite/tx ejb30/lite/view ejb30/lite/xmloverride ejb30/assembly ejb30/timer ejb30/webservice ejb30/zombie ejb30/misc ejb30/sec ejb32 el integration jacc javaee javamail jaxrs jdbc_appclient jdbc_ejb jdbc_jsp jdbc_servlet jms_appclient jms_ejb jms_jsp jms_servlet jpa_appmanaged jpa_appmanagedNoTx jpa_pmservlet jpa_puservlet jpa_stateful3 jpa_stateless3 jsonb jsonp jsp jstl jta jws samples servlet signaturetest/javaee webservices12 webservices13 websocket xa',
           description: 'Space separated list of Test suites to run') 
    string(name: 'standalone_tcks', defaultValue: 'caj connector el jms jpa jsp jstl jta saaj servlet websocket', 
           description: 'Space separated list of standalone TCKs to build and run') 
    string(name: 'USER_KEYWORDS',
           defaultValue: '',
           description: 'Optional keywords prefixed by joining operator - [&|] for filtering out the tests to run' )
  }
  environment {
    CTS_HOME="/home/tck"
    ANT_OPTS="-Djavax.xml.accessExternalStylesheet=all -Djavax.xml.accessExternalSchema=all -Djavax.xml.accessExternalDTD=file,http"
    MAIL_USER="user01@james.local"
    MAIL_HOST="localhost"
    LANG="en_US.UTF-8"
    DEFAULT_GF_BUNDLE_URL="https://download.eclipse.org/ee4j/glassfish/glassfish-7.0.0-SNAPSHOT-nightly.zip"
    JAVA_TOOL_OPTIONS="-Djava.awt.headless=true"
  }
  stages {
    stage('jakartaeetck-build') {
      when {
        expression {
          return params.BUILD_TYPE == 'CTS';
        }
      }
      steps {
        container('jakartaeetck-ci') {
          sh """
            id && ant -version && mvn -version && ls -la ${HOME}/ && cat /etc/hosts && (env|sort)
            bash -x ${WORKSPACE}/docker/build_jakartaeetck.sh 2>&1 | tee ${WORKSPACE}/build_jakartaeetck.log
          """
          archiveArtifacts artifacts: "jakartaeetck-bundles/*.zip,*.version,*.log", allowEmptyArchive: true
          stash includes: 'jakartaeetck-bundles/*.zip', name: 'jakartaeetck-bundles'
        }
      }
    }
 
    stage('jakartaeetck-run') {
      when {
        expression {
          return params.BUILD_TYPE == 'CTS';
        }
      }
      steps {
        script {
          parallel parallelCTSSuitesMap
        }
      }
    }
 
    stage('standalone-tck-build') {
      when {
        expression {
          return params.BUILD_TYPE == 'STANDALONE-TCK';
        }
      }
 
      steps {
        container('jakartaeetck-ci') {
          sh """
            id && ant -version && mvn -version && ls -la ${HOME}/ && cat /etc/hosts && (env|sort)
            bash -x ${WORKSPACE}/docker/build_standalone-tcks.sh ${standalone_tcks} 2>&1 | tee ${WORKSPACE}/build_standalone-tcks.log
          """
          archiveArtifacts artifacts: "standalone-bundles/*.zip,*.version,*.log", allowEmptyArchive: true
          stash includes: 'standalone-bundles/*.zip', name: 'standalone-bundles'
        }
      }
    } 
    
    stage('standalone-tck-run') {
      when {
        expression {
          return params.BUILD_TYPE == 'STANDALONE-TCK';
        }
      }
      steps {
        script {
          parallel parallelStandaloneTCKMap
        }
      }
    }
  }
}
