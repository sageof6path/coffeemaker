package beverage;

import ingredients.Ingredient;

import java.util.HashMap;
import java.util.Map;

public class Beverage {
    String name;
    Map<Ingredient, Integer> ingredientToQuantityMap = new HashMap<>();

    public Map<Ingredient, Integer> getIngredientToQuantityMap() {
        return ingredientToQuantityMap;
    }

    public String getName() {
        return name;
    }
}
