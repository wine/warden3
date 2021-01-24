wget "https://download2277.mediafire.com/ph3yk5h4f9tg/96mrmeo57cdf6zv/mcp811.zip" -outfile "mcp.zip"
Expand-Archive -Path mcp.zip
cd .\mcp\
.\decompile.bat
.\recompile.bat
cd .\bin
Compress-Archive -Path .\minecraft\* -DestinationPath Minecraft-deobf.zip
Rename-Item -Path .\Minecraft-deobf.zip -NewName Minecraft-deobf.jar
cd ..
cd ..

git clone https://github.com/wine/warden3
cd .\warden3\
mkdir libs
Copy-Item -Path ..\mcp\bin\Minecraft-deobf.jar .\libs\Minecraft-deobf.jar
.\gradlew.bat build