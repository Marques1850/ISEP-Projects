REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET ECAFETERIA_CP=ecourse.app.bootstrap\target\ecourse.app.bootstrap-1.4.0-SNAPSHOT.jar;ecourse.app.bootstrap\target\dependency\*;

REM call the java VM, e.g, 
java -cp %ECAFETERIA_CP% ecourse.base.app.bootstrap.BaseBootstrap