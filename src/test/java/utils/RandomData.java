package utils;

public class RandomData {
    public static String generateEmail() {
        return "test_" + System.currentTimeMillis() + "@mailinator.com";
    }

    public static String generatePassword() {
        return "Qwerty123!";
    }

    public static String generateName() {
        return "User_" + System.currentTimeMillis();
    }
}