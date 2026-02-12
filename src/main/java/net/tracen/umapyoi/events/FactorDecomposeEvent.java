package net.tracen.umapyoi.events;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.tracen.umapyoi.container.FactorDecomposeMenu.AllowContinueDefaultLogic;
import static net.tracen.umapyoi.container.FactorDecomposeMenu.DefaultAlgResultStacks;

/**
 * This event would be posted whenever a player tries to decompose a factor item inside the derby stallion table.<br>
 * inputStack: a non-mutable ItemStack, which is the input factor item.<br>
 * replaceStack: a mutable ItemStack, which is the replaced factor item to proceed instead of the original one<br>
 * listOfReturn: a list of UmaFactorStack (only the first 9 elements would be considered), output would be created
 * bypassing the original logic<br>
 * Cancel: once this event is canceled, it is treated as a non-valid input.<br>
 * randomSeed: to preserve reproducibility, all random instances shall initialize with this random seed<br>
 * logicOfAllowContinueCheck -> boolean: a function (overridable) indicates if the default generation method is valid
 * for the input factor item<br>
 * logicOfGenerateResultStacks -> List&lt;UmaFactorStack&gt;: a function (overridable) implements default generation
 */
@Cancelable
public class FactorDecomposeEvent extends Event {
    private final ItemStack inputStack;
    public final int randomSeed;
    private ItemStack replaceStack;
    private List<UmaFactorStack> defaultReturnVal;
    private List<UmaFactorStack> listOfReturn;

    public boolean logicOfAllowContinueCheck(ItemStack stack) {
        return AllowContinueDefaultLogic(stack);
    }

    public List<UmaFactorStack> logicOfGenerateResultStacks(ItemStack stack, int random) {
        return DefaultAlgResultStacks(stack, random);
    }

    public FactorDecomposeEvent(ItemStack inputStack, int random) {
        this.inputStack = inputStack;
        this.replaceStack = inputStack;
        this.randomSeed = random;
        this.listOfReturn = null;
        this.defaultReturnVal = this.logicOfAllowContinueCheck(inputStack) ? this.logicOfGenerateResultStacks(inputStack, random) : null;
    }

    public ItemStack getCopyOfInput() {
        return this.inputStack.copy();
    }

    public ItemStack getResultStack() {
        return Optional.ofNullable(replaceStack).orElse(this.inputStack.copy());
    }

    public void setReplaceSrc(ItemStack stack) {
        this.replaceStack = stack;
        this.defaultReturnVal = this.logicOfAllowContinueCheck(stack) ? this.logicOfGenerateResultStacks(inputStack, this.randomSeed) : null;
    }

    public void setResult(List<UmaFactorStack> listOfReturn) {
        this.listOfReturn = listOfReturn;
    }

    @Nullable public List<UmaFactorStack> getListOfReturn() {
        return Optional.ofNullable(Optional.ofNullable(this.listOfReturn).orElse(this.defaultReturnVal)).map(ArrayList::new).orElse(null);
    }
}
