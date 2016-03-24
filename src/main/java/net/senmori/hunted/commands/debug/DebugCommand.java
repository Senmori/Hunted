package net.senmori.hunted.commands.debug;


import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.loottable.LootTable;
import net.senmori.hunted.loottable.Pool;
import net.senmori.hunted.loottable.attribute.LootAttribute;
import net.senmori.hunted.loottable.entry.Entry;
import net.senmori.hunted.loottable.entry.EntryType;
import net.senmori.hunted.loottable.function.FurnaceSmeltFunction;
import net.senmori.hunted.loottable.function.SetAttributesFunction;
import net.senmori.hunted.util.Reference.Permissions;

public class DebugCommand extends Subcommand {

	public DebugCommand() {
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.ADMIN_DEBUG;
	}

	@Override
	protected void perform() {
		String fileName = "test";
		if(args.length > 0) {
			fileName = args[0];
		}
		LootTable testTable = new LootTable(Hunted.getInstance().getConfigManager().activeWorld, Hunted.getInstance().getName().toLowerCase(), fileName + ".json");
		Pool pool = new Pool();
		pool.setRolls(3);
		pool.setBonusRolls(2, 6);
		Entry cobbleStone = new Entry(EntryType.ITEM, "cobblestone", 1);
		Entry diamondSword = new Entry(EntryType.ITEM, "diamond_sword", 1);
		Entry crackedBrick = new Entry(EntryType.ITEM, "stonebrick", 40);
		FurnaceSmeltFunction fs = new FurnaceSmeltFunction();
		crackedBrick.addFunction(fs);
		
		SetAttributesFunction attribFunc = new SetAttributesFunction();
		LootAttribute lootAttrib = LootAttribute.genericLuckAttribute.setAmount(10.0);
		attribFunc.addModifier(lootAttrib); // add Attribute to function
		diamondSword.addFunction(attribFunc); // add function to item
		
		pool.addEntry(cobbleStone);
		pool.addEntry(diamondSword);
		pool.addEntry(crackedBrick);
		testTable.addPool(pool);
		
		getPlayer().sendMessage("JSON: " + testTable.toJson());
		testTable.write();
		
		
	}
}
