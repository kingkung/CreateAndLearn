package com.example.user.myapplication.tendotfive;

public class Card
{
    private final String rank;
    private final String suit;
    private final double pointValue;
    private final int imageResId;

    public Card(String rank, String suit, double pointValue, int imageResId)
    {
        this.rank = rank;
        this.suit = suit;
        this.pointValue = pointValue;
        this.imageResId = imageResId;
    }

    public String getRank()
    {
        return rank;
    }

    public String getSuit()
    {
        return suit;
    }

    public double getPointValue()
    {
        return pointValue;
    }

    public int getImageResId()
    {
        return imageResId;
    }

    public String toString()
    {
        String strCard;

        strCard = " --------\n";
        if(rank.equals("10"))
            strCard+="|      " + rank + "|\n";
        else
            strCard+="|      " + rank + " |\n";
        strCard+="|        |\n";
        strCard+="|        |\n";
        //strCard+="|    " + suit + "   |\n";
        strCard+="|        |\n";
        strCard+="|        |\n";
        if(rank.equals("10"))
            strCard+="| " + rank + "     |\n";
        else
            strCard+="| " + rank + "      |\n";
        strCard+=" --------\n";

        return strCard;
    }
}
