package machine;

import ingredients.Ingredient;

import java.util.concurrent.Semaphore;

/*
used builder pattern to effectively create coffee machine
 */
public class CoffeeMachineBuilder implements MachineBuilder {
    private CoffeeMachine coffeeMachine = new CoffeeMachine();
    @Override
    public MachineBuilder addIngredient(Ingredient ingredient, int capacity, int availableQuantity) {
        coffeeMachine.ingredientCapacityMap.put(ingredient, capacity);
        coffeeMachine.ingredientQuantityMap.put(ingredient, Math.min(availableQuantity, capacity));
        return this;
    }

    @Override
    public MachineBuilder outlets(int outlets) {
        coffeeMachine.outlets = outlets;
        coffeeMachine.semaphore = new Semaphore(outlets);
        return this;
    }

    @Override
    public Machine build() {
        return coffeeMachine;
    }
}
