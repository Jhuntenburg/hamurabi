import java.util.Random;
import java.util.Scanner;

public class Hammurabi {


    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    int totalDeaths = 0, percentDied = 0, year = 0, population = 95, storesOfGrain = 2800, immigrants = 5, deaths,
            harvest = 3000, yeild = 3, acres = 1000, eaten = harvest - storesOfGrain, landPrice = 19, fullPeople;

    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {

        for (int i = 0; i < 10; i++) {

            year += 1;
            population += immigrants;
            landPrice = (int) (10 * Math.random() + 17);
            System.out.println(report());
//Will update values as we play through the game
            int acresToBuy = askHowManyAcresToBuy(landPrice, storesOfGrain);
            if (acresToBuy == 0) {
                int acresToSell = askHowManyAcresToSell(acres);
            }

            int grainToFeed = askHowMuchGrainToFeedPeople(storesOfGrain);

            int acresToPlant = askHowManyAcresToPlant(acres, population, storesOfGrain);


        }
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        int temp;
        do {
            System.out.print("HOW MANY ACRES DO YOU WISH TO BUY?  ");//print out our prompting question
            temp = scanner.nextInt(); //takes input from user
            if (temp * price > bushels)
                System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY\n" +
                        bushels + " BUSHELS OF GRAIN. NOW THEN,");
        } while (temp * price > bushels);
        acres += temp;
        storesOfGrain -= temp * price;


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
        storesOfGrain += temp * landPrice;
        this.acres -= temp;
        return temp;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        int temp;
        do {
            System.out.print("\nHOW MANY BUSHELS DO YOU WISH TO FEED YOUR PEOPLE?  ");
            temp = scanner.nextInt();

            if (temp > bushels)
                System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY\n" +
                        bushels + " BUSHELS OF GRAIN. NOW THEN,");
        } while (temp > bushels);

        storesOfGrain -= temp;
        return temp;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        int temp;
        do {
            System.out.print("\nHOW MANY ACRES DO YOU WISH TO PLANT WITH SEED?  ");
            temp = scanner.nextInt();

            if (temp > acresOwned)
                System.out.println("HAMURABI:  THINK AGAIN. YOU OWN ONLY " + acresOwned + " ACRES. NOW THEN,");
            if (temp / 2 > bushels)
                System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY\n" +
                        bushels + " BUSHELS OF GRAIN. NOW THEN,");
            if (temp > population * 10)
                System.out.println("BUT YOU HAVE ONLY" + population + "PEOPLE TO TEND THE FIELDS. NOW THEN,");
        } while (temp > acresOwned || temp / 2 > bushels || temp > population * 10);
        storesOfGrain -= temp / 2;
        return temp;
    }

    String report() {
        String answer = "\nHAMURABI:  I BEG TO REPORT TO YOU,\n" +
                "IN YEAR " + year + ", " + deaths + " PEOPLE STARVED, " + immigrants + " CAME TO THE CITY.\n";
//        if (plague) {
//            population = population / 2;
        answer += "A HORRIBLE PLAGUE STRUCK!  HALF THE PEOPLE DIED.\n";
//        }
        answer += "POPULATION IS NOW " + population + ".\n" +
                "THE CITY NOW OWNS " + acres + " ACRES.\n" +
                "YOU HARVESTED " + yeild + " BUSHELS PER ACRE.\n" +
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
    int newCostOfLand(){
        //    The price of land is random, and ranges from 17 to 23 bushels per acre.
//    Return the new price for the next set of decisions the player has to make.
//    (The player will need this information in order to buy or sell land.)
        int costOfLand = rand.nextInt(7)+17;
        return costOfLand;
    }

}
