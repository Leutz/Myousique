package frontend.controllers;

public final class UserInfo {
    private static int id;
    private static String lName;
    private static String fName;

    public static void User(int i, String lastName, String firstName) {
        id = i;
        lName = lastName;
        fName = firstName;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserInfo.id = id;
    }

    public static String getlName() {
        return lName;
    }

    public static void setlName(String lName) {
        UserInfo.lName = lName;
    }

    public static String getfName() {
        return fName;
    }

    public static void setfName(String fName) {
        UserInfo.fName = fName;
    }
}