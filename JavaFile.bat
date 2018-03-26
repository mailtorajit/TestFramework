@echo off
SETLOCAL
FOR /F "usebackq delims=" %%S IN (./src/main/resources/application.properties) DO (
 echo %%S 
 )
FOR /F "eol=; tokens=2,2 delims==" %%i IN (./src/main/resources/application.properties) DO (
 echo %%i
 )

For /F "tokens=1* delims==" %%A IN (./src/main/resources/application.properties) DO (
	echo %%A
	IF "%%A"=="browser.url" set browser.url="test this"
	IF "%%A"=="browser.test" set browser.test="test that" 
	)
	
	
(
FOR /f "usebackqdelims=" %%a IN (./src/main/resources/application.properties) DO (
FOR /f "tokens=1*delims==" %%g IN ("%%a") DO (
IF "%%g"=="browser.name" (ECHO(%%g=%~1
) ELSE (IF "%%g"=="browser.url" (ECHO(%%g=%~2
) ELSE (ECHO(%%a)
)
)
)
)>temp1.properties

COPY temp1.properties .\src\main\resources\application.properties
del temp1.properties