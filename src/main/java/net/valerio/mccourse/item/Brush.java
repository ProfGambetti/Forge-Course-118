package net.valerio.mccourse.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DyeColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.Random;

public class Brush extends Item {
    public Brush(Properties properties) {
        super(properties);
    }

    // Questo metodo viene chiamato quando clicchi direttamente su un'entità
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level level = player.level;

        if (!level.isClientSide() && target instanceof Sheep) {
            Sheep sheep = (Sheep) target;
            applyRandomColor(sheep);

            // Danni l'item
            stack.hurtAndBreak(1, player,
                    p -> p.broadcastBreakEvent(hand));

            // Suono di conferma
            level.playSound(null, player.blockPosition(),
                    SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0f, 1.0f);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void applyRandomColor(Sheep sheep) {
        Random random = new Random();
        DyeColor[] colors = DyeColor.values();
        DyeColor randomColor = colors[random.nextInt(colors.length)];

        // Cambia il colore della pecora
        sheep.setColor(randomColor);
    }
}