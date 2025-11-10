package net.valerio.mccourse.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ArcaneCrystalBlock extends Block {
    // Proprietà per la luminosità notturna
    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 12);

    public ArcaneCrystalBlock() {
        super(Properties.of(Material.GLASS)
                .strength(4.0f, 8.0f)
                .sound(SoundType.GLASS)
                .lightLevel(state -> state.getValue(LIGHT_LEVEL))
                .requiresCorrectToolForDrops()
                .noOcclusion() // Rendering trasparente
        );

        // Imposta lo stato predefinito
        this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT_LEVEL, 7));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
    }

    // Metodo chiamato quando il blocco viene piazzato
    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!world.isClientSide) {
            // Aggiungi qui eventuali effetti particellari quando piazzato
            updateLightLevel(world, pos, state);
        }
    }

    // Metodo per aggiornare la luminosità in base all'ora del giorno
    private void updateLightLevel(Level world, BlockPos pos, BlockState state) {
        if (!world.isClientSide) {
            int newLightLevel = world.isDay() ? 7 : 12;
            if (state.getValue(LIGHT_LEVEL) != newLightLevel) {
                world.setBlock(pos, state.setValue(LIGHT_LEVEL, newLightLevel), 3);
            }
        }
    }

    // Metodo chiamato ogni tick (opzionale, per effetti dinamici)

    public void playerClose(Level world, BlockPos pos, Player player) {
        if (!world.isClientSide && player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 9.0D) {
            // Aggiungi qui il suono magico quando il giocatore si avvicina
            // world.playSound(null, pos, SoundEvents., ...);
        }
    }
}