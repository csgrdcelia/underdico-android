package com.esgi.project.underdico.models;

import java.io.Serializable;
import java.util.List;

public class Round implements Serializable {
    String definition;
    String[] obfuscatedWord;
    String nextPlayerId;

    public String getDefinition() {
        return definition;
    }

    public String getObfuscatedWord() {
        String word = "";
        for (String character : obfuscatedWord) {
            if(character == null) {
                word += "_";
            } else {
                word += character;
            }
            word += " ";
        }
        return word.substring(0, word.length() - 1);
    }

    public String getNextPlayerId() {
        return nextPlayerId;
    }
}
