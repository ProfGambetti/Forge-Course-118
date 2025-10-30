package net.valerio.mccourse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import net.valerio.mccourse.block.ModBlocks;

public class FindCobaltCommand {

    public FindCobaltCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("findcobalt")
                .executes((command) -> findCobaltOre(command.getSource())));
    }

    private int findCobaltOre(CommandSourceStack source) throws CommandSyntaxException {
        ServerLevel world = source.getLevel();
        BlockPos playerPos = new BlockPos(source.getPosition());

        source.sendSuccess(new TextComponent("§6🔍 Scansionando TUTTO il chunk..."), false);

        // 🔥 PRENDI il chunk dove si trova il giocatore
        int chunkX = playerPos.getX() >> 4;
        int chunkZ = playerPos.getZ() >> 4;
        LevelChunk chunk = world.getChunk(chunkX, chunkZ);

        source.sendSuccess(new TextComponent("§6Chunk: [" + chunkX + ", " + chunkZ + "]"), false);

        int blocksChecked = 0;
        int cobaltFound = 0;

        // 🔥 SCANSIONA TUTTO il chunk (16x16x384)
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = -64; y <= 320; y++) {
                    BlockPos worldPos = new BlockPos(
                            chunk.getPos().getMinBlockX() + x,
                            y,
                            chunk.getPos().getMinBlockZ() + z
                    );

                    blocksChecked++;

                    if (world.isLoaded(worldPos)) {
                        Block block = world.getBlockState(worldPos).getBlock();

                        if (block == ModBlocks.COBALT_ORE.get()) {
                            source.sendSuccess(new TextComponent(
                                    "§a✅ Cobalt Ore a: " + worldPos.getX() + ", " + worldPos.getY() + ", " + worldPos.getZ()
                            ), false);
                            cobaltFound++;
                        }
                    }
                }
            }
        }

        // 🔥 RISULTATO
        source.sendSuccess(new TextComponent("§6=== RISULTATO CHUNK COMPLETO ==="), false);
        source.sendSuccess(new TextComponent("§6Blocchi controllati: " + blocksChecked), false);
        source.sendSuccess(new TextComponent("§6Cobalt Ore trovati: " + cobaltFound), false);

        if (cobaltFound == 0) {
            source.sendSuccess(new TextComponent("§c❌ ZERO Cobalt Ore in tutto il chunk!"), false);
        }

        return cobaltFound > 0 ? 1 : -1;
    }
}