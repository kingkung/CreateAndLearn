package com.example.user.myapplication.foodorders;

import java.text.NumberFormat;

public class FoodOrder
{
    private int soda, pizza, fries, salad, bread;
    public FoodOrder()
    {
        soda = 0;
        pizza = 0;
        fries = 0;
        salad = 0;
        bread = 0;

    }
    public void addSoda(int num)
    {
        soda = soda + num;
    }
    public void addPizza(int num)
    {
        pizza = pizza + num;
    }
    public void addFries(int num)
    {
        fries = fries + num;
    }
    public void addSalad(int num)
    {
        salad = salad + num;
    }
    public void addBread(int num)
    {
        bread = bread + num;
    }
    public double getValue()
    {
        double total = (soda * .75) + (pizza * 1.25) + (fries * 1.00) + (salad * 1.25) + (bread * .75);
        return total;
    }
    public String getStringValue()
    {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String strValue = fmt.format(getValue());
        return strValue;
    }
    public void completeOrder()
    {
        soda = 0;
        pizza = 0;
        fries = 0;
        salad = 0;
        bread = 0;
    }
    public String toString()
    {
        String result = "Soda: " + soda;
        result += " Pizza: " + pizza;
        result += " Fries: " + fries;
        result += " Salad: " + salad;
        result += " Bread: " + bread;
        return result;
    }
}