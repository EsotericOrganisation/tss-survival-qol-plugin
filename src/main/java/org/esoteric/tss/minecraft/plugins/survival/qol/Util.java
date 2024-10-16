package org.esoteric.tss.minecraft.plugins.survival.qol;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.util.Random;

public class Util {

    public static void logToServer(String message) {
        Bukkit.getServer().broadcast(Component.text(message));
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
