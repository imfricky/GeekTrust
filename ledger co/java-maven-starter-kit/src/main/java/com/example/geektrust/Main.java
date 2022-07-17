package com.example.geektrust;

import com.example.geektrust.service.LedgerManager;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

@Log4j2
public class Main {
    public static void main(String[] args) {
        try {
            LedgerManager ledgerManager = new LedgerManager();
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                ledgerManager.processInput(input);
            }
            sc.close();
        } catch (IOException e) {
            log.error("Error while parsing the file, please check the file name and arguments again!");
        }
    }
}
