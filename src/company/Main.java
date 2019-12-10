package company;


import service.Service;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Service a = new Service();

        if (a.validate("C:\\Users\\hp\\Desktop\\WT_LAB2\\wt_lab2\\resources\\airlineData.xsd",
                "C:\\Users\\hp\\Desktop\\WT_LAB2\\wt_lab2\\resources\\airlineData.xml")) {
            System.out.println("yes");
        }
        else {
            System.out.println("no");
        }
    }
}
