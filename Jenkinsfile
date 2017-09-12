#!/usr/bin/env groovy

pipeline {
 agent none
 stages {
  stage('Build and Deploy') {
   agent any
   steps {
    script {
     checkout scm
     sh('./mvnw -s .mvn/settings.xml clean install docker:push')
    }
   }
  }
 }
}