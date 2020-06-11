package com.example.activitymonitor;

/**
 * User() class which is used to store user objects retreived from the firebase cloud storage
 */
public class User {
    private int Age;
    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private int UserType;
    private double Weight;

    /**
     * User()
     * @param age Age of user
     * @param firstName first name of user
     * @param lastName last name of user
     * @param phoneNumber phone number of user
     * @param userType UserType integer. 1= coach, 2= athlete
     * @param weight
     */
    public User(int age, String firstName, String lastName, String phoneNumber, int userType, double weight) {
        Age = age;
        FirstName = firstName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
        UserType = userType;
        Weight = weight;
    }

    /**
     * User() empty constructor for FireStore
     */
    public User(){

    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }
}