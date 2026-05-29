package vao211.somethingstweak;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import vao211.somethingstweak.config.SomethingsTweakConfig;
import vao211.somethingstweak.scheduler.TickHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Somethingstweak implements ModInitializer {
    public static final String MOD_ID = "somethingstweak";

    @Override
    public void onInitialize() {
        Path configDir = FabricLoader.getInstance().getConfigDir().resolve("somethingsTweak");

        try {
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
        } catch (IOException ignored) {
        }

        MidnightConfig.init("somethingsTweak/tweak", SomethingsTweakConfig.class);

        ServerTickEvents.END_SERVER_TICK.register(TickHandler::onEndTick);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("somethingstweak")
                    .requires(source -> source.hasPermissionLevel(2)) // Chỉ OP mới dùng được
                    .then(CommandManager.literal("reload")
                            .executes(context -> {
                                SomethingsTweakConfig.clearOldData();
                                TickHandler.reloadSystem();
                                MidnightConfig.init("somethingsTweak/tweak", SomethingsTweakConfig.class);
                                context.getSource().sendFeedback(() -> Text.literal("§a[SomethingsTweak] Reloaded!"), false);
                                return 1;
                            })
                    )
            );
        });
    }
}
