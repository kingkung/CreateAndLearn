package com.example.user.myapplication.tenandahalf;

import java.util.ArrayList;

public class TenDotFiveGame
{
    private final Deck deck;
    private final ArrayList<Card> playerHand;
    private final ArrayList<Card> dealerHand;
    private double playerTotal, dealerTotal;

    public TenDotFiveGame()
    {
        deck = new Deck();
        playerHand = new ArrayList<Card>();
        dealerHand = new ArrayList<Card>();
        playerTotal = dealerTotal = 0;
    }

    public void dealOpeningCards()
    {
        playerHit();
        dealerHit();
    }

    public Card getPlayerLastCard()
    {
        return playerHand.get(playerHand.size()-1);
    }

    public Card getDealerLastCard()
    {
        return dealerHand.get(dealerHand.size()-1);
    }

    /** Adds a card to player's hand
     *   PointTotal is updated
     */
    public Card playerHit()
    {
        Card card = deck.dealCard();
        playerHand.add(card);
        playerTotal += card.getPointValue();
        return card;
    }

    /** Adds a card to dealer's hand
     *   PointTotal is updated
     */
    public Card dealerHit()
    {
        Card card = deck.dealCard();
        dealerHand.add(card);
        dealerTotal += card.getPointValue();
        return card;
    }

    public boolean dealerShouldHit()
    {
        return dealerTotal <= 7;
    }

    public String getPlayerHand()
    {
        String result = "\nPlayer Hand:\n";

        for(Card card : playerHand)
            result += "\n" + card.toString();

        return result;
    }

    public String getDealerHand()
    {
        String result = "\nDealer Hand:\n";

        for(Card card : dealerHand)
            result += "\n" + card.toString();

        return result;
    }

    public double getPlayerTotal()
    {
        return playerTotal;
    }

    public double getDealerTotal()
    {
        return dealerTotal;
    }

    public boolean playerIsBusted()
    {
        return playerTotal > 10.5;
    }

    public boolean dealerIsBusted()
    {
        return dealerTotal > 10.5;
    }

    public String getWinner()
    {
        String result = "";

        if(playerTotal > 10.5)
            result = "Player Busted!";
        else if(dealerTotal > 10.5)
            result = "Dealer Busted!";
        else if(playerTotal > dealerTotal)
            result = "Player wins!";
        else if(playerTotal < dealerTotal)
            result = "Dealer wins";
        else if(playerTotal == dealerTotal)
            result = "Tie goes to the player!";
        else
            result = "unexpected";

        return result;
    }
}