import java.awt.image.Kernel;
import java.util.*;

// Find a list of private Jet rental Airlines to n from mtl
//for each airline, records cheapest rentals from mtl to anywhere on earth
//maintains n periodically updates list of airlines and associated cheap rentals per airline in a program


public class JetAgent {

    //define properties here
    private final static int MAX_DESTINTATION = 100;
    private ArrayList<JetRecord> jetRentals;
    private DestinationRecord[] destinations;
    private ArrayList<ArrayList<JetRecord>> destinationIndex;


    //add constructor to initialize Jet agent properties
    public JetAgent() {
        jetRentals = new ArrayList<JetRecord>(MAX_DESTINTATION);
        destinations = new DestinationRecord[MAX_DESTINTATION];
        destinationIndex = new ArrayList<ArrayList<JetRecord>>(MAX_DESTINTATION);
    }


    public static void main(String[] args) {

        //instantiate a JetAgent object
        JetAgent newAgent = new JetAgent();
        //instantiate a Scanner object to get user input
        Scanner keyboard = new Scanner(System.in);


        while (true) {
            //add try
            try {

                System.out.println("Enter a number 1 to add a jet record, 2 to delete jet records, -1 to exit the program.");
                //read an int from the user, then use if-statement
                int input = keyboard.nextInt();
                //if the user types 1
                //get user input to insert a jet record, refer to the addJetRecord method
                if (input == 1) {
                    System.out.println("Enter airline: ");
                    String a = keyboard.next();
                    System.out.println("Enter Destination: ");
                    String b = keyboard.next();
                    System.out.println("Enter Rental Price: ");
                    int c = keyboard.nextInt();

                    JetRecord jr = new JetRecord(a, b, c);

                    newAgent.addJetRecord(jr);
                }
                //if the user types 2
                //get user input to delete jet records, refer to the deleteJetRecord method
                else if (input == 2) {
                    System.out.println("Enter airline: ");
                    String h = keyboard.next();
                    System.out.println("Enter destination: ");
                    String i = keyboard.next();

                    newAgent.deleteJetRecord(h, i);
                }
                //if the user types -1
                //exit the program
                else if (input == -1) {


                    break;
                }
                //all other inputs
                //Pint a message to user saying ("Invalid choice, try again!");

                else
                    System.out.println("Invalid choice, try again!");
            } catch (Exception e) {
                //add catch
                e.printStackTrace();
                break;
            }
        }
    }


    // this method adds a record (as defined in the Jet record class)
    //to a JetAgent property arraylist and updates
    //an array containing all destination.
    // if the destination contained in the record has been previously added(hence exists in the destinations array),
    // the method increases the count of that particular destination, else it creates a new destination and puts
    //in the array.
    public void addJetRecord(JetRecord record) {
        this.jetRentals.add(record);

        for (int i = 0; i < MAX_DESTINTATION; i++) {

            if (destinations[i] == null) {

                destinations[i] = new DestinationRecord(record.getDestination());
                destinations[i].incrementCount();
                break;
            } else if (destinations[i].getName().equals(record.getDestination())) {
                destinations[i].incrementCount();
                break;

            }


        }


    }


    // The delete record method removes the particular record we want deleted
    //by looking for the particular airline and destination combination given to it.
    // it updates the destination array by decrementing the count for any destination that has been deleted
    public void deleteJetRecord(String airline, String destination) {
        int dcount = 0; //how many records are deleted that are of the same destination
        for (int i = 0; i < jetRentals.size(); i++) {
            if (jetRentals.get(i).getDestination().equals(destination) && jetRentals.get(i).getAirline().equals(airline)) {
                this.jetRentals.remove(i);
                dcount++;
                i--; //because the remove method shifts the remaining elements to the left, we decrease i
            }
        }
        for (int i = 0; i < destinations.length && destinations[i] != null; i++) {
            if (destinations[i].getName().equals(destination)) {
                for (int j = 0; j < dcount; j++) {
                    destinations[i].decrementCount();
                }
                break;
            }
        }
    }


    // this method returns a jet record object that is the cheapest record in the list it is given
    // It does this by looping through the whole list and comparing the prices associated with all records
    // and returns the smallest
    public static JetRecord searchCheapestJetsByDestination(ArrayList<JetRecord> list, String destination) {

        JetRecord smallest_price = list.get(0); // initialize the first record to be the smallest
        int count = 0; // tells us if the list of records matches the destination given 1 or more times
        if (list.get(0).getDestination().equals(destination)) {

            count++;
        }

        for (int i = 1; i < list.size(); i++) {

            if (list.get(i).getDestination().equals(destination)) {
                count++;
                if (list.get(i).getRentalPrice() < list.get(i - 1).getRentalPrice()) {
                    smallest_price = list.get(i);


                }
            }

        }

        if (count == 0) {
            //if we get in here then the destination did not match any destination in the list and we return null
            smallest_price = null;
        }
        //System.out.println(count);
        return smallest_price;

    }


