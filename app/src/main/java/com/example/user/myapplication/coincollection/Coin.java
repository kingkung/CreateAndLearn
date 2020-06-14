package com.example.user.myapplication.coincollection;

//Coin Collection
//Classes as Blueprints
//Coin Instance Attributes: age, denomination, condition, numberInExistence
//Coin Instance Methods: isEqual, isBetter (if/else statements)
//Coin Collection Class: ArrayList attributed, addCoin, showCollection

import java.util.Arrays;

public class Coin
{
    private int year, denomination, numberInExistence;
    private String condition;
    private final String[] coinGrades = {"Good", "Very Good", "Fine", "Very Fine",
            "Extremely Fine", "About Uncirculated", "Mint State"};

    public Coin()
    {
        year = denomination = numberInExistence = 0;
        condition = null;
    }

    public Coin(int year, int denomination, String condition, int numberInExistence)
    {
        this.year = year;
        this.denomination = denomination;
        this.condition = condition;
        this.numberInExistence = numberInExistence;
    }

    public boolean equals(Coin other)
    {
        if(year == other.year && denomination == other.denomination
                && condition.equals(other.condition) && numberInExistence == other.numberInExistence)
            return true;
        return false;
    }

    // Assign priorities based on criteria
    // In this case condition, numberInExistence and year
    public boolean isBetter(Coin other)
    {
        if(numberInExistence != other.numberInExistence)
            if(numberInExistence < other.numberInExistence)
                return true;
            else
                return false;
        if(year != other.year)
            if(year < other.year)
                return true;
            else
                return false;
        if(Arrays.asList(coinGrades).indexOf(condition) > Arrays.asList(coinGrades).indexOf(other.condition))
            return true;
        return false;
    }

    public String toString()
    {
        String result = "\nYear: " + year;
        result += "\nDenomination: " + denomination;
        result += "\nConditon: " + condition;
        result += "\nNumber In Existence: " + numberInExistence + "\n\n";
        return result;
    }
}
