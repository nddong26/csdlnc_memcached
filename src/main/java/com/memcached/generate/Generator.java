package com.memcached.generate;

import java.util.Random;

public class Generator {
    private static String[] Beginning = {"Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk"};
    private static String[] Middle = {"air", "ir", "mi", "sor", "mee", "clo",
            "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
            "marac", "zoir", "slamar", "salmar", "urak"};
    private static String[] End = {"d", "ed", "ark", "arc", "es", "er", "der",
            "tron", "med", "ure", "zur", "cred", "mur"};

    private static Random rand = new Random();

    public static String generateName() {

        return Beginning[rand.nextInt(Beginning.length)] +
                Middle[rand.nextInt(Middle.length)] +
                End[rand.nextInt(End.length)];

    }

    public static Double generatedPrice() {
        return rand.nextDouble() * 1000;
    }

    public static int generatedSex() {
        return Math.random() < 0.5 ? 0 : 1;
    }

    public static int getRandomNumberBellow(int max) {

        return rand.nextInt((max)) + 1;
    }

    public static String createRandomDate() {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(2000, 2018);
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
    }

    private static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }


    public static void main(String[] args) {
        System.out.println(createRandomDate());
    }
}
