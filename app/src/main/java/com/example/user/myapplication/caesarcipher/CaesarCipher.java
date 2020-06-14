package com.example.user.myapplication.caesarcipher;

public class CaesarCipher
{
    public static String encode(String orig, int shift)
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String result = "";
        orig = orig.toLowerCase();
        int loc;


        for(int i=0; i<orig.length(); i++)
        {
            if(alphabet.contains(orig.charAt(i)+""))
            {
                loc = alphabet.indexOf(orig.charAt(i)) + shift;
                if(loc > 25)
                    loc = loc % 26;
                if(loc < 0)
                    loc = 26 + loc;
                result += alphabet.charAt(loc);
            }
            else
                result += orig.charAt(i);
        }

        return result;
    }

    public static String decode(String orig, int shift)
    {
        return encode(orig, -shift);
    }
}