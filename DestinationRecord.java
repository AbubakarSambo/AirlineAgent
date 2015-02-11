/**
 * Created by abubakarsambo on 15-01-30.
 */
public class DestinationRecord {

    //name of destination
    //total number of jet records containing the destination


    private String name;
    private  int count;

    public DestinationRecord(String name) {

        this.name = name;
    }




    public String getName() {
        return this.name;
    }

    public int getCount() {
        return this.count;
    }

    public  void incrementCount() {
        count++;


    }

    public  void decrementCount() {

        count--;

    }


}
