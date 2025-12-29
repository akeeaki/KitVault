package net.rnsqd.kitVault.commands;

import lombok.Getter;
import lombok.Setter;
import net.rnsqd.kitVault.KitVault;
import net.rnsqd.kitVault.commands.impl.kitvault.subs.HelpCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
public class CommandRouter implements TabExecutor {
    private final KitVault kitVault;
    private final HashMap<CommandInformation, AbstractCommandInstance> commands;

    public CommandRouter(KitVault kitVault) {
        this.kitVault = kitVault;
        this.commands = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0 | args[0].equalsIgnoreCase("help")) {
            getCommand(HelpCommand.class).commandInstance().execute(sender, args, label);
            return true;
        }

        final String commandName = args[0];
        final GetResult commandResult = getCommand(commandName);
        if (commandResult == null) {
            // sending help to the player
            getCommand(HelpCommand.class).commandInstance().execute(sender, args, label);
            return true;
        }

        commandResult.commandInstance().execute(sender, args, label);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) return commands();
        final String commandName = args[0];
        final GetResult commandResult = getCommand(commandName);
        if (commandResult == null)
            return List.of(String.format("Команда '%s' не найдена", commandName));

        return commandResult.commandInstance().tabComplete(sender, args, label);
    }

    private List<String> commands() {
        return new ArrayList<>() {{
            CommandRouter.this.commands.keySet().forEach(command -> {
                add(command.name());
                addAll(List.of(command.aliases()));
            });
        }};
    }

    // get by command class
    public <T> GetResult getCommand(final Class<T> clazz) {
        AtomicReference<GetResult> result = new AtomicReference<>(null);
        this.commands.forEach((key, value) -> {
            if (value.getClass().equals(clazz))
                result.set(new GetResult(key, value));
        });
        return result.get();
    }

    // get by command name
    public GetResult getCommand(final String commandName) {
        AtomicReference<GetResult> result = new AtomicReference<>(null);
        this.commands.forEach((key, value) -> {
            if (key.name().equalsIgnoreCase(commandName) || List.of(key.aliases()).contains(commandName))
                result.set(new GetResult(key, value));
        });
        return result.get();
    }

    public void register(Map<CommandInformation, AbstractCommandInstance> commands) {
        this.commands.putAll(commands);
    }

    public record GetResult(CommandInformation command, AbstractCommandInstance commandInstance) {}
}
