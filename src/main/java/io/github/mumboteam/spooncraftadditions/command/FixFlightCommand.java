package io.github.mumboteam.spooncraftadditions.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

public class FixFlightCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("fixflight").then(CommandManager.argument("player", GameProfileArgumentType.gameProfile()).executes(context -> {
            Collection<PlayerConfigEntry> profiles = GameProfileArgumentType.getProfileArgument(context, "player");

            for (PlayerConfigEntry profile : profiles) {
                ServerPlayerEntity player = context.getSource()
                        .getServer()
                        .getPlayerManager()
                        .getPlayer(profile.id());

                if (player == null) continue; // player offline

                String cmd = String.format(
                        "execute as %s at @s run spreadplayers ~ ~ 0 1 false @s",
                        player.getName().getString()
                );

                context.getSource()
                        .getServer()
                        .getCommandManager()
                        .executeWithPrefix(
                                context.getSource(),
                                cmd
                        );
            }

            return 1;
        })));
    }
}
