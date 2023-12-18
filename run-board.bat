REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET ECAFETERIA_CP=ecourse.app.board.console\target\ecourse.app.board.console-1.4.0-SNAPSHOT.jar;ecourse.app.board.console\target\dependency\*;

REM call the java VM, e.g,
java -cp %ECAFETERIA_CP% ecourse.base.app.backoffice.console.BoardApp