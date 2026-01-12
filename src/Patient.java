public class Patient {
    private int id;
    private static int idGen=1;
    private String fullName;
    private String email;
    private String phoneNumber;


    public Patient(String fullName,String email, String phoneNumber){
        this.id = idGen++;
        setFullName(fullName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }
    public int getId() {
        return id;
    }
    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName=fullName;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.email = email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
            this.phoneNumber=phoneNumber;
    }
    @Override public String toString() {
        return "Patient{id=" + getId() + ", name='" + getFullName() + "', email='" + email + "'}";
    }
}
