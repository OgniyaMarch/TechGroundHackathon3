package com.example.EVeteranBot.service;

public enum BotFunctionality {

    REGISTRATION_USER("register"),
    ASK_QUESTION("services");



    String function;
    BotFunctionality( String function){
        this.function = function;
    }

    @Override
    public String toString() {
        return  "/" + function + "\n";
    }

    public static String printFunctionality() {
        String functions = "";
        for (BotFunctionality functionality : BotFunctionality.values()) {
            functions += functionality.toString();
        }
        return functions;
    }

    public String getFunction() {
        return function;
    }

}
