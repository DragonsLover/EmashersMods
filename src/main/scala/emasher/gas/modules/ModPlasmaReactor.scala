package emasher.gas.modules

import emasher.api._
import emasher.core._
import emasher.sockets._
import java.util._
import net.minecraft.item.crafting._
import net.minecraftforge.oredict._
import net.minecraftforge.common._
import net.minecraftforge.fluids._
import net.minecraft.item._
import net.minecraft.block._
import emasher.gas.EmasherGas
import net.minecraftforge.common.util.ForgeDirection
import cpw.mods.fml.relauncher.{Side, SideOnly}

class ModPlasmaReactor(id: Int) extends SocketModule(id, "gascraft:plasmaReactor", "gascraft:plasmaReactorActive")
{
	override def getLocalizedName = "Plasma Reactor"
	
	override def getToolTip(l: List[Object])
	{
		l.add("Generates power by combining plasma")
		l.add("with propellent and hydrogen")
		l.add("The reactor will not shut off")
		l.add("when the socket's capacitor is full")
	}
	
	override def getIndicatorKey(l: List[Object])
	{
		l.add(SocketsMod.PREF_AQUA + "Outputs 256 RF/t")
	}
	
	override def getCurrentTexture(config: SideConfig):
	Int = 
	{
		if(config.meta != 0) 1
		else 0
	}
	
	override def addRecipe
	{
		CraftingManager.getInstance().getRecipeList().asInstanceOf[List[Object]].add(new ShapedOreRecipe(new ItemStack(SocketsMod.module, 1, moduleID), "scs", "rpr", "sbs", Character.valueOf('s'), EmasherCore.psu, Character.valueOf('s'), "blockSteel",
				Character.valueOf('c'), EmasherCore.circuit, Character.valueOf('r'), SocketsMod.rsIngot, Character.valueOf('b'), SocketsMod.blankSide))
	}
	
	override def isMachine = true
	
	override def canBeInstalled(ts: SocketTileAccess, side: ForgeDirection):
	Boolean = 
	{	
		for(i <- 0 to 5)
		{
			var m = ts.getSide(ForgeDirection.getOrientation(i))
			if(m != null && m.isMachine)
			{
				return false
			}
		}
		
		true
	}
	
	override def updateSide(config: SideConfig, ts: SocketTileAccess, side: ForgeDirection)
	{
		var plasmaTank = -1
		var propellentTank = -1
		var hydrogenTank = -1
		
		var i = 0
		
		for(i <- 0 to 2)
		{
			val fs: FluidStack = ts.getFluidInTank(i)
			if(fs != null && fs.amount > 10)
			{
				if(fs.isFluidEqual(new FluidStack(EmasherGas.fluidPlasma, 4000)) && plasmaTank == -1) plasmaTank = i
				if(fs.isFluidEqual(new FluidStack(EmasherGas.fluidPropellent, 4000)) && propellentTank == -1) propellentTank = i
				if(fs.isFluidEqual(new FluidStack(EmasherGas.fluidHydrogen, 4000)) && hydrogenTank == -1) hydrogenTank = i
			}
		}
		
		if(plasmaTank != -1 && propellentTank != -1 && hydrogenTank != -1)
		{
			for(i <- 0 to 2)
			{
				ts.drainInternal(i, 10, true)
			}
			
			ts.addEnergy(256, false)
			
			if(config.meta == 0)
			{
				config.meta = 1
				ts.sendClientSideState(side.ordinal())
			}
			
		}
		else
		{
			if(config.meta != 0)
			{
				config.meta = 0
				ts.sendClientSideState(side.ordinal())
			}
		}
	}

  @SideOnly(Side.CLIENT)
  override def getInternalTexture(ts: SocketTileAccess, config: SideConfig, side: ForgeDirection): String = {
    if(config.meta != 0) "gascraft:inner_plasma"
    else "sockets:inner_black"
  }

  @SideOnly(Side.CLIENT)
  override def getAllInternalTextures: Array[String] = {
    return Array[String]("gascraft:inner_plasma")
  }
}