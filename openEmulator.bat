@echo off
echo param[1] = %1
cd  %ANDROID_HOME%\emulator
emulator -avd %1
pause