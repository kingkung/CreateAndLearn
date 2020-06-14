package com.example.user.myapplication.coincollection;

import java.util.ArrayList;

public class CoinCollection
{
    private ArrayList<Coin> collection;

    public CoinCollection()
    {
        collection = new ArrayList<Coin>();
    }

    public void addCoin(Coin piece)
    {
        collection.add(piece);
    }

    public Coin getCoin(int index)
    {
        return collection.get(index);
    }

    public String toString()
    {
        String result = "";
        int i=0;

        for(Coin c : collection)
        {
            result += "Coin " + i + " \n" + c.toString();
            i++;
        }

        return result;
    }
}
