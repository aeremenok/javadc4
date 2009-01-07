@echo off

windows\tools\nsis\makensis.exe /DINCLUDE_JRE=true windows\conf\NSISInstallerConfiguration.nsi  >..\build\log_jre.txt

windows\tools\nsis\makensis.exe windows\conf\NSISInstallerConfiguration.nsi               >..\build\log_no_jre.txt
