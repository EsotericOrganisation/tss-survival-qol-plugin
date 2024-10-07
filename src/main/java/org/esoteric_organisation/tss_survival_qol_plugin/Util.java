package org.esoteric_organisation.tss_survival_qol_plugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Util {

    @NotNull
    private static String toBinaryString(@NotNull boolean[] booleans) {
        StringBuilder binary = new StringBuilder();

        for (boolean value : booleans) {
            binary.append(value ? "1" : "0");
        }

        return binary.toString();
    }

    public static void logToServer(String message) {
        Bukkit.getServer().broadcast(Component.text(message));
    }

    public static int getFlightDurationByDistance(double distance) {
        String binary = toBinaryString(new boolean[] {distance < 40, distance < 56});

        return switch (binary) {
            case "11" -> 1;
            case "01" -> 2;
            default -> 3;
        };
    }

    public static double randomDouble(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }

        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    public static boolean randomChance(double chance) {
        if (chance < 0.0 || chance > 100.0) return false;

        Random random = new Random();
        double randomValue = random.nextDouble() * 100.0;

        return randomValue <= chance;
    }
}
