package ch.codebulb.jsfrestfulprgviewscoped.model;

public enum EmploymentStatus {
    Unemployed, Employed, Firm_owner;
    
    @Override    
    public String toString() {
        return name().replaceAll("_", " ");
    }
}
