package fr.amazonia.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class VersionCommand extends AbstractCommand {
    private final String message;

    public VersionCommand(String name, String description, String message) {
        super(name, description, Boolean.parseBoolean(message));
        this.message = message;
    }

    @Nullable
    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        context.sendMessage(Message.raw(this.message));
        return CompletableFuture.completedFuture(null);
    }

}
