package com.example.user.myapplication.huntthewumpus;

/**
 * Class that models a maze of caves for the "Hunt the Wumpus" game.
 */
public class CaveMaze
{
    private Cave currentCave;
    private Cave[][] caves;
    private int numGrenades;
    private int numWumpi;
    private int row, col;
    private final int NUMROWS = 5, NUMCOLS = 5;
    private boolean visibleFoes = true;

    /**
     * Constructs a CaveMaze.  Initially, there is 1 pit, 1 swarm of bats, and
     * between 1 and 3 wumpi randomly placed in the maze.  The player starts out in the cave numbered 0 with
     * three times as many grenades as there are wumpi.
     */
    public CaveMaze()
    {
        caves = new Cave[NUMROWS][NUMCOLS];
        row = NUMROWS/2;
        col = NUMCOLS/2;

        for (int i = 0; i < NUMROWS; i++)
        {
            for(int j=0; j< NUMCOLS; j++)
            {
                caves[i][j] = new Cave("Cave " + i + j);
            }
        }
        numWumpi = 3;
        numGrenades = numWumpi * 3;

        // Place the Wumpi
        for(int i=0; i<numWumpi; i++)
        {
            int nextRow = (int)(Math.random() * NUMROWS);
            int nextCol = (int)(Math.random() * NUMCOLS);

            while(!caves[row][col].isEmpty())
            {
                nextRow = (int)(Math.random() * NUMROWS);
                nextCol = (int)(Math.random() * NUMCOLS);
            }

            caves[nextRow][nextCol].setWumpus(true);
        }

        // Place a Bat
        int nextRow = (int)(Math.random() * NUMROWS);
        int nextCol = (int)(Math.random() * NUMCOLS);

        while(!caves[nextRow][nextCol].isEmpty())
        {
            nextRow = (int)(Math.random() * NUMROWS);
            nextCol = (int)(Math.random() * NUMCOLS);
        }

        caves[nextRow][nextCol].setBats(true);

        // Place the pit
        nextRow = (int)(Math.random() * NUMROWS);
        nextCol = (int)(Math.random() * NUMCOLS);

        while(!caves[nextRow][nextCol].isEmpty())
        {
            nextRow = (int)(Math.random() * NUMROWS);
            nextCol = (int)(Math.random() * NUMCOLS);
        }

        caves[nextRow][nextCol].setPit(true);

        currentCave = caves[row][col]; // set starting location
        currentCave.markAsVisited(); // mark it visited
    }

    public int getGrenades()
    {
        return numGrenades;
    }

    public int getNumWumpi()
    {
        return numWumpi;
    }

    public void setVisibleFoes(boolean state)
    {
        visibleFoes = state;
    }

    /**
     * Moves the player from the current cave along the specified tunnel.  The new location must be marked
     * as visited and the appropriate action taken if the new location is not empty. <br>
     * Note: if the tunnel # is not 1-3, then an error message should be returned.
     *   @param dir the direction to be traversed (1-3)
     *   @return a status message describing the result of the move attempt
     */
    public String move(String dir)
    {
        if(dir.equals("N"))
        {
            row--;
            if(row < 0)
                row = NUMROWS-1;
        }
        else if(dir.equals("S"))
        {
            row++;
            if(row > NUMROWS -1)
                row = 0;
        }
        else if(dir.equals("E"))
        {
            col++;
            if(col > NUMCOLS - 1)
                col = 0;
        }
        else if(dir.equals("W"))
        {
            col--;
            if(col < 0)
                col = NUMCOLS - 1;
        }
        else
            return "Invalid Direction";

        currentCave = caves[row][col];
        currentCave.markAsVisited();

        if(currentCave.hasWumpus())
            return "Oh no there is a Wumpus here!\nThe Wumpus devours you... and now you are dead :(";
        else if(currentCave.hasPit())
            return "Ooops... you slid down a bottomless pit... and now you are dead :(";
        else if(currentCave.hasBats())
        {
            row = (int)(Math.random() * NUMROWS);
            col = (int)(Math.random() * NUMCOLS);

            currentCave = caves[row][col]; // set new location
            currentCave.markAsVisited(); // mark it visited
            if(currentCave.hasWumpus())
                return "Magical bats transported you to a room with a wumpus!";
            else if(currentCave.hasPit())
                return "Magical bats transported you to a room with a deep pit!";
            else
                return ("Magical bats are here and they lift you to a new location...");
        }
        else
            return "";
    }

