package com.sasusage.sausagecraft;

import com.sasusage.sausagecraft.block.PastryBlock;
import com.sasusage.sausagecraft.block.SausageStationBlock;
import com.sasusage.sausagecraft.menu.SausageStationMenu;
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
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
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
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, MOD_ID);

    /** Food value shared by every sausage item: 6 hunger points (3 drumsticks), 0.6 saturation modifier. */
    public static final FoodProperties SAUSAGE_FOOD = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.6F)
            .build();

    // Collision shapes matching each model's exact footprint (north-facing AABB).
    private static final VoxelShape SHAPE_CLASSIC =
            Block.box(4.8, 0.0, 0.5, 11.2, 5.5, 14.9);
    private static final VoxelShape SHAPE_BRAIDED =
            Block.box(4.3, 0.0, 0.0, 11.7, 5.6, 16.0);
    private static final VoxelShape SHAPE_BRAID_LOW =
            Block.box(0.0, 0.0, 4.1, 16.0, 1.7, 11.9);
    // Flat caterpillar model: 11.4 x 1.2 x 6.5, authored flush with the floor.
    // Collision keeps a 2px minimum height so the block stays easy to target.
    private static final VoxelShape SHAPE_CATERPILLAR =
            Block.box(2.29, 0.0, 4.03, 13.66, 2.0, 10.57);

    public static final DeferredBlock<PastryBlock> SAUSAGE_IN_DOUGH = BLOCKS.register("sausage_in_dough",
            () -> new PastryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)
                    .noOcclusion(), SHAPE_CLASSIC));

    public static final DeferredBlock<PastryBlock> BRAIDED_SAUSAGE = BLOCKS.register("braided_sausage",
            () -> new PastryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)
                    .noOcclusion(), SHAPE_BRAIDED));

    public static final DeferredBlock<PastryBlock> SAUSAGE_BRAID_GOLDEN = BLOCKS.register("sausage_braid_golden",
            () -> new PastryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)
                    .noOcclusion(), SHAPE_BRAID_LOW));

    public static final DeferredBlock<PastryBlock> SAUSAGE_BRAID_RUDDY = BLOCKS.register("sausage_braid_ruddy",
            () -> new PastryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_BROWN)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)
                    .noOcclusion(), SHAPE_BRAID_LOW));

    public static final DeferredBlock<PastryBlock> SAUSAGE_BRAID_FLUFFY = BLOCKS.register("sausage_braid_fluffy",
            () -> new PastryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)
                    .noOcclusion(), SHAPE_BRAID_LOW));

    public static final DeferredBlock<PastryBlock> SAUSAGE_CATERPILLAR = BLOCKS.register("sausage_caterpillar",
            () -> new PastryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)
                    .noOcclusion(), SHAPE_CATERPILLAR));

    public static final DeferredBlock<SausageStationBlock> SAUSAGE_WORKSTATION = BLOCKS.register("sausage_workstation",
            () -> new SausageStationBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(2.5F)
                    .sound(SoundType.WOOD)));

    public static final DeferredItem<BlockItem> SAUSAGE_IN_DOUGH_ITEM = ITEMS.register("sausage_in_dough",
            () -> new BlockItem(SAUSAGE_IN_DOUGH.get(), new Item.Properties().food(SAUSAGE_FOOD)));

    public static final DeferredItem<BlockItem> BRAIDED_SAUSAGE_ITEM = ITEMS.register("braided_sausage",
            () -> new BlockItem(BRAIDED_SAUSAGE.get(), new Item.Properties().food(SAUSAGE_FOOD)));

    public static final DeferredItem<BlockItem> SAUSAGE_BRAID_GOLDEN_ITEM = ITEMS.register("sausage_braid_golden",
            () -> new BlockItem(SAUSAGE_BRAID_GOLDEN.get(), new Item.Properties().food(SAUSAGE_FOOD)));

    public static final DeferredItem<BlockItem> SAUSAGE_BRAID_RUDDY_ITEM = ITEMS.register("sausage_braid_ruddy",
            () -> new BlockItem(SAUSAGE_BRAID_RUDDY.get(), new Item.Properties().food(SAUSAGE_FOOD)));

    public static final DeferredItem<BlockItem> SAUSAGE_BRAID_FLUFFY_ITEM = ITEMS.register("sausage_braid_fluffy",
            () -> new BlockItem(SAUSAGE_BRAID_FLUFFY.get(), new Item.Properties().food(SAUSAGE_FOOD)));

    public static final DeferredItem<BlockItem> SAUSAGE_CATERPILLAR_ITEM = ITEMS.register("sausage_caterpillar",
            () -> new BlockItem(SAUSAGE_CATERPILLAR.get(), new Item.Properties().food(SAUSAGE_FOOD)));

    public static final DeferredItem<BlockItem> SAUSAGE_WORKSTATION_ITEM = ITEMS.register("sausage_workstation",
            () -> new BlockItem(SAUSAGE_WORKSTATION.get(), new Item.Properties()));

    public static final Supplier<MenuType<SausageStationMenu>> SAUSAGE_STATION_MENU =
            MENUS.register("sausage_station", () ->
                    IMenuTypeExtension.create((id, inventory, buf) -> new SausageStationMenu(id, inventory, buf)));

    public static final Supplier<CreativeModeTab> SAUSAGE_TAB = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID))
            .icon(() -> new ItemStack(SAUSAGE_IN_DOUGH_ITEM.get()))
            .displayItems((parameters, output) -> {
                output.accept(SAUSAGE_IN_DOUGH_ITEM.get());
                output.accept(BRAIDED_SAUSAGE_ITEM.get());
                output.accept(SAUSAGE_BRAID_GOLDEN_ITEM.get());
                output.accept(SAUSAGE_BRAID_RUDDY_ITEM.get());
                output.accept(SAUSAGE_BRAID_FLUFFY_ITEM.get());
                output.accept(SAUSAGE_CATERPILLAR_ITEM.get());
                output.accept(SAUSAGE_WORKSTATION_ITEM.get());
            })
            .build());

    public SausageCraft(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
        MENUS.register(modEventBus);
    }
}
