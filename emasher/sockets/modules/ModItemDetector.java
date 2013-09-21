package emasher.sockets.modules;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;
import emasher.api.RSPulseModule;
import emasher.api.SideConfig;
import emasher.api.SocketTileAccess;
import emasher.core.EmasherCore;
import emasher.sockets.PacketHandler;
import emasher.sockets.SocketsMod;
import emasher.sockets.TileSocket;

public class ModItemDetector extends RSPulseModule
{

	public ModItemDetector(int id)
	{
		super(id, "sockets:DETItem_0", "sockets:DETItem_1");
	}

	@Override
	public String getLocalizedName()
	{
		return "Item Detector";
	}
	
	@Override
	public void getToolTip(List l)
	{
		l.add("Creates an internal redstone pulse");
		l.add("when an item is added to its");
		l.add("configured inventory");
	}
	
	@Override
	public void getIndicatorKey(List l)
	{
		l.add(SocketsMod.PREF_GREEN + "Inventory to watch");
		l.add(SocketsMod.PREF_RED + "RS control channel to pulse");
	}
	
	@Override
	public void addRecipe()
	{
		GameRegistry.addShapedRecipe(new ItemStack(SocketsMod.module, 1, moduleID), " h ", "iui", " b ", Character.valueOf('i'), Item.ingotIron, Character.valueOf('h'), Block.pressurePlateStone,
				Character.valueOf('u'), Block.trapdoor, Character.valueOf('b'), SocketsMod.blankSide);
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(SocketsMod.module, 1, moduleID), " h ", "iui", " b ", Character.valueOf('i'), "ingotAluminum", Character.valueOf('h'), Block.pressurePlateStone,
				Character.valueOf('u'), Block.trapdoor, Character.valueOf('b'), SocketsMod.blankSide));
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(SocketsMod.module, 1, moduleID), " h ", "iui", " b ", Character.valueOf('i'), "ingotTin", Character.valueOf('h'), Block.pressurePlateStone,
				Character.valueOf('u'), Block.trapdoor, Character.valueOf('b'), SocketsMod.blankSide));
	}
	
	@Override
	public boolean hasInventoryIndicator() { return true; }
	
	@Override
	public void onInventoryChange(SideConfig config, int index, SocketTileAccess ts, ForgeDirection side, boolean add)
	{
		if(add && (index == config.inventory || config.inventory == -1))
		{
			//System.out.println("(" + index + ", " + config.inventory + ")");
			config.meta = 1;
			
			for(int i = 0; i < 3; i++)
			{
				if(config.rsControl[i])
				{
					ts.modifyRS(i, true);
				}
			}
			
			ts.updateAdj(side);
			ts.sendClientSideState(side.ordinal());
			//PacketHandler.instance.SendClientSideState(ts, (byte)side.ordinal());
		}
	}

}
