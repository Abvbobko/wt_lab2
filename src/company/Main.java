package company;


import service.Service;

public class Main {

    public static void main(String[] args) {
        Service service = Service.getInstance();
        System.out.println("Xml to Xsd validation.");
        service.validateXml();

        System.out.println("Xml parsing.");

        System.out.println("Connection to DB.");
        service.connectDB();

        System.out.println("Migration to DB.");

        System.out.println("Close DB connection.");
        service.closeDB();

        System.out.println("End.");
    }
}
