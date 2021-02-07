package machine;

import beverage.Beverage;
import beverage.SimpleBeverageBuilder;
import exceptions.InSufficientIngredientException;
import exceptions.UnsupportedIngredientException;
import ingredients.Ingredient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CoffeeMachineTest {
    //common beverages required for testing
    private Beverage customBeverage = new SimpleBeverageBuilder()
            .addIngredient(Ingredient.HOT_MILK, 50)
            .addIngredient(Ingredient.HOT_WATER, 50)
            .name("beverage 1")
            .build();
    private Beverage customBeverage2 = new SimpleBeverageBuilder()
            .addIngredient(Ingredient.HOT_MILK, 50)
            .addIngredient(Ingredient.HOT_WATER, 50)
            .addIngredient(Ingredient.TEA_LEAVES_SYRUP, 10)
            .name("beverage 2")
            .build();
    private Machine coffeeMachine;

    //initializing coffee machine required for each test case
    @Before
    public void init() {
        CoffeeMachineBuilder coffeeMachineBuilder = new CoffeeMachineBuilder();
        coffeeMachine = coffeeMachineBuilder
                .addIngredient(Ingredient.HOT_WATER, 150, 150)
                .addIngredient(Ingredient.HOT_MILK, 150, 100)
                .outlets(1)
                .build();
    }

    @Test
    public void testServe() {
        /*
        these beverages will tested on coffee machine
        first and third beverage should be served successfully
        second beverage should fail because TEA_LEAVES_SYRUP is absent in machine
        last beverage should fail because there is no HOT_WATER to serve it
         */
        Beverage[] testBeverages = new Beverage[]{
                customBeverage,
                customBeverage2,
                customBeverage,
                customBeverage
        };
        boolean[] shouldExecuteSuccessfully = new boolean[]{true, false, true, false};
        boolean[] shouldGetUnSupportedException = new  boolean[]{false, true, false, false};
        boolean[] shouldGetInSufficientIngredientException = new  boolean[]{false, false, false, true};

        for (int i=0; i<testBeverages.length; i++) {
            try {
                boolean served = coffeeMachine.serve(testBeverages[i]);
                Assert.assertEquals(shouldExecuteSuccessfully[i], served);
            } catch (InSufficientIngredientException e) {
                Assert.assertTrue(shouldGetInSufficientIngredientException[i]);
                Assert.assertEquals(e.inSufficientIngredientName(), Ingredient.HOT_MILK.getName());
            } catch (UnsupportedIngredientException e) {
                Assert.assertTrue(shouldGetUnSupportedException[i]);
                Assert.assertEquals(e.getUnSupportedIngredient(), Ingredient.TEA_LEAVES_SYRUP.getName());
            }
        }
    }

    @Test
    public void testServeForMultipleThreads() throws InterruptedException {
        final AtomicInteger atomicInteger = new AtomicInteger();
        Machine bigCoffeeMachine = new CoffeeMachineBuilder()
                .addIngredient(Ingredient.HOT_WATER, 500, 500)
                .addIngredient(Ingredient.HOT_MILK, 500, 450)
                .outlets(5)
                .build();
        int numberOfThreads = 50;
        //using latch to wait for all threads to finish
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        //creating threads to serve the beverages
        for (int i=0; i<numberOfThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    bigCoffeeMachine.serve(customBeverage);
                } catch (InSufficientIngredientException e) {
                    atomicInteger.getAndIncrement();
                } catch (UnsupportedIngredientException e) {
                    //should not occur as there is no beverage with unknown ingredient
                    Assert.fail();
                }
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
        Assert.assertEquals(41, atomicInteger.get());
    }

    @Test
    public void testPour() {
        try {
            coffeeMachine.pour(Ingredient.HOT_MILK, 55);
            Assert.assertEquals(150, coffeeMachine.availableQuantity(Ingredient.HOT_MILK));
        } catch (UnsupportedIngredientException e) {
            Assert.fail();
        }
        try {
            coffeeMachine.pour(Ingredient.GINGER_SYRUP, 50);
            Assert.fail();
        } catch (UnsupportedIngredientException e) {
            Assert.assertEquals(e.getUnSupportedIngredient(), Ingredient.GINGER_SYRUP.getName());
        }
    }

    @Test
    public void testPourForMultipleThreads() throws InterruptedException, UnsupportedIngredientException {
        Machine bigCoffeeMachine = new CoffeeMachineBuilder()
                .addIngredient(Ingredient.HOT_WATER, 500, 50)
                .outlets(5)
                .build();
        int numberOfThreads = 50;
        //using latch to wait for all threads to finish
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        //creating threads to test pour for multi threading
        for (int i=0; i<numberOfThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    bigCoffeeMachine.pour(Ingredient.HOT_WATER, 50);
                } catch (UnsupportedIngredientException e) {
                    Assert.fail();
                }
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
        Assert.assertEquals(500, bigCoffeeMachine.availableQuantity(Ingredient.HOT_WATER));
    }

    @Test
    public void testGetAvailability() {
        try {
            coffeeMachine.serve(customBeverage);
            int qty = coffeeMachine.availableQuantity(Ingredient.HOT_MILK);
            Assert.assertEquals(50, qty);
        } catch (UnsupportedIngredientException | InSufficientIngredientException e) {
            Assert.fail();
        }
        try {
            coffeeMachine.availableQuantity(Ingredient.GINGER_SYRUP);
            Assert.fail();
        } catch (UnsupportedIngredientException e) {
            Assert.assertEquals(e.getUnSupportedIngredient(), Ingredient.GINGER_SYRUP.getName());
        }
    }

    @Test
    public void testGetCapacity() {
        try {
            int qty = coffeeMachine.getCapacity(Ingredient.HOT_MILK);
            Assert.assertEquals(150, qty);
        } catch (UnsupportedIngredientException e) {
            Assert.fail();
        }
        try {
            coffeeMachine.getCapacity(Ingredient.GINGER_SYRUP);
            Assert.fail();
        } catch (UnsupportedIngredientException e) {
            Assert.assertEquals(e.getUnSupportedIngredient(), Ingredient.GINGER_SYRUP.getName());
        }
    }
}
