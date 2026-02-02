package com.blockedmcservers;

import com.blockedmcservers.config.ServerUnblockerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUnblocker implements ClientModInitializer {

	public static final String MOD_ID = "serverunblocker";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final BlockCheck isAllowedCheck = new BlockCheck();

	public static ServerUnblockerConfig config;

	@Override
	public void onInitializeClient() {
		// Load Config
		AutoConfig.register(ServerUnblockerConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ServerUnblockerConfig.class).getConfig();

		// Logs
		LOGGER.info("Initialised");
	}
}