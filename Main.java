package com.company;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("WELCOME TO FLIPZON");
            System.out.println("1) Enter as Admin\n" +
                    "2) Explore the Product Catalog\n" +
                    "3) Show Available Deals\n" +
                    "4) Enter as Customer\n" +
                    "5) Exit the Application");
            int choice = input.nextInt();
            input.nextLine();
            if(choice==1)
                AdminClass.enter_as_admin();
            if(choice ==4)
                Customer1.customer_menu();
            if(choice==5)
                break;
            if(choice ==3)
                Category.browse_product_deals("normal");
            if(choice==2)
                Category.explore_product_catalog();

        }
    }
}
