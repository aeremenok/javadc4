;JavaDC 3 installer

;--------------------------------
;Include Modern UI

  !include "MUI.nsh"


;--------------------------------
;Include Java Runtime environment or not

#  !define JAVA_HOME "%JAVA_HOME%"
#  !define JAVA_HOME "D:\Java\jdk\jdk1.5.0_06"

#  !define JAVA_HOME "D:\Java\jdk\jdk1.5.0_06"

#  !define INCLUDE_JRE

!echo "JAVA_HOME : $%JAVA_HOME%"
!echo "1 : $1 , 2 : $2"
!echo "INCLUDE_JRE : $INCLUDE_JRE"

!define JAVA_HOME $%JAVA_HOME%

#!if $2 == "-include_jre"
#!define INCLUDE_JRE
#!else
##!undef INCLUDE_JRE
#!endif

;--------------------------------
;General

  ;Name and file
  Name "P2P Java Direct Connect 3"

!ifdef INCLUDE_JRE
  OutFile "..\..\..\build\JavaDC3-jre-included-${__DATE__}.exe"
!else
  OutFile "..\..\..\build\JavaDC3-no-jre-${__DATE__}.exe"
!endif

  ;Default installation folder
  InstallDir "$PROGRAMFILES\JavaDC3"
  
  ;Get installation folder from registry if available
  InstallDirRegKey HKCU "Software\JavaDC3" ""

;--------------------------------
;Constants

  !define SRC_DIR "..\..\.."

;--------------------------------
;Variables

  Var MUI_TEMP
  Var STARTMENU_FOLDER


;--------------------------------
;Interface Settings

  !define MUI_ABORTWARNING

;--------------------------------
;Pages

  !insertmacro MUI_PAGE_LICENSE "${SRC_DIR}\licenses\gpl_license.txt"
#  !insertmacro MUI_PAGE_LICENSE "..\..\..\licenses\gpl_license.txt"
  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY
  
  ;Start Menu Folder Page Configuration
  !define MUI_STARTMENUPAGE_REGISTRY_ROOT "HKCU" 
  !define MUI_STARTMENUPAGE_REGISTRY_KEY "Software\JavaDC3" 
  !define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "Start Menu Folder"
  
  !insertmacro MUI_PAGE_STARTMENU Application $STARTMENU_FOLDER
  
  !insertmacro MUI_PAGE_INSTFILES
  
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES

;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"


;--------------------------------
;Functions 

Function .onInit
#  StrCmp $INSTDIR "" 0 Continue  ; IF installation directory string from reg key = null
#    MessageBox MB_OK "Tomcat 4.1 is not installed on this system. Installation aborted."
#    Abort
#  Continue:
#  StrCpy $INSTDIR "$INSTDIR\webapps" ; Point installation to the webapps subdirectory

FunctionEnd


;--------------------------------
;Installer Sections


;--------------------------------
;Core JavaDC3 section
;--------------------------------

Section "JavaDC3 Core" SectionCore

  
  SetOutPath "$INSTDIR"
  
  ;ADD YOUR OWN FILES HERE...
  SetOutPath $INSTDIR\bin
  File "${SRC_DIR}\systray4j.dll"
  File "${SRC_DIR}\installers\windows\conf\javadc.bat"
  File "${SRC_DIR}\installers\windows\conf\JavaDC3.ini"


  SetOutPath $INSTDIR\lib
  File "${SRC_DIR}\lib\commons-collections.jar"
  File "${SRC_DIR}\lib\commons-pool-1.2.jar"
  File "${SRC_DIR}\lib\cryptix-jce-provider.jar"
  File "${SRC_DIR}\lib\junit.jar"
  File "${SRC_DIR}\lib\kunststoff.jar"
  File "${SRC_DIR}\lib\log4j-1.2.8.jar"
  File "${SRC_DIR}\lib\looks-1.2.2.jar"
  File "${SRC_DIR}\lib\picocontainer-1.0-beta-5.jar"
  File "${SRC_DIR}\lib\spin.jar"
  File "${SRC_DIR}\lib\systray4j.jar"
  File "${SRC_DIR}\lib\tigertree-1.0.jar"

  File "${SRC_DIR}\build\javadc3.jar"

  SetOutPath $INSTDIR\conf
  File "${SRC_DIR}\queue.xml"
  File "${SRC_DIR}\queue_test.xml"


  SetOutPath $INSTDIR\license
  File "${SRC_DIR}\licenses\gpl_license.txt"
  File "${SRC_DIR}\licenses\lesser_license.txt"
  File "${SRC_DIR}\licenses\log4jcore_license.txt"
  File "${SRC_DIR}\licenses\picocontainer_license.txt"



  SetOutPath $INSTDIR
#  File ..\Docs\NSISdl\ReadMe.txt

  
  ;Store installation folder
  WriteRegStr HKCU "Software\JavaDC3" "" $INSTDIR
  
  ;Create uninstaller
  WriteUninstaller "$INSTDIR\Uninstall.exe"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "DisplayName" "JavaDC3"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "UninstallString" "$INSTDIR\Uninstall.exe"
