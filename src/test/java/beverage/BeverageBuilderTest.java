package beverage;

import ingredients.Ingredient;
import org.junit.Assert;
import org.junit.Test;

public class BeverageBuilderTest {
    private BeverageBuilder beverageBuilder = new SimpleBeverageBuilder();

    @Test
    public void testName() {
        beverageBuilder.name("test");

        Beverage beverage = beverageBuilder.build();

        Assert.assertEquals("test", beverage.name);
    }

    @Test
    public void testAddIngredient() {
        beverageBuilder.addIngredient(Ingredient.HOT_MILK, 10);
        beverageBuilder.addIngredient(Ingredient.HOT_WATER, 10);

        Beverage beverage = beverageBuilder.build();

        Assert.assertTrue(beverage.ingredientToQuantityMap.containsKey(Ingredient.HOT_MILK));
        Assert.assertTrue(beverage.ingredientToQuantityMap.containsKey(Ingredient.HOT_WATER));
        Assert.assertEquals(10, (int) beverage.ingredientToQuantityMap.get(Ingredient.HOT_MILK));
        Assert.assertEquals(10, (int) beverage.ingredientToQuantityMap.get(Ingredient.HOT_WATER));
    }
}
