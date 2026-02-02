package com.blockedmcservers;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUnblocker implements ClientModInitializer {

	public static final String MOD_ID = "serverunblocker";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final IsAllowedCheck isAllowedCheck = new IsAllowedCheck();

	@Override
	public void onInitializeClient() {
		AutoConfig.register(ServerUnblockerConfig.class, GsonConfigSerializer::new);

		LOGGER.info("Initialised");
	}
}