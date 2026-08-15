package io.github.mumboteam.spooncraftadditions.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.List;

public class DragonShield extends ShieldItem implements PolymerItem {
    public DragonShield(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON).stacksTo(1));
    }

    @Override
    public void modifyClientTooltip(List<Component> tooltip, ItemStack stack, PacketContext context) {
        tooltip.addFirst(Component.translatable("item.spooncraftadditions.dragon_shield.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        return Items.SHIELD;
    }
}
