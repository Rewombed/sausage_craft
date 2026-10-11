package com.sasusage.sausagecraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A flat pastry block that lies on the ground: low profile, horizontal facing
 * taken from the player when placed, and optional waterlogging.
 */
public class PastryBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<PastryBlock> CODEC = simpleCodec(PastryBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    /** Default low profile so the pastry hugs the surface without floating gaps. */
    private static final VoxelShape DEFAULT_SHAPE = Block.box(2.0, 0.0, 1.0, 14.0, 5.5, 15.0);

    /** Shapes indexed by (facing.ordinal() - 2): NORTH, SOUTH, WEST, EAST. */
    private final VoxelShape[] shapes;

    public PastryBlock(Properties properties) {
        this(properties, DEFAULT_SHAPE);
    }

    public PastryBlock(Properties properties, VoxelShape northShape) {
        super(properties);
        this.shapes = rotatedShapes(northShape);
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    /** Rotates a single north-facing box shape into the four horizontal directions. */
    private static VoxelShape[] rotatedShapes(VoxelShape north) {
        double x1 = north.min(Direction.Axis.X);
        double x2 = north.max(Direction.Axis.X);
        double y1 = north.min(Direction.Axis.Y);
        double y2 = north.max(Direction.Axis.Y);
        double z1 = north.min(Direction.Axis.Z);
        double z2 = north.max(Direction.Axis.Z);
        return new VoxelShape[] {
                Block.box(x1, y1, z1, x2, y2, z2),                  // NORTH
                Block.box(16.0 - x2, y1, 16.0 - z2, 16.0 - x1, y2, 16.0 - z1), // SOUTH
                Block.box(z1, y1, 16.0 - x2, z2, y2, 16.0 - x1),    // WEST
                Block.box(16.0 - z2, y1, x1, 16.0 - z1, y2, x2)     // EAST
        };
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapes[state.getValue(FACING).ordinal() - 2];
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED,
                        context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