    public String tossGrenade(String direction)
    {
        String result = "";

        if(direction.equals("TN"))
        {
            int northRow = row - 1;
            if(northRow < 0)
                northRow = NUMROWS - 1;
            if(caves[northRow][col].hasWumpus())
            {
                caves[northRow][col].setWumpus(false);
                result = "You toss a grenade to the north and BOOM! A WUMPUS was detroyed!";
                numWumpi--;
            }
            else
                result = "BOOOM!!! ... unfortunately, there was no Wumpus here anyway :(";
        }
        else if(direction.equals("TS"))
        {
            int southRow = row + 1;
            if(southRow > NUMROWS - 1)
                southRow = 0;
            if(caves[southRow][col].hasWumpus())
            {
                caves[southRow][col].setWumpus(false);
                result = "You toss a grenade to the south and BOOM! A WUMPUS was detroyed!";
                numWumpi--;
            }
            else
                result = "BOOOM!!! ... unfortunately, there was no Wumpus here anyway :(";
        }
        else if(direction.equals("TE"))
        {
            int eastCol = col + 1;
            if(eastCol > NUMCOLS - 1)
                eastCol = 0;
            if(caves[row][eastCol].hasWumpus())
            {
                caves[row][eastCol].setWumpus(false);
                result = "You toss a grenade to the east and BOOM! A WUMPUS was detroyed!";
                numWumpi--;
            }
            else
                result = "BOOOM!!! ... unfortunately, there was no Wumpus here anyway :(";
        }
        else if(direction.equals("TW"))
        {
            int westCol = col - 1;
            if(westCol < 0)
                westCol = NUMCOLS - 1;
            if(caves[row][westCol].hasWumpus())
            {
                caves[row][westCol].setWumpus(false);
                result = "You toss a grenade to the west and BOOM! A WUMPUS was detroyed!";
                numWumpi--;
            }
            else
                result = "BOOOM!!! ... unfortunately, there was no Wumpus here anyway :(";
        }

        numGrenades--;

        return result;
    }

    public String getHints()
    {
        String result = "";
        int northRow = row - 1;
        int southRow = row + 1;
        int eastCol = col + 1;
        int westCol = col - 1;

        if(northRow < 0)
            northRow = NUMROWS - 1;
        if(southRow > NUMROWS - 1)
            southRow = 0;
        if(eastCol > NUMCOLS - 1)
            eastCol = 0;
        if(westCol < 0)
            westCol = NUMCOLS - 1;

        Cave northCave = caves[northRow][col];
        Cave southCave = caves[southRow][col];
        Cave eastCave = caves[row][eastCol];
        Cave westCave = caves[row][westCol];

        if(northCave.hasWumpus() || southCave.hasWumpus() || eastCave.hasWumpus() || westCave.hasWumpus())
            result = "\nI smell a Wumpus nearby!";
        if(northCave.hasBats() || southCave.hasBats() || eastCave.hasBats() || westCave.hasBats())
            result += "\nBats nearby!";
        if(northCave.hasPit() || southCave.hasPit() || eastCave.hasPit() || westCave.hasPit())
            result += "\nI feel a draft";

        return result;
    }

    /**
     * Reports whether the player is still alive.
     *   @return true if alive, false otherwise
     */
    public boolean stillAlive()
    {
        if(currentCave.hasWumpus())
            return false;
        else if(currentCave.hasPit())
            return false;
        return true;
    }

    /**
     * Reports whether there are any wumpi remaining.
     *   @return true if there are wumpi remaining in the maze, false otherwise
     */
    public boolean stillWumpi()
    {
        return numWumpi > 0;
    }

    public String toString()
    {
        String result = "DUNGEON MAP\n\n";

        for(int i=0; i<NUMROWS; i++)
        {
            for(int j=0; j<NUMCOLS; j++)
            {
                if(caves[i][j] == currentCave)
                    result += "\tM";
                else if(caves[i][j].hasWumpus() && visibleFoes)
                    result += "\tW";
                else if(caves[i][j].hasBats() && visibleFoes)
                    result += "\tB";
                else if(caves[i][j].hasPit() && visibleFoes)
                    result += "\tP";
                else
                    result += "\t*";
            }
            result += "\n";
        }

        return result;
    }
}