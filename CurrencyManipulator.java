package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {

    private String currencyCode;

    private Map<Integer, Integer> denominations = new HashMap<>(); // это Map<номинал, количество>.

    public String getCurrencyCode() {
        return currencyCode;
    }

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void addAmount(int denomination, int count) {

        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        int totalAmount = 0;
        for (Map.Entry<Integer, Integer> pair : denominations.entrySet())
            totalAmount += pair.getKey() * pair.getValue();
        return totalAmount;
    }

    public boolean hasMoney() {
        int money = getTotalAmount();
        if (money == 0) {
            return false;
        }
        else return true;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return (getTotalAmount() - expectedAmount >= 0);
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int sum = expectedAmount;
        HashMap<Integer, Integer> temp = new HashMap<>();
        temp.putAll(denominations);
        ArrayList<Integer> nominals = new ArrayList<>();
        for (Map.Entry<Integer, Integer> pair : temp.entrySet())
            nominals.add(pair.getKey());

        Collections.sort(nominals);
        Collections.reverse(nominals);

        TreeMap<Integer, Integer> result = new TreeMap<>(
                new Comparator<Integer>()
                {
                    @Override
                    public int compare(Integer o1, Integer o2)
                    {
                        return o2.compareTo(o1);
                    }
                });

        for (Integer nominal : nominals) {
            int key = nominal;
            int value = temp.get(key);
            while (true) {
                if (sum < key || value <= 0) {
                    temp.put(key, value);
                    break;
                }
                sum -= key;
                value--;

                if (result.containsKey(key))
                    result.put(key, result.get(key) + 1);
                else
                    result.put(key, 1);
            }
        }

        if (sum > 0)
            throw new NotEnoughMoneyException();
        else
        {
                /*for (Map.Entry<Integer, Integer> pair : result.entrySet())
                    ConsoleHelper.writeMessage("\t" + pair.getKey() + " - " + pair.getValue());*/

            denominations.clear();
            denominations.putAll(temp);
            //ConsoleHelper.writeMessage("Transaction was successful!");
        }
        return result;
    }
}
