package beverage;

import ingredients.Ingredient;

//created builder to make code more readable
public class SimpleBeverageBuilder implements BeverageBuilder {
    private Beverage beverage = new Beverage();

    public BeverageBuilder name(String name) {
        beverage.name = name;
        return this;
    }

    public BeverageBuilder addIngredient(Ingredient ingredient, int quantity) {
        beverage.ingredientToQuantityMap.put(ingredient, quantity);
        return this;
    }

    public Beverage build() {
        return beverage;
    }
}
