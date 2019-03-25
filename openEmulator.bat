@echo off
echo param[1] = %1
%1 = Nexus_5X_API_28_3
cd  %ANDROID_HOME%\emulator
emulator -avd %1
pause