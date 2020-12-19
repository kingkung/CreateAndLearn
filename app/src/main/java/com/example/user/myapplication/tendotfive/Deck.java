package com.example.user.myapplication.tendotfive;
import com.example.user.myapplication.R;

import java.util.List;
import java.util.ArrayList;

public class Deck
{
    private static final int NUMBER_OF_CARDS = 52;
    private static final String[] RANKS =  {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final double[] VALUES = { 1,   2,   3,   4,   5,   6,   7,   8,   9,   10,  .5,  .5 , .5 };
    private static final String[] SUITS = {"S"}; // look up working Unicode chars
    private static final int[] IMAGE_RES_ID = {
        R.drawable.spade_ace,
        R.drawable.spade2,
        R.drawable.spade3,
        R.drawable.spade4,
        R.drawable.spade5,
        R.drawable.spade6,
        R.drawable.spade7,
        R.drawable.spade8,
        R.drawable.spade9,
        R.drawable.spade10,
        R.drawable.spade_jack,
        R.drawable.spade_queen,
        R.drawable.spade_king,
    };

    private final List<Card> cards;

    public Deck()
    {
        cards = new ArrayList<>();

        for(String suit : SUITS)
        {
            for(int i=0; i<RANKS.length; i++)
            {
                cards.add(new Card(RANKS[i], suit, VALUES[i], IMAGE_RES_ID[i]));
            }
        }
        shuffleDeck();
    }

    public void shuffleDeck()
    {
        for(int i=0; i<cards.size(); i++)
        {
            int randPos = (int)(Math.random() * cards.size());

            //swap the current card with a randomly selected card
            Card temp = cards.get(i);
            cards.set(i, cards.get(randPos));
            cards.set(randPos, temp);
        }
    }

    public Card dealCard()
    {
        if(cards.size() == 0)
            return null;
        else
        {
            Card card = cards.remove(cards.size() - 1);
            return card;
        }
    }

    public String toString()
    {
        String result = "";

        for(int i=0; i<cards.size(); i++)
        {
            result += cards.get(i).toString() + "\n";
        }

        return result;
    }
}