rem @echo off
C:
CD "\_Cm\Soen341ProjectB"
"C:\Program Files\Java\jdk1.8.0_121\bin\javac.exe" TestOne.java
rem pause
"C:\Program Files\Java\jdk1.8.0_121\bin\java.exe"  TestOne > TestOne.txt
type TestOne.txt
"C:\Program Files\Java\jdk1.8.0_121\bin\java.exe"  aunit     TestOne.txt
pause
