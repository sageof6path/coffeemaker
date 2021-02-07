package machine;

import beverage.Beverage;
import exceptions.InSufficientIngredientException;
import exceptions.UnsupportedIngredientException;
import ingredients.Ingredient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CoffeeMachine implements Machine {

    //map stores maximum quantity that coffee machine can store per ingredient
    Map<Ingredient, Integer> ingredientCapacityMap = new ConcurrentHashMap<>();
    //map stores quantity of ingredients available in machine
    Map<Ingredient, Integer> ingredientQuantityMap = new ConcurrentHashMap<>();
    int outlets;
    Semaphore semaphore;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //checking if all ingredients are supported by machine
    private void validateIfPossibleToServe(Beverage beverage) throws UnsupportedIngredientException {
        try {
            readWriteLock.readLock().lock();
            for (Map.Entry<Ingredient, Integer> ingredientWithQty : beverage.getIngredientToQuantityMap().entrySet()) {
                if (!ingredientCapacityMap.containsKey(ingredientWithQty.getKey())) {
                    throw new UnsupportedIngredientException(ingredientWithQty.getKey().getName());
                }
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    //using this method as real world coffee preparation simulation
    private void prepare(Beverage beverage) throws InterruptedException {
        System.out.println("preparing beverage : "+beverage.getName());
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        System.out.println("done beverage : "+beverage.getName());
    }

    //changing value for ingredient's availablity
    private void useAllIngredients(Beverage beverage) throws InSufficientIngredientException {
        try {
            readWriteLock.writeLock().lock();
            for (Map.Entry<Ingredient, Integer> ingredientWithQty : beverage.getIngredientToQuantityMap().entrySet()) {
                if (ingredientQuantityMap.get(ingredientWithQty.getKey()) < ingredientWithQty.getValue()) {
                    throw new InSufficientIngredientException(ingredientWithQty.getKey().getName());
                }
            }
            for (Map.Entry<Ingredient, Integer> ingredientWithQty : beverage.getIngredientToQuantityMap().entrySet()) {
                int currentQty = ingredientQuantityMap.get(ingredientWithQty.getKey());
                currentQty = currentQty - ingredientWithQty.getValue();
                ingredientQuantityMap.put(ingredientWithQty.getKey(), currentQty);
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /*
    algorithm to serve the coffee
     */
    @Override
    public boolean serve(Beverage beverage) throws InSufficientIngredientException, UnsupportedIngredientException {
        try {
            semaphore.acquire();
            validateIfPossibleToServe(beverage);
            useAllIngredients(beverage);
            prepare(beverage);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            semaphore.release();
        }
    }

    @Override
    public void pour(Ingredient ingredient, int quantity) throws UnsupportedIngredientException {
        if (!ingredientQuantityMap.containsKey(ingredient)) {
            throw new UnsupportedIngredientException(ingredient.getName());
        }
        try {
            readWriteLock.writeLock().lock();
            int currentQty = ingredientQuantityMap.get(ingredient);
            ingredientQuantityMap
                    .put(ingredient, Math.min(ingredientCapacityMap.get(ingredient), currentQty + quantity));
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public int getCapacity(Ingredient ingredient) throws UnsupportedIngredientException {
        if (!ingredientQuantityMap.containsKey(ingredient)) {
            throw new UnsupportedIngredientException(ingredient.getName());
        }
        return ingredientCapacityMap.get(ingredient);
    }

    @Override
    public int availableQuantity(Ingredient ingredient) throws UnsupportedIngredientException {
        if (!ingredientQuantityMap.containsKey(ingredient)) {
            throw new UnsupportedIngredientException(ingredient.getName());
        }
        return ingredientQuantityMap.get(ingredient);
    }
}
