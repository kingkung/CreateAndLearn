package com.example.user.myapplication.lunchcombo;

public class LunchMenu {
    private final Sandwich[] sandwiches;
    private final Salad[] salads;
    private final Drink[] drinks;

    public LunchMenu() {
        Sandwich hamburger = new Sandwich("Hamburger", 3.00);
        Sandwich cheeseburger = new Sandwich("Cheeseburger", 3.50);
        Sandwich chickenSandwich = new Sandwich("Chicken sandwich", 3.00);
        Sandwich clubSandwich = new Sandwich("Club Sandwich", 2.75);
        sandwiches =
                new Sandwich[] {hamburger, cheeseburger, chickenSandwich, clubSandwich};

        Salad caesar = new Salad("Caesar salad", 1.50);
        Salad cobb = new Salad("Cobb salad", 1.25);
        Salad fruit = new Salad("Fruit salad", 2.00);
        Salad chicken = new Salad("Chicken salad", 2.50);
        salads = new Salad[] {caesar, cobb, fruit, chicken};

        Drink juice = new Drink("Juice", 1.50);
        Drink coke = new Drink("Coke", 1.00);
        Drink icedTea = new Drink("Iced tea", 1.00);
        Drink gingerAle = new Drink("Ginger ale", 1.25);
        drinks = new Drink[] {juice, coke, icedTea, gingerAle};
    }

    public LunchCombo createCombo() {
        Sandwich sandwich = sandwiches[(int)(Math.random() * sandwiches.length)];
        Salad salad = salads[(int)(Math.random() * salads.length)];
        Drink drink = drinks[(int)(Math.random() * drinks.length)];
        return new LunchCombo(sandwich, salad, drink);
    }
}
