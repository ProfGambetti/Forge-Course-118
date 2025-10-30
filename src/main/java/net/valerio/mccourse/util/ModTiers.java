package net.valerio.mccourse.util;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.valerio.mccourse.item.ModItems;

public class ModTiers {
    public static final Tier COBALT = new ForgeTier(
            3,                          // livello di mining (diamond = 3)
            1800,                       // durabilità (più del diamante che ha 1561)
            8.0F,                       // velocità di mining
            4.0F,                       // danno bonus
            15,                         // enchantability
            BlockTags.NEEDS_DIAMOND_TOOL, // blocchi che richiedono questo tier
            () -> Ingredient.of(ModItems.COBALT_INGOT.get()) // materiale di riparazione
    );
}
