package com.example.geektrust;

import com.example.geektrust.services.CommandManagerService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            CommandManagerService commandManagerService = new CommandManagerService();
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                commandManagerService.parseInput(s);
            }
            sc.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
