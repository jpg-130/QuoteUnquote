package com.example.android.quoteunquote;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.Toast;
import java.util.Random;



public class QuoteUnquoteDictionary {

    public String Quote,newQuote,encrypt;
    public HashMap<Character,Character> dict;
    public HashMap<Integer,String> quotelist;
    public ArrayList<Integer>quotesGiven;

    Random random = new Random();

    public QuoteUnquoteDictionary(InputStream reader) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(reader));
        String line;
        int i=1;
        quotelist = new HashMap<>();
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= 1){
                quotelist.put(i,line.trim());
                i++;}
        }
    }


    public String EncryptText(){
        String str;
        encrypt="";
        dict = new HashMap<>(30);
        str=getNewQuote();
        //str=str.toUpperCase();
        for (char i='A'; i<='Z'; i++){

            char j=generate();
            while (dict.containsValue(j)){
                j=generate();
                while(i==j){
                    j=generate();
                }
            }
            if (!dict.containsValue(j)){
                dict.put(i,j);
            }
        }
        dict.put(' ',' ');
        dict.put(',',',');
        dict.put('.','.');
        dict.put('\'','\'');
        for (int i=0; i<str.length();i++){
            String newStr=(dict.get(str.charAt(i))).toString();
            encrypt=encrypt.concat(newStr);
            System.out.print(encrypt);
        }
        return encrypt;
    }

    public char generate(){
        String atoz="QWERTYUIOPLKJHGFDSAZXCVBNM";
        char j = atoz.charAt(random.nextInt(atoz.length()));
        return j;
    }
    public String getNewQuote() {
        newQuote=quotelist.get(random.nextInt(quotelist.size()));
        Quote=newQuote;
        return newQuote;

    }

    public  Boolean CheckAns(String answer){
        //if(quotelist.containsValue(answer))
        if (answer.equals(Quote))
            return true;
        else
            return false;
    }

    public String getQuote() {
        return Quote;
    }

    public String getEncrypt() {
        return encrypt;
    }
}

