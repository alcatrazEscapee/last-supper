/*
 * Part of the Last Supper Mod by AlcatrazEscapee
 * Work under Copyright. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.lastsupper.items;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

import com.alcatrazescapee.lastsupper.capability.PlayerData;

@ParametersAreNonnullByDefault
public class LastSupperItem extends Item
{
    public LastSupperItem()
    {
        super(new Properties().maxStackSize(1).group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(new Food.Builder().setAlwaysEdible().hunger(0).saturation(0).build()));
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity)
    {
        entity.getCapability(PlayerData.CAPABILITY).ifPresent(cap -> cap.setEatenLastSupper(true));
        return super.onItemUseFinish(stack, world, entity);
    }
}