    //This method, slightly different from the previous method
    // also finds the cheapest jet, but takes as input only a list of records.
    // The method uses recursion to find the cheapest jet
    public static JetRecord searchCheapestJet(ArrayList<JetRecord> list) {

        JetRecord ans = null;

        if (list.size() == 1) {

            ans = list.get(0);

        }

       else if (list.size() == 2) {
            JetRecord temp = null;
            if (list.get(0).getRentalPrice() < list.get(1).getRentalPrice()) {
                ans = list.get(0);

            } else ans = list.get(1);
        }


        else if (list.size() > 2) {
            JetRecord temp = null;
            temp = list.remove(0);

            if (temp.getRentalPrice() < searchCheapestJet(list).getRentalPrice()) {

                ans = temp;
            } else ans = searchCheapestJet(list);
            list.add(0, temp); // This step is necessary because an arraylist remove method reduces the size of the list and confuses things
        }


        return ans;
    }


    // This method returns the name of the airline (from the records in the JetRecord list) that provides the
    // cheapest rates to the most destinations in the destinationRecord array that is passed to it

    public static String searchMostValuableAirline(ArrayList<JetRecord> list, DestinationRecord[] destinations) {
        String ans = null;
        String[] airlines = new String[destinations.length];
        int[] count = new int[destinations.length];// conatins the number representing how many times the airline at that index provided the cheapest rates to a certain destination

        JetRecord temp = null;

        for (int i = 0; i < destinations.length; i++) {

            if (destinations[i].getCount() > 0) {
                temp = searchCheapestJetsByDestination(list, destinations[i].getName()); // returns record that has cheapest rate to this destinatin

                airlines[i] = temp.getAirline(); // gets the name of the airline from the record above
            }
        }

        for (int i = 0; i < airlines.length; i++) {


            for (int j = 0; j < airlines.length; j++) {
                // compares each airline that is the cheapest to a certain destination with every other airline
                // if there is a match in airline names (indicating it provides cheap airlines to various destinations)
                // we increment count at the index of such an airline
                if (i != j) {

                    if (airlines[i].equals(airlines[j])) {
                        count[i]++;

                    }

                }

            }
        }
        ans = airlines[0];
        for (int i = 1; i < airlines.length; i++) {

            if (count[i] > count[i - 1]) {

                ans = airlines[i];
            }


        }


        //System.out.println(count[4]);
        return ans;


    }

    //The destinationIndex 2d arraylist is a more efficient means of storing data. The records
    // are stored by grouping records with the same destination together.

    public void addJetRecordToDestinationIndex(JetRecord record) {
        boolean found = false;
        if (destinationIndex.size() == 0) {
            destinationIndex.add(new ArrayList<JetRecord>());

            destinationIndex.get(0).add(record);
        } else
            for (int i = 0; i < destinationIndex.size(); i++) {

                if (record.getDestination().equals(destinationIndex.get(i).get(0).getDestination())) {
                    //we only checked the 0 index because all the records in the second dimension of destination index belong to the same destination
                    destinationIndex.get(i).add(record);
                    found = true;

                }

                if (!found) {
                    //we come in this block if the destinationIndex 2d Arraylist doesn't have the destination that this record has information for
                    destinationIndex.add(new ArrayList<JetRecord>());
                    destinationIndex.get(destinationIndex.size() - 1).add(record);


                }
            }


    }



    //This method is similar to the other method that searches for the most valuable airline.
    // the only difference is that this method loops though the destinationindex 2d arraylist

    public static String searchMostValuableAirlineByIndex(ArrayList<ArrayList<JetRecord>> destinationIndex) {
        String ans = null;
        String[] airlines = new String[destinationIndex.size()];
        int[] count = new int[destinationIndex.size()];

        JetRecord temp = null;
        for (int i = 0; i < destinationIndex.size(); i++) {
            temp = searchCheapestJetsByDestination(destinationIndex.get(i), destinationIndex.get(i).get(0).getDestination());
            airlines[i] = temp.getAirline();

        }
        for (int i = 0; i < airlines.length; i++) {


            for (int j = 0; j < airlines.length; j++) {

                if (i != j) {

                    if (airlines[i].equals(airlines[j])) {
                        count[i]++;

                    }
                }
            }
        }
        ans = airlines[0];
        for (int i = 1; i < airlines.length; i++) {

            if (count[i] > count[i - 1]) {

                ans = airlines[i];
            }
        }
        return ans;
    }




}