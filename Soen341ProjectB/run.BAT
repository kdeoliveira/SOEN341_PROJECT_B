@echo Compiling Programs ...
@echo off
C:
CD "\_Cm\Soen341ProjectB"
"C:\Program Files\Java\jdk1.8.0_121\bin\javac.exe" TestOne.java
"C:\Program Files\Java\jdk1.8.0_121\bin\javac.exe" TestTwo.java
@echo Running Programs ...
@if exist Tests.txt                                       del Tests.txt
"C:\Program Files\Java\jdk1.8.0_121\bin\java.exe"  TestOne >  Tests.txt
"C:\Program Files\Java\jdk1.8.0_121\bin\java.exe"  TestTwo >> Tests.txt
"C:\Program Files\Java\jdk1.8.0_121\bin\java.exe"  TestOne >> Tests.txt
type Tests.txt
@echo on
@echo Checking test result...
"C:\Program Files\Java\jdk1.8.0_121\bin\java.exe"  aunit Tests.txt
pause
