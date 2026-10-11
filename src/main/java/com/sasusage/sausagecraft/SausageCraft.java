package com.sasusage.sausagecraft;

import com.sasusage.sausagecraft.block.PastryBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(SausageCraft.MOD_ID)
public class SausageCraft {
    public static final String MOD_ID = "sausage_craft";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    /** Food value shared by every sausage item: 6 hunger points (3 drumsticks), 0.6 saturation modifier. */
    public static final FoodProperties SAUSAGE_FOOD = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.6F)
            .build();

    public static final DeferredBlock<PastryBlock> SAUSAGE_IN_DOUGH = BLOCKS.register("sausage_in_dough",
            () -> new PastryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)
                    .noOcclusion()));

    public static final DeferredBlock<PastryBlock> BRAIDED_SAUSAGE = BLOCKS.register("braided_sausage",
            () -> new PastryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)
                    .noOcclusion()));

    public static final DeferredItem<BlockItem> SAUSAGE_IN_DOUGH_ITEM = ITEMS.register("sausage_in_dough",
            () -> new BlockItem(SAUSAGE_IN_DOUGH.get(), new Item.Properties().food(SAUSAGE_FOOD)));

    public static final DeferredItem<BlockItem> BRAIDED_SAUSAGE_ITEM = ITEMS.register("braided_sausage",
            () -> new BlockItem(BRAIDED_SAUSAGE.get(), new Item.Properties().food(SAUSAGE_FOOD)));

    public static final Supplier<CreativeModeTab> SAUSAGE_TAB = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID))
            .icon(() -> new ItemStack(SAUSAGE_IN_DOUGH_ITEM.get()))
            .displayItems((parameters, output) -> {
                output.accept(SAUSAGE_IN_DOUGH_ITEM.get());
                output.accept(BRAIDED_SAUSAGE_ITEM.get());
            })
            .build());

    public SausageCraft(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
    }
}
