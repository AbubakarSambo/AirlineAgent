/**
 * Created by abubakarsambo on 15-01-30.
 */
public class JetRecord {


    private String airline;
    private String destination;
    private int rentalPrice;

    public JetRecord(String airline, String destination, int rentalPrice) {
        this.airline = airline;
        this.destination = destination;
        this.rentalPrice = rentalPrice;

    }


    public String getAirline() {

        return this.airline;
    }

    public String getDestination() {

        return this.destination;
    }


    public int getRentalPrice() {


        return this.rentalPrice;
    }


}



