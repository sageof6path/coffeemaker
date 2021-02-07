package exceptions;

public class UnsupportedIngredientException extends Exception {
    private String name;

    public UnsupportedIngredientException(String ingredientName) {
        super("operation unsupported for ingredient : " + ingredientName);
        this.name = ingredientName;
    }

    public String getUnSupportedIngredient() {
        return name;
    }
}
