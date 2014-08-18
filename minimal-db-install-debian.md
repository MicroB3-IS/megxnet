MegDb Installation on Debian Systems
==========

This doc is based on Debian Wheezy

# Prerequisites #

* Enable pinning to install packages from unstable 

# Required packages #

* apt-get -t unstable install postgresql postgresql-contrib 
* apt-get -t unstable install pgadmin3
* apt-get install postgresql-9.3-pgq3 
* apt-get install postgresql-server-dev-9.3
* apt-get -t unstable postgresql-9.3-postgis 
* apt-get -t unstable install postgresql-9.3-postgis-scripts 
  
 
 svn co https://colab.mpi-bremen.de/postbis/svn/trunk postbis
 cd postbis
 
 make
 make install

 createdb -E UTF8 -O renzo -T template0 -h 127.0.0.1 -U renzo megdb_2
  psql megdb_2
 
 create extension postgis;
 
 # vanilla template db maybe template1
 
 # create extension postbis, postgis, plpgsql, pgq??

download megdb dev from https://dev-dav.megx.net/megdb/
 
TODO need to add user to DB

createuser -L megdb_admin
 
createuser -S -R -D megxuser
 
 perl /usr/share/postgresql/9.3/contrib/postgis-2.1/postgis_restore.pl -v test-min-dev.dump | psql -h 127.0.0.1   megdb 2> error.log 
 
  
 
