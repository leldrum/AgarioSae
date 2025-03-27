package com.example.libraries.options;

import java.util.Random;

public class NameGenerator {

    private static final String[] ADJECTIVES = {
            "Dark", "Mighty", "Swift", "Fierce", "Silent", "Brave", "Cunning", "Vicious", "Shadow", "Wild"
    };

    private static final String[] NOUNS = {
            "Predator", "Hunter", "Beast", "Ghost", "Warrior", "Phantom", "Reaper", "Knight", "Sorcerer", "Titan"
    };

    public static String generateRandomName() {
        Random rand = new Random();
        String adjective = ADJECTIVES[rand.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[rand.nextInt(NOUNS.length)];

        return adjective + " " + noun;
    }

}
