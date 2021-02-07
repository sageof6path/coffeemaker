package beverage;

import ingredients.Ingredient;

public interface BeverageBuilder {
    public BeverageBuilder name(String name);
    public BeverageBuilder addIngredient(Ingredient ingredient, int quantity);
    public Beverage build();
}
