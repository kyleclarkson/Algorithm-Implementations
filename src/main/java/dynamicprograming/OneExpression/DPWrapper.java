package dynamicprograming.OneExpression;

public class DPWrapper {

    int integer;
    int numOfOperations;
    String representation;

    public DPWrapper() {
        this.integer = 0;
        this.representation = null;
        this.numOfOperations = 0;
    }

    public DPWrapper(DPWrapper wrapper) {
        this.integer = wrapper.getInteger();
        this.representation = wrapper.getRepresentation();
        this.numOfOperations = wrapper.getNumOfOperations();
    }

    public DPWrapper(int integer, String representation, int numOfOperations) {
        this.integer = integer;
        this.representation = representation;
        this.numOfOperations = numOfOperations;
    }

    public int getInteger() { return this.integer ;}

    public int getNumOfOperations() { return this.numOfOperations; }

    public String getRepresentation() { return this.representation; }

    public void setInteger(int integer) {this.integer = integer; }

    public void setNumOfOperations(int numOfOperations) { this.numOfOperations = numOfOperations; }

    public void setRepresentation(String representation) {this.representation = representation;}

    public String toString()
    {
        return "Integer: " + this.integer + "\nRep: "
                + this.representation + "\nNum of operations: "
                + this.numOfOperations +"\n";
    }
}
