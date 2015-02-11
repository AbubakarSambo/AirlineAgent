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


    //add constructor
    public JetAgent() {
        jetRentals = new ArrayList<JetRecord>(MAX_DESTINTATION);
        destinations = new DestinationRecord[MAX_DESTINTATION];
        destinationIndex = new ArrayList<ArrayList<JetRecord>>(MAX_DESTINTATION);
    }

    //add methods

    /* public static void main(String[] args) {

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
             }
         }
     }
 */
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

    public void deleteJetRecord(String airline, String destination) {
        int dcount = 0; //how many records are deleted that are of the same destination
        for (int i = 0; i < jetRentals.size(); i++) {
            if (jetRentals.get(i).getDestination().equals(destination) && jetRentals.get(i).getAirline().equals(airline)) {
                this.jetRentals.remove(i);
                dcount++;
                i--;
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

    public static JetRecord searchCheapestJetsByDestination(ArrayList<JetRecord> list, String destination) {

        JetRecord smallest_price = list.get(0);
        int count = 0;
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

            smallest_price = null;
        }
        //System.out.println(count);
        return smallest_price;

    }


    public static JetRecord searchCheapestJet(ArrayList<JetRecord> list) {
        //System.out.println(list.size());
        JetRecord ans = null;

        if (list.size() == 1) {
            System.out.println("=1");
            ans = list.get(0);

        }

        if (list.size() == 2) {
            JetRecord temp = null;
            if (list.get(0).getRentalPrice() < list.get(1).getRentalPrice()) {
                ans = list.get(0);
                //    temp = list.remove(1);
                System.out.println(" =2 ");


            } else ans = list.get(1);


        }


        if (list.size() > 2) {
            JetRecord temp = null;
            temp = list.remove(0);

            if (temp.getRentalPrice() < searchCheapestJet(list).getRentalPrice()) {

                ans = temp;
            } else ans = searchCheapestJet(list);
            list.add(0, temp);
        }


        return ans;
    }


    public static String searchMostValuableAirline(ArrayList<JetRecord> list, DestinationRecord[] destinations) {
        String ans = null;
        String[] airlines = new String[destinations.length];
        int[] count = new int[destinations.length];

        JetRecord temp = null;

        for (int i = 0; i < destinations.length; i++) {

            if (destinations[i].getCount() > 0) {
                temp = searchCheapestJetsByDestination(list, destinations[i].getName());

                airlines[i] = temp.getAirline();
            }
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


        //System.out.println(count[4]);
        return ans;


    }

    public void addJetRecordToDestinationIndex(JetRecord record) {
        boolean destinationFound = false;
        if (destinationIndex.size() == 0) {
            destinationIndex.add(new ArrayList<JetRecord>());

            destinationIndex.get(0).add(record);
        } else
            for (int i = 0; i < destinationIndex.size(); i++) {

                if (record.getDestination().equals(destinationIndex.get(i).get(0).getDestination())) {
                    destinationIndex.get(i).add(record);
                    destinationFound = true;

                }

                if (!destinationFound) {
                    destinationIndex.add(new ArrayList<JetRecord>());
                    destinationIndex.get(destinationIndex.size() - 1).add(record);


                }
            }


    }

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



    public  void addJetRecordToDestinationIndexNew(JetRecord record){

        boolean destinationFound = false;
        if (destinationIndex.size() == 0) {
            destinationIndex.add(new ArrayList<JetRecord>());

            destinationIndex.get(0).add(record);
        } else
            for (int i = 0; i < destinationIndex.size(); i++) {

                if (record.getDestination().equals(destinationIndex.get(i).get(0).getDestination())) {

                    destinationIndex.get(i).add(record);
                    destinationFound = true;

                }

                if (!destinationFound) {
                    destinationIndex.add(new ArrayList<JetRecord>());
                    destinationIndex.get(destinationIndex.size() - 1).add(record);


                }
            }



    }







}