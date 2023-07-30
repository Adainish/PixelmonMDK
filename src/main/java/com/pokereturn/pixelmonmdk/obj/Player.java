package com.pokereturn.pixelmonmdk.obj;

import com.pokereturn.pixelmonmdk.PixelmonMDK;
import com.pokereturn.pixelmonmdk.storage.PlayerStorage;
import com.pokereturn.pixelmonmdk.util.PermissionUtil;
import com.pokereturn.pixelmonmdk.util.Util;
import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public class Player
{
    public UUID uuid;
    public String userName;

    public Player(UUID uuid)
    {
        this.uuid = uuid;
    }

    public Player(UUID uuid, String userName)
    {
        this.uuid = uuid;
        this.userName = userName;
    }

    public void sendMessage(String msg)
    {
        if (msg == null)
            return;
        if (msg.isEmpty())
            return;
        Util.send(uuid, msg);
    }

    public String getUsername()
    {
        if (this.userName != null)
            return userName;
        return "";
    }

    public void setUsername(String name)
    {
        this.userName = name;
    }

    public void save()
    {
        PlayerStorage.savePlayer(this);
    }

    public void updateCache()
    {
        PixelmonMDK.playerCache.playerCache.put(uuid, this);
    }
    @Nullable
    public ServerPlayerEntity serverPlayer()
    {
        return Util.getPlayer(this.uuid);
    }

    public boolean hasPermission(String permission)
    {
        return PermissionUtil.checkPerm(serverPlayer(), permission);
    }
}
