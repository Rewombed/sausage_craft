package com.sasusage.sausagecraft.client;

import com.sasusage.sausagecraft.SausageCraft;
import com.sasusage.sausagecraft.menu.SausageStationMenu;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/**
 * Client-side registration: reuse the vanilla stonecutter screen for our menu.
 */
@EventBusSubscriber(modid = SausageCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SausageCraftClient {
    private SausageCraftClient() {
    }

    @SubscribeEvent
    static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(SausageCraft.SAUSAGE_STATION_MENU.get(), StonecutterScreen::new);
    }
}
