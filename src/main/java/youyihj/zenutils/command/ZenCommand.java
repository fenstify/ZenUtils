package youyihj.zenutils.command;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.mc1120.server.MCServer;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.*;
import youyihj.zenutils.util.object.ZenUtilsCommandSender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@ZenRegister
@ZenClass("mods.zenutils.command.ZenCommand")
@SuppressWarnings("unused")
public class ZenCommand extends CommandBase implements IZenCommand {
    private ZenCommand(@Nonnull String name) {
        this.name = name;
    }
    private String name;

    @ZenMethod
    public static ZenCommand create(@Nonnull String name) {
        return new ZenCommand(name);
    }

    @ZenProperty
    public ICommandExecute execute = ((command, server, sender, args) -> {});

    @ZenProperty
    public IGetCommandUsage getCommandUsage = (sender -> "commands.undefined.usage");

    @ZenProperty
    public TabCompletion tabCompletion = null;

    @ZenProperty
    public int requiredPermissionLevel = 4;

    @Override
    @ZenGetter("name")
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        this.execute.execute(this, new MCServer(server), new ZenUtilsCommandSender(sender), args);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return this.requiredPermissionLevel;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        int index = args.length - 1;
        List<String> emptyList = Collections.emptyList();
        if (this.tabCompletion == null || index >= this.tabCompletion.getInfo().length || index < 0) return emptyList;
        switch (this.tabCompletion.getInfo()[index]) {
            case "empty":
                return emptyList;
            case "item":
                return getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys());
            case "block":
                return getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys());
            case "player":
                return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
            case "potion":
                return getListOfStringsMatchingLastWord(args, Potion.REGISTRY.getKeys());
            case "x":
                return (targetPos == null) ? emptyList : Collections.singletonList(String.valueOf(targetPos.getX()));
            case "y":
                return (targetPos == null) ? emptyList : Collections.singletonList(String.valueOf(targetPos.getY()));
            case "z":
                return (targetPos == null) ? emptyList : Collections.singletonList(String.valueOf(targetPos.getZ()));
            default:
                return getListOfStringsMatchingLastWord(args, TabCompletionCase.cases.getOrDefault(this.tabCompletion.getInfo()[index], emptyList));
        }
    }
}