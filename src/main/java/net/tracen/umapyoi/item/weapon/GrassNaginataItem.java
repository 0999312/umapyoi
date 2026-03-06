package net.tracen.umapyoi.item.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;

public class GrassNaginataItem extends UmaWeaponItem {
	private static final ResourceLocation REACH_ID = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "reach");

	public GrassNaginataItem() {
		super(new NaginataTier(), 7, -2.7F, Umapyoi.defaultItemProperties().stacksTo(1));
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
		return super.getDefaultAttributeModifiers(stack).withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(REACH_ID, 2D,
				AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
	}

	private static class NaginataTier implements Tier {

		@Override
		public int getUses() {
			return 1561;
		}

		@Override
		public float getSpeed() {
			return 0F;
		}

		@Override
		public float getAttackDamageBonus() {
			return 1F;
		}

		@Override
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_IRON_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 20;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(ItemRegistry.HORSESHOE_RAINBOW.get());
		}

	}
}
