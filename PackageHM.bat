ECHO Recompiling Hawk's Machinery...
runtime\bin\python\python_mcp runtime\recompile.py %*
ECHO Reobfuscating Hawk's Machinery...
runtime\bin\python\python_mcp runtime\reobfuscate.py %*
ECHO Done!
PAUSE