package machine;

import exceptions.UnsupportedIngredientException;
import ingredients.Ingredient;
import org.junit.Assert;
import org.junit.Test;

public class CoffeeMachineBuilderTest {
    @Test
    public void testAddIngredients() throws UnsupportedIngredientException {
        MachineBuilder machineBuilder = new CoffeeMachineBuilder();
        machineBuilder.addIngredient(Ingredient.HOT_WATER, 100, 150);
        Machine machine = machineBuilder.build();
        Assert.assertEquals(100 ,machine.availableQuantity(Ingredient.HOT_WATER));
        Assert.assertEquals(100 ,machine.getCapacity(Ingredient.HOT_WATER));
    }

    @Test
    public void testOutlets() {
        MachineBuilder machineBuilder = new CoffeeMachineBuilder().outlets(3);
        CoffeeMachine coffeeMachine = (CoffeeMachine) machineBuilder.build();
        Assert.assertEquals(3, coffeeMachine.outlets);
        Assert.assertEquals(3, coffeeMachine.semaphore.availablePermits());
    }
}
