package exceptions;

public class InSufficientIngredientException extends Exception {
    private String name;

    public InSufficientIngredientException(String ingredientName) {
        super(String.format("ingredient %s not available in sufficient amount", ingredientName));
        name = ingredientName;
    }

    public String inSufficientIngredientName() {
        return name;
    }
}
