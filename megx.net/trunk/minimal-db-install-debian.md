MegDb Installation on Debian Systems
==========

This doc is based on Debian Wheezy

# Prerequisites #

* Enable pinning to install packages from unstable 

# Required packages #

## PostgreSQL 9.3 and PostGIS ##
* apt-get -t unstable install postgresql postgresql-contrib 
* apt-get -t unstable install pgadmin3
* apt-get install postgresql-9.3-pgq3 
* apt-get install postgresql-server-dev-9.3
* apt-get -t unstable postgresql-9.3-postgis 
* apt-get -t unstable install postgresql-9.3-postgis-scripts 
  
## PostBIS ##
 
svn co https://colab.mpi-bremen.de/postbis/svn/trunk postbis
cd postbis
 
make
make install

# Installation #
 
First create a vanilla database 
 
`
createdb -E UTF8 -O renzo -T template0 -h 127.0.0.1 -U renzo megdb_2
`  
	
## Extensions ##
 
`
  create extension postgis;
`  

`
create extension topology;
`

`
create extension rtpostgis;
` 

## DB roles ##

`createuser -L megdb_admin`
 
`createuser -S -R -D megxuser`


## Restore MegDb dump ##

download newest megdb dev from https://dev-dav.megx.net/megdb/
 
This dump is based on an old version of PostGIS, therefore we need to
apply the following update procedure on the dump file:

`perl /usr/share/postgresql/9.3/contrib/postgis-2.1/postgis_restore.pl -v test-min-dev.dump | psql -h 127.0.0.1   megdb 2> error.log 
` 


TODO

* check error log and enhance source DB 
 
