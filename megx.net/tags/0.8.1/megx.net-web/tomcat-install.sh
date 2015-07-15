#!/bin/bash

##
# Installs and configures tomcat for running megx.net
#
##
declare -r TOMCAT_VER=7.0.54 #change to most recent

INSTALL_DIR=

function main() {

  INSTALL_DIR=${1}
  wget --header 'Host: mirror.netcologne.de' "http://mirror.netcologne.de/apache.org/tomcat/tomcat-7/v${TOMCAT_VER}/bin/apache-tomcat-${TOMCAT_VER}.tar.gz" -O "apache-tomcat-${TOMCAT_VER}.tar.gz"
  tar xzvf apache-tomcat-${TOMCAT_VER}.tar.gz -C ${INSTALL_DIR}
  #optional
  ln -s ${INSTALL_DIR}/apache-tomcat-${TOMCAT_VER} ${INSTALL_DIR}/tomcat-7
}

main $@