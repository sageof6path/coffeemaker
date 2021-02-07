package machine;

import ingredients.Ingredient;

public interface MachineBuilder {
    public MachineBuilder addIngredient(Ingredient ingredient, int capacity, int availableQuantity);
    public MachineBuilder outlets(int outlets);
    public Machine build();
}
