package edu.wctc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private Scanner keyboard;
    private Cookbook cookbook;


    public Main() {
        keyboard = new Scanner(System.in);
        cookbook = new Cookbook();

        try {
            System.out.println("Reading in meals information from file...");
            List<String> fileLines = Files.readAllLines(Paths.get("meals_data.csv"));

            for (String line : fileLines) {
                String[] fields = line.split(",");
                cookbook.addMeal(fields[0], fields[1], fields[2]);
            }

            runMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    private void listByMealType() {
        // Default value pre-selected in case
        // something goes wrong w/user choice
        MealType mealType = MealType.DINNER;

        System.out.print("Which meal type? ");

        // Generate the menu using the ordinal value of the enum
        for (MealType m : MealType.values()) {
            System.out.println((m.ordinal() + 1) + ". " + m.getPrettyPrint());
        }

        System.out.print("Please enter your choice: ");
        String ans = keyboard.nextLine();

        try {
            int ansNum = Integer.parseInt(ans);
            if (ansNum < MealType.values().length) {
                mealType = MealType.values()[ansNum - 1];
            }
        } catch (NumberFormatException nfe) {
            System.out.println(String.format("Invalid meal type %s. Defaulted to %s.",
                    ans, mealType.getPrettyPrint()));
        }

        cookbook.printMealsByType(mealType);
    }

    private void printMenu() {
        System.out.println("");
        System.out.println("Select Action");
        System.out.println("1. List All Items");
        System.out.println("2. List All Items by Meal");
        System.out.println("3. Search by Meal Name");
        System.out.println("4. Do Control Break");
        System.out.println("5. Exit");
        System.out.print("Please enter your choice: ");
    }

    private void runMenu() {
        boolean userContinue = true;

        while (userContinue) {
            printMenu();

            String ans = keyboard.nextLine();
            switch (ans) {
                case "1":
                    cookbook.printAllMeals();
                    break;
                case "2":
                    listByMealType();
                    break;
                case "3":
                    searchByName();
                    break;
                case "4":
                    doControlBreak();
                    break;
                case "5":
                    userContinue = false;
                    break;
            }
        }

        System.out.println("Goodbye");
        System.exit(0);
    }

    private void doControlBreak(){
        //need total, mean, min, max, median;
        ArrayList<Meal> allMeals = new ArrayList<>(cookbook.getMeals());

        //separate by mealtype and sort them low to high
        //probably don't use this since it can't be resorted
        Stream<Meal> lunchStreamSorted = allMeals.stream().filter(meal -> meal.getMealType()==MealType.LUNCH)
                .sorted(Comparator.comparingInt(Meal::getCalories));


        /**Min values**/
        int minBreakfast = allMeals.stream()
                .filter(meal -> meal.getMealType()==MealType.BREAKFAST)
                .sorted(Comparator.comparingInt(Meal::getCalories))
                .findFirst().get().getCalories();

        int minLunch = allMeals.stream()
                .filter(meal -> meal.getMealType()==MealType.LUNCH)
                .sorted(Comparator.comparingInt(Meal::getCalories))
                .findFirst().get().getCalories();

        int minDinner = allMeals.stream()
                .filter(meal -> meal.getMealType()==MealType.DINNER)
                .sorted(Comparator.comparingInt(Meal::getCalories))
                .findFirst().get().getCalories();

        int minDessert = allMeals.stream()
                .filter(meal -> meal.getMealType()==MealType.DESSERT)
                .sorted(Comparator.comparingInt(Meal::getCalories))
                .findFirst().get().getCalories();


        /**Max Values**/
        Integer maxBreakfast = allMeals.stream()
                .filter(meal -> meal.getMealType()==MealType.BREAKFAST)
                .sorted(Comparator.comparingInt(Meal::getCalories))
                .map(Meal::getCalories).max(Comparator.comparing(Integer::valueOf)).get();

        Integer maxLunch = allMeals.stream()
                .filter(meal -> meal.getMealType()==MealType.LUNCH)
                .sorted(Comparator.comparingInt(Meal::getCalories))
                .map(Meal::getCalories).max(Comparator.comparing(Integer::valueOf)).get();

        Integer maxDinner = allMeals.stream()
                .filter(meal -> meal.getMealType()==MealType.DINNER)
                .sorted(Comparator.comparingInt(Meal::getCalories))
                .map(Meal::getCalories).max(Comparator.comparing(Integer::valueOf)).get();

        Integer maxDessert = allMeals.stream()
                .filter(meal -> meal.getMealType()==MealType.DESSERT)
                .sorted(Comparator.comparingInt(Meal::getCalories))
                .map(Meal::getCalories).max(Comparator.comparing(Integer::valueOf)).get();


        /****Start printing****/
        System.out.println("******BREAKFAST******");
        System.out.println("MIN: " + minBreakfast);
        System.out.println("MAX: " + maxBreakfast);

        System.out.println("******LUNCH******");
        System.out.println("MIN: " + minLunch);
        System.out.println("MAX: " + maxLunch);

        System.out.println("******DINNER******");
        System.out.println("MIN: " + minDinner);
        System.out.println("MAX: " + maxDinner);

        System.out.println("******DESSERT******");
        System.out.println("MIN: " + minDessert);
        System.out.println("MAX: " + maxDessert);





        //Optional<Meal> lowestCalorieLunch = allMeals.stream().findFirst();
        //System.out.println("The lowest calorie lunch is: " + lowestCalorieLunch.get().getCalories());

        //Collections.sort(allMeals, Comparator.comparingInt(Meal::getCalories));
        //Integer lowCalories = allMeals.get(0).getCalories();
        //Integer highCalories = allMeals.get(allMeals.size()-1).getCalories();

        //System.out.println(lowCalories);
        //System.out.println(highCalories);


        /**allMeals.forEach(meal -> {
            int totalCalories = 0;
            if (meal.getMealType().equals(currentMealType)){
                totalCalories = totalCalories + meal.getCalories();

                if (meal.getCalories() > Collections.max()){

                }



            }
        });**/



        /**
        for (Meal m : allMeals) {
            if (m.getMealType().equals(currentMealType)){ //if true, work on our values. otherwise move to next meal type
                totalCalories = totalCalories + m.getCalories();
                if (m.getCalories() > maxCalories) {
                    maxCalories = m.getCalories();
                }
                if (m.getCalories() < minCalories) {
                    minCalories = m.getCalories();
                }
                mealTypeCount++; //use for mean
                currentMealTypeSet.add(m); //subset we can use for median
                index++;
            }

            else if (m.getMealType()!=currentMealType) {
                System.out.println(currentMealType);
                int meanCalories = totalCalories/mealTypeCount;
                int medianCalories = currentMealTypeSet.get(mealTypeCount/2).getCalories();

                System.out.println("Total / Mean / Min / Max / Median");
                System.out.println(totalCalories + " / " + meanCalories + " / " + minCalories + " / " + maxCalories + " / " + medianCalories);

                //resets everything for next "if" iteration
                currentMealTypeSet.clear();
                minCalories = m.getCalories();
                maxCalories = m.getCalories();
                totalCalories = m.getCalories();
                mealTypeCount = 0;

                //switch to new mealType
                currentMealType = m.getMealType();
                index++;
            }

            if (index == length){
                System.out.println(currentMealType);
                int meanCalories = totalCalories/mealTypeCount;
                int medianCalories = currentMealTypeSet.get(mealTypeCount/2).getCalories();

                System.out.println("Total / Mean / Min / Max / Median");
                System.out.println(totalCalories + " / " + meanCalories + " / " + minCalories + " / " + maxCalories + " / " + medianCalories);
            }
        }**/
    }

    private void searchByName() {
        keyboard.nextLine();
        System.out.print("Please enter name to search: ");
        String ans = keyboard.nextLine();
        cookbook.printByNameSearch(ans);
    }
}
