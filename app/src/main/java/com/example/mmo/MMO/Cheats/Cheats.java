package com.example.mmo.MMO.Cheats;

import android.util.Log;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Cheats{

    private Handler handler;

    public Cheats(Handler handler){
        this.handler = handler;
        try {
            add();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add() throws IOException{
        InputStream stream = null;
        stream = handler.getGame().getContext().getAssets().open("Worlds/Cheats.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String tilesAsString = new String();

        while(reader.ready()){
            tilesAsString += reader.readLine() + " ";
        }

        String[] numbers = tilesAsString.split(" ");

        if(numbers.length < 3)
            return;

        for(int i = 0; i < numbers.length - 2; i += 3){
            handler.getEq().getEq().addItem(new ContainerItem(Integer.parseInt(numbers[i]), handler, Integer.parseInt(numbers[i + 1]), Integer.parseInt(numbers[i + 2]), null));
        }

        stream.close();
        reader.close();
    }
}
