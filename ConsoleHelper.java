package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName()+".resources.common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static void printExitMessage() {
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }

    public static String readString() throws InterruptOperationException {
        try {
            String userInput = bis.readLine();
            if (userInput.equalsIgnoreCase("exit")) {
                throw new InterruptOperationException();
            }
            return userInput;
        } catch (IOException e) {

        }

        return null;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        String currency = "";
        System.out.println("Enter currency code: ");

        try {
            currency = bis.readLine();
            while (currency.length() != 3) {
                System.out.println("Currency code is too long. Try again: ");
                currency = bis.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currency.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {

        writeMessage("Input nominal and amount:");

        String[] input;
        while (true) {
            input = readString().split(" ");

            int nominal = 0;
            int total = 0;
            try {
                nominal = Integer.parseInt(input[0]);
                total = Integer.parseInt(input[1]);
            } catch (Exception e) {
                writeMessage("Error, Repeat again:");
                continue;
            }
            if (nominal <= 0 || total <= 0) {
                writeMessage("Error, Repeat again:");
                continue;
            }
            break;
        }
        return input;
    }

    public static Operation askOperation() throws InterruptOperationException {

        writeMessage("Choose operation:\n" +
                "1 - INFO, 2 - DEPOSIT, 3 - WITHDRAW, 4 - EXIT: ");

        String userInput = readString();
        Operation opr = null;

        while (true) {

            try {
                opr = Operation.getAllowableOperationByOrdinal(Integer.parseInt(userInput));
                break;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                writeMessage("Wrong operation. Please, try again: ");
                userInput = readString();
            }
        }
        return opr;
    }
}
