package UnitTestCases;

import java.util.Scanner;

public class TestResultsShouldBeTrueOrFalse {

    private static Scanner keyboard;

    public Boolean WhatShouldBeTheOutputs() {
        try {
            System.out.println("Should The dates be correct? \n Type true for yes \n Type false for no");
            keyboard = new Scanner(System.in);
            Boolean TrueorFalse = keyboard.nextBoolean();
            return TrueorFalse;
        } catch (Exception e) {
            System.out.println("Entered Value is not Boolean");
            return WhatShouldBeTheOutputs();
        }
    }
}
