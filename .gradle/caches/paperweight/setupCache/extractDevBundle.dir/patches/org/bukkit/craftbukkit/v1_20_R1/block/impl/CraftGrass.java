/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.v1_20_R1.block.impl;

public final class CraftGrass extends org.bukkit.craftbukkit.v1_20_R1.block.data.CraftBlockData implements org.bukkit.block.data.Snowable {

    public CraftGrass() {
        super();
    }

    public CraftGrass(net.minecraft.world.level.block.state.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.v1_20_R1.block.data.CraftSnowable

    private static final net.minecraft.world.level.block.state.properties.BooleanProperty SNOWY = getBoolean(net.minecraft.world.level.block.GrassBlock.class, "snowy");

    @Override
    public boolean isSnowy() {
        return get(CraftGrass.SNOWY);
    }

    @Override
    public void setSnowy(boolean snowy) {
        set(CraftGrass.SNOWY, snowy);
    }
}
