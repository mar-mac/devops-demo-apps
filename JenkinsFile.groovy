pipeline {

    agent any

    parameters {
      string(defaultValue: 'eu-west-1', description: 'AWS Region in which deploy this infrastructure',  name: 'AWS_REGION')
    }

    stages {
            stage('Checkout-EnvPreparation') {
                agent any
                steps {
                    echo 'Checkout-EnvPreparation stage: this will checkout the source code from github'

                    git branch: 'master',
                            credentialsId: 'github-marcomaccio',
                            url: 'https://github.com/mar-mac/devops-demo-apps.git'


                }
            }
            stage('Dependency-Installation') {
                agent any
                steps {

                    wrap([$class: 'AnsiColorBuildWrapper', colorMapName: 'xterm']) {
                      echo 'Dependency installation via yarn ...'
                      sh "yarn install"
                    }
                }
            }
            stage('FE-Unit-Testing') {
                agent any
                steps {

                    wrap([$class: 'AnsiColorBuildWrapper', colorMapName: 'xterm']) {
                      echo 'Launching unit tests via yarn ...'
                      sh "yarn build && yarn test"
                    }
                }
            }
            stage('FE-E2E-Testing') {
                agent any
                steps {

                    wrap([$class: 'AnsiColorBuildWrapper', colorMapName: 'xterm']) {
                      echo 'Launching unit tests via yarn ...'
                      sh "yarn build && yarn ci:e2e"
                    }
                }
            }
            stage('FE-Build-Prod-And-Deploy-AWS') {
                agent any
                steps {

                    wrap([$class: 'AnsiColorBuildWrapper', colorMapName: 'xterm']) {
                      echo 'Launching unit tests via yarn ...'
                      sh "yarn deploy"
                    }
                }
            }
    }

}
