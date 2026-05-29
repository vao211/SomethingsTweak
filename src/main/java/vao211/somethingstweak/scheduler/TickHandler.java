package vao211.somethingstweak.scheduler;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import vao211.somethingstweak.config.SomethingsTweakConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TickHandler {
    private static final Map<String, Integer> tickCounters = new HashMap<>();
    private static final Set<String> erroredTasks = new HashSet<>();

    public static void onEndTick(MinecraftServer server) {
        if (SomethingsTweakConfig.tasks == null || SomethingsTweakConfig.tasks.isEmpty()) return;

        for (Map.Entry<String, SomethingsTweakConfig.CommandTask> entry : SomethingsTweakConfig.tasks.entrySet()) {
            String taskName = entry.getKey();
            SomethingsTweakConfig.CommandTask task = entry.getValue();
            if (!task.isValid()) {
                if (!erroredTasks.contains(taskName)) {
                    System.err.println("[SomethingsTweak] TASK REJECTED: Task '" + taskName + "' is missing information or has incorrect configuration in tweak.json!");                    erroredTasks.add(taskName);
                }
                continue;
            }

            if (erroredTasks.contains(taskName)) {
                System.out.println("[SomethingsTweak] Task '" + taskName + "' is valid and has been added to the system.");                erroredTasks.remove(taskName);
            }

            int currentTicks = tickCounters.getOrDefault(taskName, 0) + 1;
            int freqTicks = task.Freq * 20;
            int notifTicks = task.NotifBeforeRun > 0 ? (task.Freq - task.NotifBeforeRun) * 20 : -1;

            if (currentTicks == notifTicks && task.Notif != null && !task.Notif.isEmpty()) {
                server.getPlayerManager().broadcast(Text.literal(task.Notif), false);
            }

            if (currentTicks >= freqTicks) {
                executeCommand(server, task);
                currentTicks = 0;
            }

            tickCounters.put(taskName, currentTicks);
        }
    }

    public static void reloadSystem() {
        tickCounters.clear();
        erroredTasks.clear();
        System.out.println("[SomethingsTweak] Cache Cleared.");
    }
    private static void executeCommand(MinecraftServer server, SomethingsTweakConfig.CommandTask task) {
        if (task.Cmd == null || task.Cmd.isEmpty()) return;
        ServerCommandSource source = server.getCommandSource();
        if (task.ExecuteNotifEnable == 0) {
            source = source.withSilent();
        }
        server.getCommandManager().executeWithPrefix(source, task.Cmd);
    }
}