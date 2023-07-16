import java.util.Random;
import java.util.Scanner;

public class Hammurabi {


    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        int population = 95, storesOfGrain = 2800, immigrants = 0, acres = 1000, landPrice = 19, numberOfDeathsFromStarvation = 0,
                grainHarvested = 0, hungryRats = 0, acresToPlant =0, numberOfDeathsFromPlague =0;
        boolean plague = false;
        final  String FINK = "DUE TO THIS EXTREME MISMANAGEMENT YOU HAVE NOT ONLY\n" +
                "BEEN IMPEACHED AND THROWN OUT OF OFFICE BUT YOU HAVE\n" +
                "ALSO BEEN DECLARED PERSONA NON GRATA!!\n";
        for (int i = 0; i < 10; i++) {


            System.out.println(report(i, numberOfDeathsFromStarvation, immigrants, population, acres, grainHarvested,acresToPlant, hungryRats, storesOfGrain, landPrice, plague));
//Will update values as we play through the game through user inputs
            int acresToBuy = askHowManyAcresToBuy(landPrice, storesOfGrain);
            int acresToSell = 0;
            if (acresToBuy == 0) {
                acresToSell = askHowManyAcresToSell(acres);
            }
            acres += acresToBuy - acresToSell;
            storesOfGrain += (acresToSell * landPrice) - (acresToBuy * landPrice);

            int grainToFeed = askHowMuchGrainToFeedPeople(storesOfGrain);
            storesOfGrain -= grainToFeed;

            acresToPlant = askHowManyAcresToPlant(acres, population, storesOfGrain);
            storesOfGrain -= (acresToPlant * 2);
            //this section will calculate the changes made from the last sections user choices

            numberOfDeathsFromPlague = plagueDeaths(population);

            population -= numberOfDeathsFromPlague;
            if( numberOfDeathsFromPlague > 0) plague = true;


            numberOfDeathsFromStarvation = starvationDeaths(population, grainToFeed);

            population -= numberOfDeathsFromStarvation;
            if(numberOfDeathsFromStarvation > .45 * population){
                epicFail(numberOfDeathsFromStarvation, FINK);
            }


            boolean isUprising = uprising(population, numberOfDeathsFromStarvation);
            if (isUprising) {
                break;
            }
            if (numberOfDeathsFromStarvation == 0) {
                immigrants(population, acres, storesOfGrain);
            }

            grainHarvested = harvest(acres);
            storesOfGrain += grainHarvested;

            hungryRats = grainEatenByRats(storesOfGrain);
            storesOfGrain -= hungryRats;

            newCostOfLand();

        }
        System.out.println(finished(numberOfDeathsFromStarvation, numberOfDeathsFromPlague,population,acres,FINK ));

    }

    int askHowManyAcresToBuy(int price, int bushels) {
        int temp;
        do {
            System.out.print("HOW MANY ACRES DO YOU WISH TO BUY?  ");//print out our prompting question
            temp = scanner.nextInt(); //takes input from user
            if (temp * price > bushels)
                System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY " +
                        bushels + " BUSHELS OF GRAIN. NOW THEN,");
        } while (temp * price > bushels);


        return temp;
    }

    int askHowManyAcresToSell(int acres) {
        int temp;
        do {
            System.out.print("HOW MANY ACRES DO YOU WISH TO SELL?  ");
            temp = scanner.nextInt();

            if (temp > acres)
                System.out.println("HAMURABI:  THINK AGAIN. YOU OWN ONLY " + acres + " ACRES. NOW THEN,");
        } while (temp > acres);

        return temp;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        int temp;
        do {
            System.out.print("\nHOW MANY BUSHELS DO YOU WISH TO FEED YOUR PEOPLE?  ");
            temp = scanner.nextInt();

            if (temp > bushels)
                System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY " +
                        bushels + " BUSHELS OF GRAIN. NOW THEN,");
        } while (temp > bushels);

        return temp;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        int temp;
        do {
            System.out.print("\nHOW MANY ACRES DO YOU WISH TO PLANT WITH SEED?  ");
            temp = scanner.nextInt();

            if (temp > acresOwned)
                System.out.println("HAMURABI:  THINK AGAIN. YOU OWN ONLY " + acresOwned + " ACRES. NOW THEN,");
            if (temp * 2 > bushels)
                System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY " +
                        bushels + " BUSHELS OF GRAIN. NOW THEN,");
            if (temp > population * 10)
                System.out.println("BUT YOU HAVE ONLY " + population + " PEOPLE TO TEND THE FIELDS. NOW THEN,");
        } while (temp > acresOwned || temp * 2 > bushels || temp > population * 10);

        return temp;
    }

    String report(int year, int deaths, int immigrants, int population, int acres, int grainHarvested, int acresToPlant, int eaten,
                  int storesOfGrain, int landPrice, boolean plague) {
        String answer = "\nHAMURABI:  I BEG TO REPORT TO YOU,\n" +
                "IN YEAR " + year + ", " + deaths + " PEOPLE STARVED, " + immigrants + " CAME TO THE CITY.\n";
        if (plague) {

            answer += "A HORRIBLE PLAGUE STRUCK!  HALF THE PEOPLE DIED.\n";
        }
        if (acresToPlant > 0){
            answer += "YOU HARVESTED " + grainHarvested/acresToPlant + " BUSHELS PER ACRE.\n";
        }
        answer += "POPULATION IS NOW " + population + ".\n" +
                "THE CITY NOW OWNS " + acres + " ACRES.\n" +

                "RATS ATE " + eaten + " BUSHELS.\n" +
                "YOU NOW HAVE " + storesOfGrain + " BUSHELS IN STORE\n\n" +
                "LAND IS TRADING AT " + landPrice + " BUSHELS PER ACRE.";
        return answer;
    }

    int plagueDeaths(int population) {
        int deathFromPlague = 0;
        if (rand.nextInt(100) < 15) {
            deathFromPlague = population / 2;

        }
        return deathFromPlague;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
//        Each person needs 20 bushels of grain to survive. If you feed them more than this,
//        they are happy, but the grain is still gone. You don't get any benefit from having happy subjects.
//        Return the number of deaths from starvation (possibly zero).
        int numberOfStarvationDeaths = 0;
        int peopleFed = bushelsFedToPeople / 20;

        if (peopleFed < population) {
            numberOfStarvationDeaths = population - peopleFed;
        }

        return numberOfStarvationDeaths;


    }

    boolean uprising(int population, int howManyPeopleStarved) {

//    Return true if more than 45% of the people starve.
//    (This will cause you to be immediately thrown out of office, ending the game.)
//        boolean instanceOfUprising = false;
       /* if ((population * .45) > howManyPeopleStarved) {
            instanceOfUprising = true;
        }
        return instanceOfUprising;*/
        boolean instanceOfUprising = false;

        if ((howManyPeopleStarved * 100.0 / population) > 45.0) {
            instanceOfUprising = true;
        }

        return instanceOfUprising;
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
//    Nobody will come to the city if people are starving (so don't call this method).
//    If everyone is well fed, compute how many people come to the city as:
//    (20 * _number of acres you have_ + _amount of grain you have in storage_) / (100 * _population_) + 1
        int numberOfImmigrants = (20 * acresOwned + grainInStorage) / (100 * population) + 1;
        return numberOfImmigrants;
    }

    int harvest(int acres) {
//    Choose a random integer between 1 and 6, inclusive. Each acre that was planted with seed
//    will yield this many bushels of grain. (Example: if you planted 50 acres, and your number
//    is 3, you harvest 150 bushels of grain). Return the number of bushels harvested.
        int numberOfBushelsHarvested = rand.nextInt(6) + 1 * acres;
        return numberOfBushelsHarvested;
    }

    int grainEatenByRats(int bushels) {
//    There is a 40% chance that you will have a rat infestation. When this happens,
//    rats will eat somewhere between 10% and 30% of your grain. Return the amount of grain eaten by rats (possibly zero).
        int bushelsEatenByRats = 0;
        if (rand.nextInt(100) < 40) bushelsEatenByRats = (int) ((bushels * (rand.nextDouble() / 5 + .1)));
        return bushelsEatenByRats;
    }

    int newCostOfLand() {
        //    The price of land is random, and ranges from 17 to 23 bushels per acre.
//    Return the new price for the next set of decisions the player has to make.
//    (The player will need this information in order to buy or sell land.)
        int costOfLand = rand.nextInt(7) + 17;
        return costOfLand;
    }

    String finished(int numberOfDeathsFromStarvation, int numberOfDeathsFromPlague, int population, int acres, String FINK) {
        String answer = "IN YOUR 10-YEAR TERM OF OFFICE, " + ((numberOfDeathsFromStarvation + numberOfDeathsFromPlague)/population) *100  + " PERCENT OF THE\n" +
                "POPULATION STARVED PER YEAR ON AVERAGE, I.E., A TOTAL OF\n" +
                (numberOfDeathsFromStarvation + numberOfDeathsFromPlague) + " PEOPLE DIED!!\n" +
                "YOU STARTED WITH 10 ACRES PER PERSON AND ENDED WITH\n" +
                acres / population + " ACRES PER PERSON\n\n";
        if (((numberOfDeathsFromStarvation + numberOfDeathsFromPlague)/population) *100 > 33 || acres / population < 7)
            answer += FINK;
        else if (((numberOfDeathsFromStarvation + numberOfDeathsFromPlague)/population) *100 > 10 || acres / population < 9)
            answer += "YOUR HEAVY-HANDED PERFORMANCE SMACKS OF NERO AND IVAN IV.\n" +
                    "THE PEOPLE (REMAINING) FIND YOU AN UNPLEASANT RULER, AND,\n" +
                    "FRANKLY, HATE YOUR GUTS!";
        else if (((numberOfDeathsFromStarvation + numberOfDeathsFromPlague)/population) *100 > 3 || acres / population < 10)
            answer += "YOUR PERFORMANCE COULD HAVE BEEN SOMEWHAT BETTER, BUT\n" +
                    "REALLY WASN'T TOO BAD AT ALL.\n" +
                    Math.random() * population * .8 + " PEOPLE WOULD" +
                    "DEARLY LIKE TO SEE YOU ASSASSINATED BUT WE ALL HAVE OUR" +
                    "TRIVIAL PROBLEMS";
        else
            answer += "A FANTASTIC PERFORMANCE!!!  CHARLEMANGE, DISRAELI, AND\n" +
                    "JEFFERSON COMBINED COULD NOT HAVE DONE BETTER!";
        answer += "\n\n\n\n\n\n\n\n\n\nSo long for now.";
        System.out.println(answer);
        return answer;
    }

    String epicFail(int numberOfDeathsFromStarvation,String FINK){
        String answer = "YOU STARVED " + numberOfDeathsFromStarvation + " PEOPLE IN ONE YEAR!!!\n" +
                FINK;
        System.out.println(answer);

        System.exit(0);
        return answer;
    }

}
