package lab1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;



public class Main {

    //a function that accept and return a value that is already defined with the correct JavaDoc of the function

    /**
     * @param name the user's name
     * @return a greeting message
     */
    public static String greetuser(String name) {
        return "Hello " + name + ", nice to meet you";
    }


    public static void main(String[] args) {
        //Variables
        String name;
        int age = -1;
        String Singer;
        var favoritesinger = new ArrayList<String>();


        // To read the input of the user
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello, what is your name? :)");
        name = sc.nextLine();

        //A loop that asks the user's age to check if he/she can drink alcohol
        //The loop will not break if the user don't put an int
        while (true) {
            try {
                System.out.println("How old are you?");
                age = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number for age.");
                sc.nextLine();

            }
        }
        //conditional statement
        //The condition checks if the user is over 19 years old to drink alcohol.
        if (age < 19) {
            System.out.println("You can't drink alcohol");
        } else {
            System.out.println("You can drink alcohol");
        }

        //Recolect the favorite singers of the user to give a list with that information
        System.out.println("Give me your 3 favorite singers");
        for (int i = 0; i < 3; i++) {
            System.out.println("Singer " + (i + 1) + ":");
            Singer = sc.nextLine();
            favoritesinger.add(Singer);
        }

        System.out.println("Your favorites singers are:");
        for (String singer : favoritesinger) {
            System.out.println(singer);
        }

        //Put the all the information of the user in a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userinfo.txt"))) {
            //Recollect the information that the user already give to us
            //The information is output in order way and each one is in their own line
            writer.write("User Information:");
            writer.newLine();
            writer.write("Name: " + name);
            writer.newLine();
            writer.write("Age: " + age);
            writer.newLine();

            //Here is the same loop of the beginning but here we are using the same loop to save in the doc
            if (age < 19) {
                writer.write("Can drink alcohol: No");
            } else {
                writer.write("Can drink alcohol: Yes");
            }
            writer.newLine();

            //Are taking the information of the singers to save in the document and output in order way
            writer.write("Favorite Singers:");
            writer.newLine();
            for (String singer : favoritesinger) {
                writer.write(singer);
                writer.newLine();
            }
        //The information is saved in a doc named "userinfo.txt"
            System.out.println("All user information saved to 'userinfo.txt'");
        //If something get wrong, the terminal is going to print a message with the error
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }
}

