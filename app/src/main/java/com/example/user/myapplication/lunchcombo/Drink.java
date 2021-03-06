package com.example.user.myapplication.lunchcombo;

public class Drink {

    private final String name;
    private final double price;

    public Drink (String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format(name + " - $%.2f", price);
    }
}
