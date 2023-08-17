package org.bukkit.craftbukkit.v1_20_R1.block;

import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import org.bukkit.World;
import org.bukkit.block.SculkCatalyst;

public class CraftSculkCatalyst extends CraftBlockEntityState<SculkCatalystBlockEntity> implements SculkCatalyst {

    public CraftSculkCatalyst(World world, SculkCatalystBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    // Paper start - SculkCatalyst bloom API
    @Override
    public void bloom(@org.jetbrains.annotations.NotNull io.papermc.paper.math.Position position, int charge) {
        com.google.common.base.Preconditions.checkNotNull(position);
        requirePlaced();

        getTileEntity().getListener().bloom(
            world.getHandle(),
            getTileEntity().getBlockPos(),
            getTileEntity().getBlockState(),
            world.getHandle().getRandom()
        );
        getTileEntity().getListener().getSculkSpreader().addCursors(io.papermc.paper.util.MCUtil.toBlockPos(position), charge);
    }
    // Paper end
}
