package net.valerio.mccourse.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MagicStickConfig {

    private static ResourceLocation blockToUse = new ResourceLocation("minecraft", "diamond_block");

    public static void loadConfig() {
        Path configPath = FMLPaths.CONFIGDIR.get().resolve("magicstick.txt");

        // Se non esiste, crealo con un valore di default
        if (!Files.exists(configPath)) {
            try {
                Files.write(configPath, List.of("block=minecraft:diamond_block"));
            } catch (IOException e) {
                System.out.println("Non legge il file");
                e.printStackTrace();
            }
        }

        // Legge il file
        try {
            List<String> lines = Files.readAllLines(configPath);
            for (String line : lines) {
                if (line.startsWith("block=")) {
                    String id = line.substring("block=".length()).trim();
                    blockToUse = new ResourceLocation(id);
                    System.out.println("[MagicStick] Config loaded: " + id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Block getBlockToUse() {
        Block block = ForgeRegistries.BLOCKS.getValue(blockToUse);
        return block != null ? block : Blocks.DIAMOND_BLOCK;
    }
}
