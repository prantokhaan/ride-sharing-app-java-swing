import java.io.Serializable;

class User implements Serializable {
    private String name;
    private String email;
    private String password;
    private String username;
    private String gender;
    private int age;

    public User(){}

    public User(String name, String email, String password, String username, String gender, int age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.gender = gender;
        this.age = age;
    }

    // Getters and setters for the attributes

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class Customer extends User implements Serializable {
    private String phoneNumber;

    public Customer(){}

    public Customer(String name, String email, String password, String username, String gender, int age, String phoneNumber) {
        super(name, email, password, username, gender, age);
        this.phoneNumber = phoneNumber;
    }

    // Getter and setter for the phone number

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

class Rider extends User implements Serializable {
    private String phoneNumber;
    private String nid;
    private String drivingLicense;
    private String bikeLicense;
    private boolean approvalStatus = false;
    private int balance = 0;

    public Rider(String name, String email, String password, String username, String gender, int age, String phoneNumber, String nid, String drivingLicense, String bikeLicense) {
        super(name, email, password, username, gender, age);
        this.phoneNumber = phoneNumber;
        this.bikeLicense = bikeLicense;
        this.drivingLicense = drivingLicense;
        this.nid = nid;
    }

    // Getter and setter for the phone number

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNid(){
        return nid;
    }

    public void setNid(String nid){
        this.nid = nid;
    }

    public String getDrivingLicense(){
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense){
        this.drivingLicense = drivingLicense;
    }

    public String getBikeLicense(){
        return bikeLicense;
    }

    public void setBikeLicense(String bikeLicense){
        this.bikeLicense = bikeLicense;
    }

    public String getApprovalStatus(){
        if(approvalStatus) return "Approved";
        else return "Not Approved";
    }

    public void approveRider(){
        approvalStatus = true;
    }

    public void blockRider(){
        approvalStatus = false;
    }

    public int getBalance(){
        return balance;
    }

    public void setBalance(int balance){
        this.balance =balance;
    }

    public void addBalance(int balance){
        this.balance+=balance;
    }

}
