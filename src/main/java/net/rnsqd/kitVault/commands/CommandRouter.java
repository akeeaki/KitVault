package net.rnsqd.kitVault.commands;

import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.commands.impl.HelpCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public final class CommandRouter implements TabExecutor {
    private final KitVault kitVault;
    private final HashMap<CommandInformation, CommandInstance> commands;

    public CommandRouter(KitVault kitVault) {
        this.kitVault = kitVault;
        this.commands = new HashMap<>() {{
            putAll(
                    Map.of(
                            // Help command
                            HelpCommand.class.getAnnotation(CommandInformation.class),
                            new HelpCommand(CommandRouter.this)
                    )
            );
        }};
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {


        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
