package com.example.EVeteranBot.bd;

import java.util.Random;

public class IDgenerator {
    private final String characters = "QWERTYUIOPASDFGHJKLZXCVBNM" +
            "qwertyuiopasdfghjklzxcvbnm" +
            "1234567890";

    private Random random = new Random();
    private int sizeOfId = 8;

    public IDgenerator() {

    }

    private char getChar(int index){
        char[] charArray = characters.toCharArray();
        return charArray[index];
    }
    public String getID(){
        StringBuilder passwordSB = new StringBuilder();

        int max_randomNumb = characters.length();
        for (int i = 0; i < sizeOfId; i++){
            passwordSB.append(getChar(random.nextInt(max_randomNumb)));
        }
        return passwordSB.toString();
    }
}
