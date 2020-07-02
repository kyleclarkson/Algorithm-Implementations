package dynamicprograming.OneExpression;

public class DPWrapper {

    int integer;
    int minNumOfOnes;
    int numOfOperations;
    String representation;

    public DPWrapper() {
        this.integer = 0;
        this.minNumOfOnes = 0;
        this.representation = null;
        this.numOfOperations = 0;
    }

    public DPWrapper(DPWrapper wrapper) {
        this.minNumOfOnes = wrapper.getMinNumOfOnes();
        this.representation = wrapper.getRepresentation();
        this.numOfOperations = wrapper.getNumOfOperations();
    }

    public DPWrapper(int minNumOfOnes, String representation, int numOfOperations) {
        this.minNumOfOnes = minNumOfOnes;
        this.representation = representation;
        this.numOfOperations = numOfOperations;
    }

    public int getInteger() {return this.integer; }

    public int getMinNumOfOnes() { return this.minNumOfOnes;}

    public int getNumOfOperations() { return this.numOfOperations; }

    public String getRepresentation() { return this.representation; }

    public void setInteger(int integer) {this.integer = integer; }

    public void setMinNumOfOnes(int minNumOfOnes) {this.minNumOfOnes = minNumOfOnes; }

    public void setNumOfOperations(int numOfOperations) { this.numOfOperations = numOfOperations; }

    public void setRepresentation(String representation) {this.representation = representation;}

    public String toString()
    {
        return "Integer: " + this.integer
                + "\n Min num of ones: " + this.minNumOfOnes
                + "\nRep: " + this.representation + "\nNum of operations: "
                + this.numOfOperations +"\n";
    }
}
