package net.slqmy.tss_plugin_template;

import net.kyori.adventure.text.Component;
import net.slqmy.tss_core.TSSCorePlugin;
import org.bukkit.Bukkit;

import java.util.Random;

public class Util {

    public Util(TSSPlugin plugin, TSSCorePlugin core) {
    }

    private String toBinaryString(boolean[] booleans) {
        StringBuilder binary = new StringBuilder();

        for (boolean value : booleans) {
            binary.append(value ? "1" : "0");
        }

        return binary.toString();
    }

    public void logToServer(String message) {
        Bukkit.getServer().broadcast(Component.text(message));
    }

    public int getFlightDurationByDistance(double distance) {
        String binary = this.toBinaryString(new boolean[] {distance < 40, distance < 56});

        return switch (binary) {
            case "11" -> 1;
            case "01" -> 2;
            default -> 3;
        };
    }

    public double randomDouble(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }

        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    public boolean randomChance(double chance) {
        if (chance < 0.0 || chance > 100.0) return false;

        Random random = new Random();
        double randomValue = random.nextDouble() * 100.0;

        return randomValue <= chance;
    }
}