#  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "DisplayIcon" "${INSTDIR}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "Publisher" "GNU Software"
#  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "ModifyPath" "GNU Software"
#  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "InstallSource" "${INSTDIR}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "ProductID" "JavaDC3"
#  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "HelpLink" "http://westkamper.freezope.org/javadc3"
#  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "HelpTelephone" "${INSTDIR}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "URLUpdateInfo" "http://sourceforge.net/project/showfiles.php?group_id=105048"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "URLInfoAbout" "http://westkamper.freezope.org/javadc3"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "DisplayVersion" "0.0.0"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "VersionMajor" "0"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "VersionMinor" "0.0"
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3" "NoRepair" 1
  
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    
    ;Create shortcuts
    CreateDirectory "$SMPROGRAMS\$STARTMENU_FOLDER"
    CreateShortCut  "$SMPROGRAMS\$STARTMENU_FOLDER\JavaDC3.lnk" "$INSTDIR\bin\JavaDC3.exe" ""

    WriteINIStr "$SMPROGRAMS\$STARTMENU_FOLDER\JavaDC3 Site.url" "InternetShortcut" "URL" "http://sourceforge.net/projects/javadc3/"
    WriteINIStr "$SMPROGRAMS\$STARTMENU_FOLDER\Bug list.url" "InternetShortcut" "URL" "http://sourceforge.net/tracker/?group_id=105048&atid=640110"
    WriteINIStr "$SMPROGRAMS\$STARTMENU_FOLDER\Submit a feature.url" "InternetShortcut" "URL" "http://sourceforge.net/tracker/?group_id=105048&atid=640113"

    CreateShortCut  "$SMPROGRAMS\$STARTMENU_FOLDER\Uninstall.lnk" "$INSTDIR\Uninstall.exe"
  
  !insertmacro MUI_STARTMENU_WRITE_END

SectionEnd


;--------------------------------
; Java Runtime environment section
;--------------------------------

  !ifdef INCLUDE_JRE


Section "Java Runtime Environment" SectionJre

#  Var /GLOBAL JAVA_HOME
#  ExpandEnvStrings $JAVA_HOME "%JAVA_HOME%"
#  ExecShell "print" "$JAVA_HOME"

  SetOutPath $INSTDIR\jre
  File "${JAVA_HOME}\jre\copyright"
  File "${JAVA_HOME}\jre\LICENSE"
  File "${JAVA_HOME}\jre\README.txt"
  File "${JAVA_HOME}\jre\THIRDPARTYLICENSEREADME.txt"

  SetOutPath $INSTDIR\jre\bin
  File "${JAVA_HOME}\jre\bin\*.*"

  SetOutPath $INSTDIR\jre\bin\client
  File "${JAVA_HOME}\jre\bin\client\jvm.dll"
  File "${JAVA_HOME}\jre\bin\client\Xusage.txt"

  SetOutPath $INSTDIR\jre\lib
  File /r "${JAVA_HOME}\jre\lib\*.*"


  WriteRegStr HKCU "Software\JavaDC3" "" $INSTDIR

SectionEnd

  !endif ; INCLUDE_JRE

;--------------------------------
;Descriptions

  ;Language strings
  LangString DESCRIPTION_SectionCore ${LANG_ENGLISH} "JavaDC3 core"
  LangString DESCRIPTION_SectionJre ${LANG_ENGLISH}  "Java Runtime environment"

  ;Assign language strings to sections
  !insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
    !insertmacro MUI_DESCRIPTION_TEXT ${SectionCore} $(DESCRIPTION_SectionCore)
    !ifdef INCLUDE_JRE
      !insertmacro MUI_DESCRIPTION_TEXT ${SectionJre} $(DESCRIPTION_SectionJre)
    !endif ; INCLUDE_JRE
  !insertmacro MUI_FUNCTION_DESCRIPTION_END
 
;--------------------------------
;Uninstaller Section

Section "Uninstall"

  ;ADD YOUR OWN FILES HERE...

  Delete "$INSTDIR\Uninstall.exe"
#  Delete "$INSTDIR\bin\*.*"
#  Delete "$INSTDIR\lib\*.*"
#  Delete "$INSTDIR\*.*"
#  Delete "$INSTDIR\**\*.*"
  RMDir  /r "$INSTDIR\bin"
  RMDir  /r "$INSTDIR\conf"
  RMDir  /r "$INSTDIR\jre"
  RMDir  /r "$INSTDIR\lib"
  RMDir  /r "$INSTDIR\license"
  RMDir  "$INSTDIR"

  !insertmacro MUI_STARTMENU_GETFOLDER Application $MUI_TEMP
    
  Delete "$SMPROGRAMS\$MUI_TEMP\Uninstall.lnk"

  Delete "$SMPROGRAMS\$MUI_TEMP\*.*"
#  Delete "$SMPROGRAMS\$MUI_TEMP"
  RMDir "$SMPROGRAMS\$MUI_TEMP"
  
  ;Delete empty start menu parent diretories
  StrCpy $MUI_TEMP "$SMPROGRAMS\$MUI_TEMP"
 
  startMenuDeleteLoop:
	ClearErrors
    RMDir $MUI_TEMP
    GetFullPathName $MUI_TEMP "$MUI_TEMP\.."
    
    IfErrors startMenuDeleteLoopDone
  
    StrCmp $MUI_TEMP $SMPROGRAMS startMenuDeleteLoopDone startMenuDeleteLoop
  startMenuDeleteLoopDone:

  DeleteRegKey HKCU "Software\JavaDC3"
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JavaDC3"

SectionEnd
