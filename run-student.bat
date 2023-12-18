REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET ECAFETERIA_CP=ecourse.app.student.console\target\ecourse.app.student.console-1.4.0-SNAPSHOT.jar;ecourse.app.student.console\target\dependency\*;

REM call the java VM, e.g,
java -cp %ECAFETERIA_CP% ecourse.base.app.user.console.StudentApp