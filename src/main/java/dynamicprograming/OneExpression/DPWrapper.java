package dynamicprograming.OneExpression;

public class DPWrapper {

    int integer;
    String representation;

    public DPWrapper() {
        this.integer = 0;
        this.representation = null;
    }

    public DPWrapper(DPWrapper wrapper) {
        this.integer = wrapper.getInteger();
        this.representation = wrapper.getRepresentation();
    }

    public DPWrapper(int integer, String representation) {
        this.integer = integer;
        this.representation = representation;
    }

    public int getInteger() { return this.integer ;}

    public String getRepresentation() { return this.representation; }

    public void setInteger(int integer) {this.integer = integer; }

    public void setRepresentation(String representation) {this.representation = representation;}
}
