pipeline {
    agent any
    tools {
        //Install the maven version
        maven "3.6.3"
    }
    stages {
        stage('Source'){
            //Run maven on Unix agent
            steps
            {
               git branch :'master' ,url:'https://github.com/AlKoubra/group6.git'
            }
    }
      stage('Build'){
                //Run maven on Unix agent
                steps
                {
                   sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
                }
        }

         stage('SonarQube Analysis'){
                        //Run maven on Unix agent
                        steps
                        {

                             sh 'mvn sonar:sonar'

                        }
                }
           stage('Approve Deployement')
           {
                                 //Run maven on Unix agent
                                   input{
                                     message 'Do you want do proceed for Deployement'
                                   }
                                    steps
                                    {
                                       sh 'echo "Deployement into Server  "'
                                    }


                         }

}

}