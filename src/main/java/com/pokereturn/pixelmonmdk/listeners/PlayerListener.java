package com.pokereturn.pixelmonmdk.listeners;

import com.pokereturn.pixelmonmdk.obj.Player;
import com.pokereturn.pixelmonmdk.storage.PlayerStorage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerListener
{
    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getPlayer() != null) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
            Player player = PlayerStorage.getPlayer(serverPlayer.getUniqueID());
            if (player == null) {
                PlayerStorage.makePlayer(serverPlayer.getUniqueID());
                player = PlayerStorage.getPlayer(serverPlayer.getUniqueID());

            }

            if (player != null) {
                player.setUsername(serverPlayer.getName().getString());
                player.updateCache();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
    {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
        if (serverPlayer != null) {
            Player player = PlayerStorage.getPlayer(serverPlayer.getUniqueID());
            if (player != null) {
                player.save();
            }
        }
    }
}
