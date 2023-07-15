# hamurabi
an ancient (in more ways than one) computer game (text)

## General idea of the lab

*hammurabi.docs.matuszek.Hammurabi* is a very old computer game (like about as old as Kris). Your job is to bring it into the 21st century by writing it in Java.

Here are the standard instructions for the game:

> **Congratulations, you are the newest ruler of ancient Sumer, elected for a ten year term of office. Your duties are to dispense food, direct farming, and buy and sell land as needed to support your people. Watch out for rat infestiations and the plague! Grain is the general currency, measured in bushels. The following will help you in your decisions:**
> 
> *   **Each person needs at least 20 bushels of grain per year to survive**
> *   **Each person can farm at most 10 acres of land**
> *   **It takes 2 bushels of grain to farm an acre of land**
> *   **The market price for land fluctuates yearly**
> 
> **Rule wisely and you will be showered with appreciation at the end of your term. Rule poorly and you will be kicked out of office!**

### Maven

There is NO `pom.xml` file in this repo. You need to add that. Use `vscode` or `IntelliJ` to make sure your project is a Maven project with a POM file.

### Details

Define a single class named `hammurabi.docs.matuszek.Hammurabi`. Use the following skeleton (but leave out the `//` comments):

```java
package hammurabi;               // package declaration 
import java.util.Random;         // imports go here
import java.util.Scanner;

public class hammurabi.docs.matuszek.Hammurabi {         // must save in a file named hammurabi.docs.matuszek.Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    public static void main(String\[\] args) { // required in every Java program
        new hammurabi.docs.matuszek.Hammurabi().playGame();
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        // statements go after the declations
    }

    //other methods go here
}
```

### Playing the game

Here is what you start the game with:

*   100 people
*   2800 bushels of grain in storage
*   1000 acres of land
*   Land value is 19 bushels/acre
    

Each year, print out a summary similar to the following:

    O great hammurabi.docs.matuszek.Hammurabi!
    You are in year 1 of your ten year rule.
    In the previous year 0 people starved to death.
    In the previous year 5 people entered the kingdom.
    The population is now 100.
    We harvested 3000 bushels at 3 bushels per acre.
    Rats destroyed 200 bushels, leaving 2800 bushels in storage.
    The city owns 1000 acres of land.
    Land is currently worth 19 bushels per acre.

The above summary represents the initial state, at the _beginning_ of the first year--that is, when you first take office, and before you do any of the computations below). So, for example, the previous year (under a different ruler) must have started with 95 people; none starved, and 5 entered the kingdom, so as you enter office you rule 100 people.

Here's what your `playGame` method needs to do each year, for up to 10 years:

