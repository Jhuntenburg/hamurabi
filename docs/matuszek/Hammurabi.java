package hammurabi.docs.matuszek;//package hammurabi.src.main.java;

import java.io.IOException;
import java.util.Scanner;

// this seems to be an example of a solution that mimics the original BASIC code the author was writing from.
//
// it's a great example of Very Bad Java.
// Do not write Java like this. If you do, do NOT tell people you went to Zip Code.
// I'm serious.
// (how the hell would you ever be able to TEST this piece of code?)
//
public class Hammurabi {
	static int totalDeaths = 0, percentDied = 0, year = 0, population = 95, stores = 2800, immigrants = 5, deaths,
		harvest = 3000, yeild = 3, acres = harvest / yeild, eaten = harvest - stores, landPrice, fullPeople, temp;
	static boolean plague = false;
	final static String FINK = "DUE TO THIS EXTREME MISMANAGEMENT YOU HAVE NOT ONLY\n" +
		"BEEN IMPEACHED AND THROWN OUT OF OFFICE BUT YOU HAVE\n" +
		"ALSO BEEN DECLARED PERSONA NON GRATA!!\n";
	Scanner input = new Scanner(System.in);

	private void newYear() {
		year += 1;
		population += immigrants;
		landPrice = (int) (10 * Math.random() + 17);
		System.out.println(report());
	/*	do {
			System.out.print("HOW MANY ACRES DO YOU WISH TO BUY?  ");
			temp = input.nextInt();
			if (temp < 0)
				epicFail(0);
			if (temp * landPrice > stores)
				System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY\n" +
						stores + " BUSHELS OF GRAIN. NOW THEN,");
		} while (temp * landPrice > stores);
		acres += temp;
		stores -= temp * landPrice;
		do {
			System.out.print("HOW MANY ACRES DO YOU WISH TO SELL?  ");
			temp = input.nextInt();
			if (temp < 0)
				epicFail(0);
			if (temp > acres)
				System.out.println("HAMURABI:  THINK AGAIN. YOU OWN ONLY " + acres + " ACRES. NOW THEN,");
		} while (temp > acres);
		stores += temp * landPrice;
		acres -= temp;
		do {
			System.out.print("\nHOW MANY BUSHELS DO YOU WISH TO FEED YOUR PEOPLE?  ");
			temp = input.nextInt();
			if (temp < 0)
				epicFail(0);
			if (temp > stores)
				System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY\n" +
						stores + " BUSHELS OF GRAIN. NOW THEN,");
		} while (temp > stores);
		fullPeople = temp / 20;
		stores -= temp;
		do {
			System.out.print("\nHOW MANY ACRES DO YOU WISH TO PLANT WITH SEED?  ");
			temp = input.nextInt();
			if (temp < 0)
				epicFail(0);
			if (temp > acres)
				System.out.println("HAMURABI:  THINK AGAIN. YOU OWN ONLY " + acres + " ACRES. NOW THEN,");
			if (temp / 2 > stores)
				System.out.println("HAMURABI:  THINK AGAIN. YOU HAVE ONLY\n" +
						stores + " BUSHELS OF GRAIN. NOW THEN,");
			if (temp > population * 10)
				System.out.println("BUT YOU HAVE ONLY" + population + "PEOPLE TO TEND THE FIELDS. NOW THEN,");
		} while (temp > acres || temp / 2 > stores || temp > population * 10);
		stores -= temp / 2;*/
		yeild = (int) (Math.random() * 5 + 1);
		harvest = temp * yeild;
		temp = (int) (Math.random() * 5 + 1);
		if (temp % 2 != 1)
			eaten = (stores / temp);
		else 
			eaten = 0;
		stores += (harvest - eaten);
		immigrants = (int) (Math.random() * 5 + 1) * 
					(20 * acres + stores) / population / 100 + 1;
		if (population > fullPeople) {
			deaths = population - fullPeople;
			if (deaths > .45 * population)
				epicFail(1);
			percentDied = ((year - 1) * percentDied + deaths * 100 / population) / year;
			population = fullPeople;
			totalDeaths += deaths;
		}
		if (20 * Math.random() >= 17)
			plague = true;
		plague = false;
	}

	private static String report() {
		String answer = "\nHAMURABI:  I BEG TO REPORT TO YOU,\n" +
				"IN YEAR " + year + ", " + deaths + " PEOPLE STARVED, " + immigrants + " CAME TO THE CITY.\n";
		if (plague) {
			population = population / 2;
			answer += "A HORRIBLE PLAGUE STRUCK!  HALF THE PEOPLE DIED.\n";
		}
		answer += "POPULATION IS NOW " + population + ".\n" +
				"THE CITY NOW OWNS " + acres + " ACRES.\n" +
				"YOU HARVESTED " + yeild + " BUSHELS PER ACRE.\n" +
				"RATS ATE " + eaten + " BUSHELS.\n" +
				"YOU NOW HAVE " + stores + " BUSHELS IN STORE\n\n" +
				"LAND IS TRADING AT " + landPrice + " BUSHELS PER ACRE.";
		return answer;
	}

	
	private static void epicFail(int x) {
		String reason = "";
		switch (x) {
		case 0: reason = "HAMURABI:  I CANNOT DO WHAT YOU WISH.\n" +
			"GET YOURSELF ANOTHER STEWARD!!!!!"; break;
		case 1: reason = "YOU STARVED " + deaths + " PEOPLE IN ONE YEAR!!!\n" +
			FINK; break;
		}
		System.out.println(reason);
		System.exit(0);
	}
	
	private void finished() {
		String answer = "IN YOUR 10-YEAR TERM OF OFFICE, " + percentDied + " PERCENT OF THE\n" +
			"POPULATION STARVED PER YEAR ON AVERAGE, I.E., A TOTAL OF\n" +
			totalDeaths + " PEOPLE DIED!!\n" +
			"YOU STARTED WITH 10 ACRES PER PERSON AND ENDED WITH\n" +
			acres / population + " ACRES PER PERSON\n\n";
		if (percentDied > 33 || acres / population < 7)
			answer += FINK;
		else if (percentDied > 10 || acres / population < 9)
			answer += "YOUR HEAVY-HANDED PERFORMANCE SMACKS OF NERO AND IVAN IV.\n" +
					"THE PEOPLE (REMAINING) FIND YOU AN UNPLEASANT RULER, AND,\n" +
					"FRANKLY, HATE YOUR GUTS!";
		else if (percentDied > 3 || acres / population < 10)
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
	}
	
	public static void main(String[] args) throws IOException {
		Hammurabi a = new Hammurabi();
		System.out.println("\t\t\t\tHAMURABI\n\t       CREATIVE COMPUTING MORRISTOWN, NEW JERSEY\n\n" +
				"TRY YOUR HAND AT GOVERNING ANCIENT SUMERIA\nSUCCESSFULLY FOR A TEN-YEAR TERM OF OFFICE.");
		while (year < 10)
			a.newYear();
		a.finished();
	}

	public int immigrants(int i, int i1, int i2) {
		return 0;
	}

	public int plagueDeaths(int i) {
		return 0;
	}

	public int starvationDeaths(int i, int i1) {
		return 0;
	}

	public boolean uprising(int i, int i1) {
		return false;
	}

	public int harvest(int i) {
		return 0;
	}

	public int grainEatenByRats(int i) {
		return 0;
	}

	public int newCostOfLand() {
		return 0;
	}
}