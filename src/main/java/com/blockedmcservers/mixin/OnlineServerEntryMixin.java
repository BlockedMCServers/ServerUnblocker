package com.blockedmcservers.mixin;

import com.blockedmcservers.ServerUnblocker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SelectableEntry;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerSelectionList.OnlineServerEntry.class)
public abstract class OnlineServerEntryMixin extends ServerSelectionList.Entry implements SelectableEntry {

    @Shadow
    @Final
    private ServerData serverData;

    @Unique
    private boolean serverBlocked;

    @Unique
    private static final Identifier BLOCKED_SPRITE = Identifier.withDefaultNamespace("textures/item/barrier.png");

    @Inject(method = "refreshStatus", at = @At(value = "HEAD"))
    public void serverunblocker$checkBlock(CallbackInfo ci) {
        this.serverBlocked = ServerUnblocker.isAllowedCheck.isBlocked(serverData);
    }

    @Inject(method = "renderContent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/multiplayer/ServerSelectionList$OnlineServerEntry;drawIcon(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/resources/Identifier;)V"))
    public void serverunblocker$showBlock(GuiGraphics guiGraphics, int i, int j, boolean bl, float f, CallbackInfo ci) {
        if(this.serverBlocked) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BLOCKED_SPRITE, this.getContentRight() - 24, this.getContentY() + 10, 0.0F, 0.0F, 16, 16, 16, 16);
        }
    }

}
