package com.blockedmcservers;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = ServerUnblocker.MOD_ID)
class ServerUnblockerConfig implements ConfigData {
    @ConfigEntry.Gui.PrefixText
    boolean icon = true;
    @ConfigEntry.Gui.PrefixText
    boolean telemetry = true;
}