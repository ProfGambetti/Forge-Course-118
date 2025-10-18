package net.valerio.mccourse.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.valerio.mccourse.MCCourseMod;

import java.util.function.Supplier;

public class ArmaturaCobalto implements ArmorMaterial {
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private static final int[] SLOT_PROTECTIONS = new int[]{5, 8, 10, 5}; // DA QUESTO ARRAY MODIFICO I VALORI DI ARMATURA AGGIUNTIVA DEI 4 SINGOLI PEZZI

    private final String name;
    private final int durabilityMultiplier;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private static final String[] TEXTURE_NAMES = {         //per visualizzare l'armatura effettiva sul personaggio ci sono 2 layer
            "cobalt_layer_1",  // per elmo, pantaloni, scarpe
            "cobalt_layer_2"   // per corazza
    };

    public ArmaturaCobalto() {
        this.name = "cobalt";
        this.durabilityMultiplier = 25;
        this.enchantmentValue = 15;
        this.sound = SoundEvents.ARMOR_EQUIP_IRON;
        this.toughness = 2.0F;
        this.knockbackResistance = 0.0F;
        this.repairIngredient = new LazyLoadedValue<>(() -> Ingredient.of(ModItems.COBALT_INGOT.get()));
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return SLOT_PROTECTIONS[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
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

