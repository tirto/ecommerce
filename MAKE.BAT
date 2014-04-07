call shutdown

if errorlevel 0 call javac -d ..\classes *.java

if errorlevel 0 call startup