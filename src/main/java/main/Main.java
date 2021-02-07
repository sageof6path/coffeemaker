package main;

import beverage.Beverage;
import exceptions.InSufficientIngredientException;
import exceptions.UnsupportedIngredientException;
import ingredients.Ingredient;
import machine.CoffeeMachineBuilder;
import machine.Machine;
import machine.MachineBuilder;
import util.CommonBeverages;

import java.util.concurrent.TimeUnit;

public class Main {
    //written main to simulate coffee machine
    public static void main(String[] args) {
        MachineBuilder machineBuilder = new CoffeeMachineBuilder();
        Machine coffeeMachine = machineBuilder
                .addIngredient(Ingredient.HOT_WATER, 500, 500)
                .addIngredient(Ingredient.HOT_MILK, 500, 500)
                .addIngredient(Ingredient.GINGER_SYRUP, 100, 100)
                .addIngredient(Ingredient.SUGAR_SYRUP, 100, 100)
                .addIngredient(Ingredient.TEA_LEAVES_SYRUP, 100, 100)
                .outlets(3)
                .build();
        Beverage []beverages = new Beverage[] {
                CommonBeverages.getBlackTea(), CommonBeverages.getGreenTea(),
                CommonBeverages.getHotCoffee(), CommonBeverages.getHotTea()
        };
        //creating multiple client threads to get beverage
        for (int i=0; i<4; i++) {
            final Beverage beverage = beverages[i];
            Runnable runnable = () -> {
                try {
                    coffeeMachine.serve(beverage);
                } catch (InSufficientIngredientException | UnsupportedIngredientException e) {
                    System.out.println(e.getMessage());
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
