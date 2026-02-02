package com.blockedmcservers.config;

import com.blockedmcservers.ServerUnblocker;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = ServerUnblocker.MOD_ID)
public class ServerUnblockerConfig implements ConfigData {
    @ConfigEntry.Gui.PrefixText
    public boolean icon = true;
    @ConfigEntry.Gui.PrefixText
    public boolean telemetry = true;
}