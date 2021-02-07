package machine;

import beverage.Beverage;
import exceptions.InSufficientIngredientException;
import exceptions.UnsupportedIngredientException;
import ingredients.Ingredient;

public interface Machine {
    public boolean serve(Beverage beverage) throws InSufficientIngredientException,
            UnsupportedIngredientException;
    public void pour(Ingredient ingredient, int quantity) throws UnsupportedIngredientException;
    public int getCapacity(Ingredient ingredient) throws UnsupportedIngredientException;
    public int availableQuantity(Ingredient ingredient) throws UnsupportedIngredientException;
}
