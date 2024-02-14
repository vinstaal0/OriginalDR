<p align="center"> 
<img src="http://i.imgur.com/HSCayEW.png">
</p>
<p align="center">
<b>DungeonRealms Official Source Code (Era #1)</b>
</p>

This repository is me working on improving the older Minecraft Dungeonrealms code. It is currently built for Minecraft 1.8

Originally, when this was pushed, each mechanic was a seperate plugin, a couple dozen in total.  
Vilsol cleaned up parts of this code drastically, and combined it into one single plugin.

This code is still extremely bad. There's no getting around that.  
It demands respect that it was used for so long, and that it was the code that sustained thousands of players in its peak.  

## Build Instructions (How To Use)
This assumes you are using IntelliJ IDEA.
1. Clone this from git. File > New > Project From Version Control > Github.
2. Mark src/main/java as the source root.
3. Mark src/main/resources as the resources root.
4. File > Project Settings > Libraries Add all of the jars in libs.
5. Create an artifact that exports the entry: "DROriginal Compile Output".
6. You can now create the plugin with Build > Build Artifacts... > OriginalDR > Build.

## How to start a server
1. Copy the compiled jar plus all the following plugins in the plugins folder
2. Copy the corresponding config files in the following folders or create your own
3. Start a Mysql server and create a database named Dungeonrealms with user and password Dungeonrealms

## This is an unofficial DungeonRealms code release:
 - We want the players to have an accurate history of DungeonRealms, and how the game used to work.
 - Minecraft is a platform for tinkering. We want to let players tinker with DungeonRealms, make their own ideas, learn to code, and potentially even become future DungeonRealms developers.

## Licensing
The original version of this code has been released under the Attribution-NonCommercial-ShareAlike 4.0 International license I am releasing it under the same license.  
This means you may use this code for anything except commercial purposes, such as <b>for-profit</b> practice servers.

© DungeonRealms 2018 All rights reserved
