package com.blockedmcservers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.mojang.blocklist.BlockListSupplier;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddressResolver;

import java.util.*;
import java.util.function.Predicate;

public class BlockCheck {

    private final List<String> blockedServers = new ArrayList<>();
    private final ImmutableList<Predicate<String>> immutableList = Streams.stream(ServiceLoader.load(BlockListSupplier.class)).map(BlockListSupplier::createBlockList).filter(Objects::nonNull).collect(ImmutableList.toImmutableList());

    /**
     * Check if a server is blocked via ResolvedServerAddress
     * @param resolvedServerAddress
     */
    public boolean isBlocked(ResolvedServerAddress resolvedServerAddress) {
        String hostName = resolvedServerAddress.getHostName();
        String hostIp = resolvedServerAddress.getHostIp();

        boolean blocked = immutableList.stream().anyMatch((predicate) -> predicate.test(hostName) || predicate.test(hostIp));

        if(blocked) {
            this.postServerBlock(hostName, hostIp);
        }

        return blocked;
    }

    /**
     * Check if a server is blocked via ServerAddress
     * @param serverAddress
     */
    public boolean isBlocked(ServerAddress serverAddress) {
        String hostName = serverAddress.getHost();
        boolean blocked = immutableList.stream().anyMatch((predicate) -> predicate.test(hostName));

        if(blocked) {
            this.postServerBlock(hostName, null);
        }

        return blocked;
    }

    /**
     * Check is a server is actually in the block list.
     * @param serverData
     * @return
     */
    public boolean isBlocked(ServerData serverData) {
        ServerAddress serverAddress = ServerAddress.parseString(serverData.ip);
        Optional<ResolvedServerAddress> optional = ServerAddressResolver.SYSTEM.resolve(serverAddress);
        return ((optional.isEmpty() || this.isBlocked(optional.get())) && this.isBlocked(serverAddress));
    }

    /**
     * Send a server block to the API if allowed
     * @param hostName
     * @param hostIp
     */
    private void postServerBlock(String hostName, String hostIp) {
        // Respect user privacy
        if(!ServerUnblocker.config.telemetry) return;

        // We don't want to flood our system
        if(blockedServers.contains(hostName)) return;

        ServerUnblocker.LOGGER.info("Blocked server found, sending to our API, thank you!");

        blockedServers.add(hostName);
    }

}
