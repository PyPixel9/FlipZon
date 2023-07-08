package com.company;

import java.util.Scanner;

abstract class Admin{
    static void add_category(){}
    static void delete_category() {}
    static void add_product(){}
    static void delete_product() {}
    static void set_discount_on_product() {}
}



class AdminClass extends Admin{
    Scanner input = new Scanner(System.in);
    private static String username1 = "Beff Jezos";
    private static String username2 = "Gill Bates";
    private static String username3 = "utkarsh";
    private static int password = 2021570;

    static void enter_as_admin() {
        Scanner input = new Scanner(System.in);
        System.out.println("Dear Admin");
        System.out.println("Enter username:");
        String x = input.nextLine();
        System.out.println("Enter password");
        int y = input.nextInt();
        if((x.equals(username1) || x.equals(username2) || x.equals(username3)) && y==AdminClass.password){
            admin_menu(x);
        }
    }

    private static void admin_menu(String x){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome "+ x+" !!!");
        while(true) {
            System.out.println("Please choose any one of the following actions:\n" +
                    "1) Add category\n" +
                    "2) Delete category\n" +
                    "3) Add Product\n" +
                    "4) Delete Product\n" +
                    "5) Set Discount on Product\n" +
                    "6) Add giveaway deal\n" +
                    "7) Back");
            int choice = input.nextInt();
            if (choice == 1)
                add_category();
            if (choice ==2)
                delete_category();
            if (choice == 3)
                add_product();
            if (choice ==4)
                delete_product();
            if(choice ==7)
                return ;
            if(choice ==5){
                Category.set_discount_on_product();
            }
            if(choice ==2){
                Category.explore_product_catalog();
            }
            if(choice ==6)
                Category.add_giveaway_deals();
        }
    }


    static void add_category() {
        Category latest = new Category();
        int return_value = latest.set_category_ID();
        if(return_value == -1){
            return ;
        }
        latest.set_category_name();
        Category.append_category_to_tracker_array(latest);
    }



    static void add_product(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter category ID: ");
        int x = input.nextInt();
        input.nextLine();
        Category we_wanted = Category.get_category_object(x);
        we_wanted.add_product_to_category();
    }

    public static void delete_category() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter category ID: ");
        int x = input.nextInt();
        Category.delete_a_category(x);
    }


    static void delete_product() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the product ID: ");
        float product_id = input.nextFloat();
        System.out.println("-------------------------------\nNOTE: for product ID eg: 3.4 : the category id is 3\n-------------------------------");
        System.out.println("Enter the product's category ID: ");
        int category_id = input.nextInt();
        Category we_need = Category.get_category_object(category_id);
        we_need.delete_a_product_from_this_category(product_id);

    }
}

