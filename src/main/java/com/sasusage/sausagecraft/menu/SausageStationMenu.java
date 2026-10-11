package com.sasusage.sausagecraft.menu;

import com.sasusage.sausagecraft.SausageCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StonecutterMenu;

/**
 * A stonecutter-style menu bound to the Sausage Workstation block.
 * Uses the vanilla "stonecutting" recipe type, so every
 * "1 raw meat -> 1 sausage of your choice" recipe shows up in its GUI.
 */
public class SausageStationMenu extends StonecutterMenu {
    private final ContainerLevelAccess access;

    public SausageStationMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
        this.access = access;
    }

    public SausageStationMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        this(containerId, playerInventory, ContainerLevelAccess.create(playerInventory.player.level(), pos));
    }

    public SausageStationMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, buf.readBlockPos());
    }

    @Override
    public boolean stillValid(Player player) {
        // Keep the menu open only while standing next to our workstation, not the vanilla stonecutter.
        return stillValid(this.access, player, SausageCraft.SAUSAGE_WORKSTATION.get());
    }
}
