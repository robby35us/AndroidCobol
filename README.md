# COBOLT

OVERVIEW

The project idea is to develop a COBOL IDE that runs on the Android framework: the COBOLT app. The app should allow a developer to:

  Write a cobol program using an editor.
  Compile the program.
  Run the program using the android file system and an interactive display.
  The project is to be open source and, hopefully, involve other interested developers.

GOALS
  To practice organizing and developing a large project.
  To expand my knowledge and capabilities as an Android developer.
  To explore the inner workings of  the COBOL programming language.

SPECIFICATIONS
  The general idea of how to structure the project is to start with a central Controller that starts and stops Android services that perform each of the various functions of the app. The controller would not have a major UI component. The app would require at least the following services:

  Menu - Foreground
  Editor - Foreground
  Compiler - Background
  Linker - Background
  Program Executor - Background
  Interactive console - Foreground

SERVICES
  Editor
  Compiler
  Compiler Output Viewer
  Source Code Parser
  Syntax color highlighting
  File manager
  Build manager
  Source Control
  Debugger
  Database Manager
