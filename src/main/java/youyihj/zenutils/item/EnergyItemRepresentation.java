package youyihj.zenutils.item;

import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ItemRepresentation;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ModOnly("contenttweaker")
@ZenClass("mods.zenutils.EnergyItem")
public class EnergyItemRepresentation extends ItemRepresentation {

    int capacity;
    int maxReceive;
    int maxExtract;

    public EnergyItemRepresentation(String unlocalizedName, int capacity, int maxReceive, int maxExtract) {
        this.unlocalizedName = unlocalizedName;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public void register() {
        ContentTweaker.instance.getRegistry(ItemRegistry.class, "ITEM").register(new EnergyItemContent(this));
    }
}