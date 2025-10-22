package net.valerio.mccourse.item;
//classe armatura cobalto
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.valerio.mccourse.MCCourseMod;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ArmaturaCobalto implements ArmorMaterial {
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private static final int[] SLOT_PROTECTIONS = new int[]{3, 6, 8, 3};    //Protezione data dai 4 item

    private final String name;
    private final int durabilityMultiplier;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    public ArmaturaCobalto() {
        this.name = "cobalt";
        this.durabilityMultiplier = 25;
        this.enchantmentValue = 15;
        this.sound = SoundEvents.ARMOR_EQUIP_IRON;
        this.toughness = 2.0F;
        this.knockbackResistance = 0.0F;
        this.repairIngredient = () -> Ingredient.of(ModItems.COBALT_INGOT.get());
    }

    @Override
    public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
        return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
        return SLOT_PROTECTIONS[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return MCCourseMod.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}