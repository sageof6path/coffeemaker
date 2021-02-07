package util;

import beverage.Beverage;
import beverage.SimpleBeverageBuilder;
import ingredients.Ingredient;

public class CommonBeverages {
    public static Beverage getHotTea() {
        SimpleBeverageBuilder beverageBuilder = new SimpleBeverageBuilder();
        return beverageBuilder
                .name("tea")
                .addIngredient(Ingredient.HOT_WATER, 200)
                .addIngredient(Ingredient.HOT_MILK, 200)
                .addIngredient(Ingredient.GINGER_SYRUP, 10)
                .addIngredient(Ingredient.SUGAR_SYRUP, 10)
                .addIngredient(Ingredient.TEA_LEAVES_SYRUP, 30)
                .build();
    }

    public static Beverage getHotCoffee() {
        SimpleBeverageBuilder beverageBuilder = new SimpleBeverageBuilder();
        return beverageBuilder
                .name("hot coffee")
                .addIngredient(Ingredient.HOT_WATER, 100)
                .addIngredient(Ingredient.HOT_MILK, 400)
                .addIngredient(Ingredient.GINGER_SYRUP, 30)
                .addIngredient(Ingredient.SUGAR_SYRUP, 50)
                .addIngredient(Ingredient.TEA_LEAVES_SYRUP, 30)
                .build();
    }

    public static Beverage getBlackTea() {
        SimpleBeverageBuilder beverageBuilder = new SimpleBeverageBuilder();
        return beverageBuilder
                .name("black coffee")
                .addIngredient(Ingredient.HOT_WATER, 300)
                .addIngredient(Ingredient.GINGER_SYRUP, 30)
                .addIngredient(Ingredient.SUGAR_SYRUP, 50)
                .addIngredient(Ingredient.TEA_LEAVES_SYRUP, 30)
                .build();
    }

    public static Beverage getGreenTea() {
        SimpleBeverageBuilder beverageBuilder = new SimpleBeverageBuilder();
        return beverageBuilder
                .name("green tea")
                .addIngredient(Ingredient.HOT_WATER, 100)
                .addIngredient(Ingredient.GINGER_SYRUP, 30)
                .addIngredient(Ingredient.SUGAR_SYRUP, 50)
                .addIngredient(Ingredient.GREEN_MIXTURE, 30)
                .build();
    }
}
