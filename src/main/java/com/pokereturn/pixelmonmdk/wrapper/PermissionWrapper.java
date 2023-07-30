package com.pokereturn.pixelmonmdk.wrapper;

import com.pokereturn.pixelmonmdk.PixelmonMDK;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.Level;

public class PermissionWrapper {
    public static String userPermission = "pixelmonmdk.user.base";
    public static String adminPermission = "pixelmonmdk.admin";
    public void registerPermissions() {
        registerCommandPermission(userPermission, "The base permission players need to use it");
        registerCommandPermission(adminPermission, "The admin permission");
    }
    public static void registerCommandPermission(String s) {
        if (s == null || s.isEmpty()) {
            return;
        }
        PermissionAPI.registerNode(s, DefaultPermissionLevel.NONE, s);
    }

    public static void registerCommandPermission(String s, String description) {
        if (s == null || s.isEmpty()) {
            return;
        }
        PermissionAPI.registerNode(s, DefaultPermissionLevel.NONE, description);
    }
}
