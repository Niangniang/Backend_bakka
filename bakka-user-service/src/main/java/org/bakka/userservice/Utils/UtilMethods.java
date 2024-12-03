package org.bakka.userservice.Utils;

import org.springframework.stereotype.Service;

import java.util.Random;


public  class UtilMethods {

    // Méthode pour générer un identifiant
    public static String generateIdentifiant() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder id = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            id.append(randomChar);
        }

        return id.toString();
    }

    public static String generateNumero() {
        String characters = "1234567890";
        StringBuilder id = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            id.append(randomChar);
        }

        return id.toString();
    }

    public static String generateCVV() {
        String characters = "1234567890";
        StringBuilder id = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            id.append(randomChar);
        }

        return id.toString();
    }
    public static String generateOtp() {
        String characters = "1234567890";
        StringBuilder id = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            id.append(randomChar);
        }

        return id.toString();
    }

    public static double generateSolde() {
        Random random = new Random();
        return 15000 + (random.nextDouble() * (40000 - 15000));
    }


}
