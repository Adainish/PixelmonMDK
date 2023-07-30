package com.pokereturn.pixelmonmdk.command;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pokereturn.pixelmonmdk.util.PermissionUtil;
import com.pokereturn.pixelmonmdk.util.Util;
import com.pokereturn.pixelmonmdk.wrapper.PermissionWrapper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class Command
{
    public static LiteralArgumentBuilder<CommandSource> getCommand()
    {
        return Commands.literal("pixelmonmdk")
                .requires(cs -> PermissionUtil.checkPermAsPlayer(cs, PermissionWrapper.userPermission))
                .executes(cc -> {
                    try {
                        Util.send(cc.getSource(), "&cLook! A command!");
                    } catch (Exception e) {
                        cc.getSource().sendFeedback(new StringTextComponent("Something went wrong while executing the command, please contact a member of Staff if this issue persists"), true);
                        return 1;
                    }
                    return 1;
                });
    }
}
