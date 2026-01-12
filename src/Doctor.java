public class Doctor {
    private int id;
    private static int idGen=1;
    private String fullName;
    private String specialization;

    public Doctor(String fullName,String specialization) {
        this.id = idGen++;
        setFullName(fullName);
        setSpecialization(specialization);
    }

    public int getId() {
        return id;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(int id) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName=fullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }
        this.specialization = specialization;
    }
    @Override public String toString() {
        return  "',name='" + getFullName() + "',specialization'" + getSpecialization()+ "'}";
    }
}
