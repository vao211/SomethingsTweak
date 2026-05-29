package vao211.somethingstweak.config;

import eu.midnightdust.lib.config.MidnightConfig;
import java.util.HashMap;
import java.util.Map;

public class SomethingsTweakConfig extends MidnightConfig {

    @Entry
    public static Map<String, CommandTask> tasks = new HashMap<>();

    static {
        CommandTask dropClear = new CommandTask();
        dropClear.Freq = 180;
        dropClear.Cmd = "kill @e[type=minecraft:item]";
        dropClear.Notif = "All Drop item will be clear after 30s";
        dropClear.NotifBeforeRun = 30;
        dropClear.ExecuteNotifEnable = 0;

        tasks.put("Drop Item Clear", dropClear);
    }

    public static void clearOldData() {
        if (tasks != null) {
            tasks.clear();
        }
    }
    public static class CommandTask {
        public int Freq;
        public String Cmd;
        public String Notif;
        public int NotifBeforeRun;
        public int ExecuteNotifEnable;

        public boolean isValid() {
            if (Freq <= 0) return false;
            if (Cmd == null || Cmd.trim().isEmpty()) return false;
            if (Notif == null) return false;
            if (ExecuteNotifEnable != 0 && ExecuteNotifEnable != 1) return false;
            return true;
        }
    }
}