REM based on http://www.iulidragos.org/?p=207 
REM and http://software.2206966.n2.nabble.com/P2-transparent-mirroring-td5793062.html

SET ECLIPSE=E:\progs\eclipse\
SET SOURCE=http://download.eclipse.org/releases/indigo
SET LOCAL_SITE=E:\dev\eclipse-repo-mirror
SET SITE_NAME=my-indigo-repository


@ECHO off
echo Mirroring metadata for %SOURCE%
java -jar %ECLIPSE%\plugins\org.eclipse.equinox.launcher_*.jar ^
     -application org.eclipse.equinox.p2.metadata.repository.mirrorApplication ^
     -source %SOURCE% ^
     -destination %LOCAL_SITE% ^
     -destinationName "%SITE_NAME%" ^
     -verbose -ignoreErrors

echo Mirroring artifacts for %SOURCE%
java -jar %ECLIPSE%\plugins\org.eclipse.equinox.launcher_*.jar ^
     -application org.eclipse.equinox.p2.artifact.repository.mirrorApplication ^
     -source %SOURCE% ^
     -destination %LOCAL_SITE% ^
     -destinationName "%SITE_NAME%" ^
     -verbose -ignoreErrors