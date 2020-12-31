package com.example.user.myapplication.huntthewumpus;

public class Cave
{
    private String caveName;
    private boolean visited, wumpus, bats, pit;

    /** Constructs a cave with the specified characteristics.
     *	By default, the cave is unvisited and empty.
     *	@param name - the name of the cave
     */
    public Cave(String name)
    {
        caveName = name;
        visited = wumpus = bats = pit = false;

    }

    public boolean hasWumpus()
    {
        return wumpus;
    }

    public boolean hasBats()
    {
        return bats;
    }

    public boolean hasPit()
    {
        return pit;
    }

    public boolean isEmpty()
    {
        return !wumpus && !bats && !pit;
    }

    public void setWumpus(boolean isWump)
    {
        wumpus = isWump;
    }

    public void setBats(boolean isBat)
    {
        bats = isBat;
    }

    public void setPit(boolean isPit)
    {
        pit = isPit;
    }

    /**	Accesses the name of the cave.
     *	@return	the cave name */
    public String getCaveName()
    {
        return caveName;
    }

    /** Determines whether this cave has been visited previously.
     *	@return	true if it has been visited, otherwise false */
    public boolean hasBeenVisited()
    {
        return visited;
    }

    /** Marks the cave as having been visited.*/
    public void markAsVisited()
    {
        visited = true;
    }

}

