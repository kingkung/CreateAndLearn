package com.example.user.myapplication.lunchcombo;

public class LunchCombo {
    private final Sandwich sandwich;
    private final Salad salad;
    private final Drink drink;

    public LunchCombo(Sandwich sandwich, Salad salad, Drink drink) {
        this.sandwich = sandwich;
        this.salad = salad;
        this.drink = drink;
    }

    @Override
    public String toString() {
        return sandwich.toString() + "\n" + salad.toString() + "\n" + drink.toString();
    }

    public double getTotal() {
        return sandwich.getPrice() + salad.getPrice() + drink.getPrice();
    }
}
