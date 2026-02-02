package com.blockedmcservers.mixin;

import com.blockedmcservers.ServerUnblocker;
import net.minecraft.client.multiplayer.resolver.AddressCheck;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerNameResolver.class)
public class ServerNameResolverMixin {

    @Redirect(method = "resolveAddress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/resolver/AddressCheck;isAllowed(Lnet/minecraft/client/multiplayer/resolver/ServerAddress;)Z"))
    private static boolean serverunblocker$createFromService(AddressCheck instance, ServerAddress serverAddress) {
        return true;
    }

    @Redirect(method = "resolveAddress", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/resolver/AddressCheck;isAllowed(Lnet/minecraft/client/multiplayer/resolver/ResolvedServerAddress;)Z"))
    private static boolean serverunblocker$createFromService(AddressCheck instance, ResolvedServerAddress resolvedServerAddress) {
        String hostName = resolvedServerAddress.getHostName();
        String hostIp = resolvedServerAddress.getHostIp();
        ServerUnblocker.LOGGER.info("Handled a server check for {} ({})", hostName, hostIp);
        return true;
    }
}