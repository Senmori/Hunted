package net.senmori.hunted.loottable.function;


public enum LootFunctionType {
	
	/** See {@link SetCountFunction} */
	SET_COUNT("set_count"),
	/** See {@link SetDataFunction} */
	SET_DATA("set_data"),
	/** See {@link SetDamageFunction} */
	SET_DAMAGE("set_damage"),
	/** See {@link SetNBTFunction} */
	SET_NBT("set_nbt"),
	/** See {@link SetAttributesFunction} */
	SET_ATTRIBUTES("set_attributes"),
	/** See {@link EnchantRandomFunction} */
	ENCHANT_RANDOM("enchant_randomly"),
	/** See {@link EnchantWithLevelsFunction} */
	ENCHANT_WITH_LEVELS("enchant_with_levels"),
	/** See {@link LootingEnchantFunction} */
	LOOTING_ENCHANT("looting_enchant"),
	/** See {@link FurnaceSmeltFunction} */
	FURNACE_SMELT("furnace_smelt");
	
	private String name;
	LootFunctionType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
