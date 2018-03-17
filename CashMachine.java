package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;


import java.util.Locale;

public class CashMachine {

    public static final String RESOURCE_PATH = "com.javarush.task.task26.task2613.resources.";

    public static void main(String[] args) {


        Locale.setDefault(Locale.ENGLISH);

        try {
            Operation operation;
            //CommandExecutor.execute(Operation.LOGIN);
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            } while (operation != Operation.EXIT);
        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }
    }
}