> Ask the player the following questions. These questions should be asked **in this order.** Do **not** give the player a chance to "back up" and change a previous answer. Each question should be asked in a separate method, and the answer returned as the value of the method, as specified below:
> 
> `int askHowManyAcresToBuy(int price, int bushels)`
> 
> Asks the player how many acres of land to buy, and returns that number. You must have enough grain to pay for your purchase.
> 
> `  
> int askHowManyAcresToSell(int acresOwned)`
> 
> Asks the player how many acres of land to sell, and returns that number. You can't sell more than you have.  
> Do not ask this question if the player is buying land; it doesn't make sense to do both in one turn.
> 
> `  
> int askHowMuchGrainToFeedPeople(int bushels)`
> 
> Ask the player how much grain to feed people, and returns that number. You can't feed them more grain than you have. You **can** feed them more than they need to survive.
> 
> `  
> int askHowManyAcresToPlant(int acresOwned, int population, int bushels)`
> 
> Ask the player how many acres to plant with grain, and returns that number. You must have enough acres, enough grain, and enough people to do the planting. Any grain left over goes into storage for next year.
> 
> For each question, do "sanity checking"; that is, test whether the answer is possible (you have enough grain, you have enough people to do the planting etc.), and keep asking until you get a possible value. (For example, `O Great hammurabi.docs.matuszek.Hammurabi, surely you jest! We have only 3415 bushels left!`)
> 
> Then the method needs to determine:
> 
> 1.  If there is a plague, and how many people die from it.
> 2.  How many people starved.
> 3.  How many people came to the city.
> 4.  How good the harvest is.
> 5.  If you have a problem with rats, and how much grain they eat.
> 6.  How much land costs (for deciding what to do next).
> 
> These can all be local variables of your `playGame` method.
> 
> Use the following methods, **in this order,** to make the necessary calculations:
> 
> `int plagueDeaths(int population)`
> 
> Each year, there is a 15% chance of a horrible plague. When this happens, half your people die. Return the number of plague deaths (possibly zero).
> 
> `  
> int starvationDeaths(int population, int bushelsFedToPeople)`
> 
> Each person needs 20 bushels of grain to survive. If you feed them more than this, they are happy, but the grain is still gone. You don't get any benefit from having happy subjects. Return the number of deaths from starvation (possibly zero).
> 
> `  
> boolean uprising(int population, int howManyPeopleStarved)`
> 
> Return `true` if more than 45% of the people starve. (This will cause you to be immediately thrown out of office, ending the game.)
> 
> `  
> int immigrants(int population, int acresOwned, int grainInStorage)`
> 
> Nobody will come to the city if people are starving (so don't call this method). If everyone is well fed, compute how many people come to the city as: `(20 * _number of acres you have_ + _amount of grain you have in storage_) / (100 * _population_) + 1`.
> 
> `  
> int harvest(int acres, int bushelsUsedAsSeed)`
> 
> Choose a random integer between 1 and 6, inclusive. Each acre that was planted with seed will yield this many bushels of grain. (Example: if you planted 50 acres, and your number is 3, you harvest 150 bushels of grain). Return the number of bushels harvested.
> 
> `  
> int grainEatenByRats(int bushels)`
> 
> There is a 40% chance that you will have a rat infestation. When this happens, rats will eat somewhere between 10% and 30% of your grain. Return the amount of grain eaten by rats (possibly zero).
> 
> `  
> int newCostOfLand()`
> 
> The price of land is random, and ranges from 17 to 23 bushels per acre. Return the new price for the next set of decisions the player has to make. (The player will need this information in order to buy or sell land.)

Do these computations, _in this order,_ for each of ten years. Each computation should be done in a separate method, and none of these methods should read anything or print anything. Since most methods change the amount of grain available, you will need to keep this information in a variable so it is available to the next method that needs it.

When the computations are finished, call a method `printSummary` to print the summary for the year. This method will take several parameters.

When the game ends, use a method `finalSummary` to print out a final summary, and to tell the player how good a job he/she did. I'll leave the details up to you, but the usual evaluation is based on how many people starved, and how many acres per person you end up with.

Your `playGame` method will be quite long, but very straightforward; it does very little but call other methods.

All the required arithmetic in this program should be **integer**. You do not need doubles.

### Urgent!

> It is **strongly** suggest that you first get the *simplest possible version of the program running* and tested out (that is, leave out things like rats, plagues, and immigration), then add these things one at a time to a _working_ program, **testing as you go**.

### Things you need to know:

#### Random numbers

To get a random number generator, you first need to import `java.util.Random` (or `java.util.*`). Then, you can create a random number generator with a statement such as:

> `static Random rand = new Random();`

To get a new random number in the range `0..n-1`, where `n` is an `int`, call `rand.nextInt(n)`. To get a new random number in the range `min..max`, use the expression `rand.nextInt(max - min + 1) + min`. You can use the `rand` method in statements such as

> `myNewNumber = rand.nextInt(5);  
> if (rand.nextInt(100) < 15) { ... }`

To do something that happens `p` percent of the time, use

> `if (rand.nextInt(100) < p) { _...do something..._ }`

#### Getting input

To get a number from the player, add this method to your program:

```java
    /**
     * Prints the given message (which should ask the user for some integral
     * quantity), and returns the number entered by the user. If the user's
     * response isn't an integer, the question is repeated until the user
     * does give a integer response.
     * 
     * @param message The request to present to the user.
     * @return The user's numeric response.
     */
     int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }
```

Here is an example of how you can use this method:

> `sell = getNumber("O great hammurabi.docs.matuszek.Hammurabi, how many acres shall you sell?");`

### Structure of the solution

*   Project name: `hammurabi.docs.matuszek.Hammurabi`
*   Package name: `hammurabi`
*   Class names and method signatures:
    *   `class hammurabi.docs.matuszek.Hammurabi`
        *   `public static void main(String[] args)`
        *   Numerous other methods, as described above.

The above are requirements. **The methods that do calculations**, not input/output, **will be tested by the test methods in the test class**, so be sure to get the spelling, capitalization, and number and types of arguments correct (argument _names_ are irrelevant). You may have additional methods if you wish.

### Yo! Test it!

You'll see there is a Test File (HammurabiTest.java) you need to be able to run against your code to prove your solution works. Use It!!

But realize that you'll have to structure your solution so that app classes are in one source direstory and your test files is in another. Study your other labs to figure out what to do.

### Credits

Adapted from: CIT 591 Assignment 3: hammurabi.docs.matuszek.Hammurabi, Fall 2010, David Matuszek (this guy is a Rock Star)

