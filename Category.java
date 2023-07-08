package com.company;

import java.util.Scanner;

class Category extends AdminClass{
    static Scanner input = new Scanner(System.in);
    private int categoryID;
    private String category_name;
    private Product[] product_array_of_particular_category = new Product[100];
    private int product_array_of_particular_category_ptr = 0;
    private static Category[] category_tracker_array = new Category[100];
    private static int[] taken_category_ID_array = new int[100];
    private static int taken_category_ID_array_ptr = 0 ;
    private static float[] giveaway_id1_array= new float[100];
    private static float[] giveaway_id2_array= new float[100];
    private static double[] giveaway_combined_price_aray = new double[100];
    private static double[] giveaway_combined_price_aray_elite = new double[100];
    private static double[] giveaway_combined_price_aray_prime = new double[100];
    private static int giveaway_id_array_ptr = 0 ;
    static protected float get_array1_giveaway_element(int index){
        return giveaway_id1_array[index];
    }
    static protected float get_array2_giveaway_element(int index){
        return giveaway_id2_array[index];
    }
    static protected double get_combined_price_giveaway_element(int index){
        return giveaway_combined_price_aray[index];
    }

    static protected double get_combined_price_giveaway_element_elite(int index){
        return giveaway_combined_price_aray_elite[index];
    }

    static protected double get_combined_price_giveaway_element_prime(int index){
        return giveaway_combined_price_aray_prime[index];
    }
    static protected String get_product_name(float id){
        int flag = 0 ;
        int category = (int)(id);
        for(int i = 0 ; i<taken_category_ID_array_ptr ; i++){
            if(taken_category_ID_array[i] == category){
                flag = 1;
            }
        }
        if(flag == 0){
            System.out.println("Product doesn't exist");
            return "PRODUCT IS NOT EXISTENT";
        }

        for(int i = 0 ; i<category_tracker_array[category].product_array_of_particular_category_ptr ; i++){
            if(category_tracker_array[category].product_array_of_particular_category[i].get_product_ID()==id){
                return category_tracker_array[category].product_array_of_particular_category[i].get_product_name();
            }
        }
        return "PRODUCT IS NOT EXISTENT";
    }
    static protected void add_giveaway_deals(){
        System.out.println("Enter 1st product ID:");
        float one = input.nextFloat();
        input.nextLine();
        System.out.println("Enter 2nd product ID:");
        float two = input.nextFloat();
        System.out.println("Combined price (Normal): "); //for normal
        double price = Category.get_price(one , (int)one) + Category.get_price(two , (int)two);
        while(true) {
            double x = input.nextDouble();
            if(x < price){
                price= x;
                System.out.println("Successfully set deal price to "+ price + "!!");
                break ;
            }
            else{
                System.out.println("Combined price: " + price);
                System.out.println("Added price: " + x);
                System.out.println("Try again!");
            }
        }

//        System.out.println(price);
        System.out.println("Combined price (Prime)");
        double price_prime = ((Category.get_price(one , (int)one) - ((Category.get_discount_percent("prime" ,one))*Category.get_price(one , (int)one))/100)   +   (Category.get_price(two , (int)two) - ((Category.get_discount_percent("prime" ,two))*Category.get_price(two , (int)two))/100)) ;
        while(true) {
            double x = input.nextDouble();
            if(x < price_prime){
                price_prime= x;
                System.out.println("Successfully set deal price to "+ price_prime+ "!!");
                break ;
            }
            else{
                System.out.println("Combined price: " + price_prime);
                System.out.println("Added price: " + x);
                System.out.println("Try again!");
            }
        }
        System.out.println("Combined price (Elite)");
        double price_elite = ((Category.get_price(one , (int)one) - ((Category.get_discount_percent("elite" ,one))*Category.get_price(one , (int)one))/100)   +   (Category.get_price(two , (int)two) - ((Category.get_discount_percent("elite" ,two))*Category.get_price(two , (int)two))/100)) ;
        while(true) {
            double x = input.nextDouble();
            if(x < price_elite){
                price_elite= x;
                System.out.println("Successfully set deal price to "+ price_elite + "!!");
                break ;
            }
            else{
                System.out.println("Combined price: " + price_elite);
                System.out.println("Added price: " + x);
                System.out.println("Try again!");
            }
        }

        giveaway_id1_array[giveaway_id_array_ptr] = one;
        giveaway_id2_array[giveaway_id_array_ptr] = two;
        giveaway_combined_price_aray[giveaway_id_array_ptr] = price ;
        giveaway_combined_price_aray_prime[giveaway_id_array_ptr] = price_prime ;
        giveaway_combined_price_aray_elite[giveaway_id_array_ptr] = price_elite ;
        giveaway_id_array_ptr++;
    }
    static protected void browse_product_deals(String status){
        if(giveaway_id_array_ptr==0){
            System.out.println("Dear User, there are no deals for now!!! Please check regularly for exciting deals");
            return ;
        }
        for(int i = 0 ; i<giveaway_id_array_ptr ; i++){
            System.out.print(i+".");
            if(status.equals("normal"))
                System.out.println("Get "+ Category.get_product_name(giveaway_id1_array[i])+ " and "+ Category.get_product_name(giveaway_id2_array[i]) +" for a combined price of Rs."+giveaway_combined_price_aray[i]);
            else if(status.equals("prime"))
                System.out.println("Get "+ Category.get_product_name(giveaway_id1_array[i])+ " and "+ Category.get_product_name(giveaway_id2_array[i]) +" for a combined price of Rs."+giveaway_combined_price_aray_prime[i]);
            else if(status.equals("elite"))
                System.out.println("Get "+ Category.get_product_name(giveaway_id1_array[i])+ " and "+ Category.get_product_name(giveaway_id2_array[i]) +" for a combined price of Rs."+giveaway_combined_price_aray_elite[i]);

        }

    }
    static protected void set_discount_on_product() {
        System.out.println("Enter product ID:");
        float product_id = input.nextFloat();
        System.out.println("Enter discount(%) for");
        System.out.println("Elite");
        int x = input.nextInt();
        input.nextLine();
        System.out.println("Prime");
        int y = input.nextInt();
        input.nextLine();
        System.out.println("Normal");
        int z = input.nextInt();
        input.nextLine();
        int category = (int)product_id;
        for(int i = 0 ; i<category_tracker_array[category].product_array_of_particular_category_ptr; i++){
            if(category_tracker_array[category].product_array_of_particular_category[i].get_product_ID()==product_id){
                category_tracker_array[category].product_array_of_particular_category[i].set_product_discount(x,y,z);
            }
        }
    }
    protected static int get_price(float id , int category){
        int flag = 0 ;
        for(int i = 0 ; i<taken_category_ID_array_ptr ; i++){
            if(taken_category_ID_array[i] == category){
                flag = 1;
            }
        }
        if(flag == 0){
            System.out.println("Product doesn't exist");
            return flag;
        }

        for(int i = 0 ; i<category_tracker_array[category].product_array_of_particular_category_ptr ; i++){
            if(category_tracker_array[category].product_array_of_particular_category[i].get_product_ID()==id){
                return category_tracker_array[category].product_array_of_particular_category[i].get_product_price();
            }
        }
        return 0;
    }
    protected static int get_product_details(float id , int category){
        int flag = 0 ;
        for(int i = 0 ; i<taken_category_ID_array_ptr ; i++){
            if(taken_category_ID_array[i] == category){
                flag = 1;
            }
        }
        if(flag == 0){
            System.out.println("Product doesn't exist");
            return flag;
        }
        for(int i = 0 ; i<category_tracker_array[category].product_array_of_particular_category_ptr ; i++){
            if(category_tracker_array[category].product_array_of_particular_category[i].get_product_ID()==id){
                System.out.println("NAME: "+ category_tracker_array[category].product_array_of_particular_category[i].get_product_name());
                System.out.println("ID: "+ category_tracker_array[category].product_array_of_particular_category[i].get_product_ID());
                System.out.println("PRICE: "+ category_tracker_array[category].product_array_of_particular_category[i].get_product_price());
                System.out.println("Prime discount(%): " + category_tracker_array[category].product_array_of_particular_category[i].get_product_discount("prime"));
                System.out.println("Elite discount(%): "+ category_tracker_array[category].product_array_of_particular_category[i].get_product_discount("elite"));
                System.out.println("SPECIFICATIONS: ");
                category_tracker_array[category].product_array_of_particular_category[i].get_other_details();
                System.out.println("STOCK: " + category_tracker_array[category].product_array_of_particular_category[i].get_stock());
            }

        }
        return 0;
    }
    static int get_discount_percent(String status , float product_id){
        int category = (int)product_id ;
        for(int i = 0 ; i<category_tracker_array[category].product_array_of_particular_category_ptr; i++){
            if(category_tracker_array[category].product_array_of_particular_category[i].get_product_ID()==product_id){
                return category_tracker_array[category].product_array_of_particular_category[i].get_product_discount(status);
            }
        }
        return 0 ;
    }
    protected static int get_product_details(float id){
        int flag = 0 ;
        int category = (int)id;
        for(int i = 0 ; i<taken_category_ID_array_ptr ; i++){
            if(taken_category_ID_array[i] == category){
                flag = 1;
            }
        }
        if(flag == 0){
            System.out.println("Product doesn't exist");
            return flag;
        }
        for(int i = 0 ; i<category_tracker_array[category].product_array_of_particular_category_ptr ; i++){
            if(category_tracker_array[category].product_array_of_particular_category[i].get_product_ID()==id){
                System.out.println("NAME: "+ category_tracker_array[category].product_array_of_particular_category[i].get_product_name());
                System.out.println("ID: "+ category_tracker_array[category].product_array_of_particular_category[i].get_product_ID());
                System.out.println("PRICE: "+ category_tracker_array[category].product_array_of_particular_category[i].get_product_price());
                System.out.println("Prime discount(%): " + category_tracker_array[category].product_array_of_particular_category[i].get_product_discount("prime"));
                System.out.println("Elite discount(%): "+ category_tracker_array[category].product_array_of_particular_category[i].get_product_discount("elite"));
                System.out.println("SPECIFICATIONS: ");
                category_tracker_array[category].product_array_of_particular_category[i].get_other_details();
                System.out.println("STOCK: ");
                System.out.println(category_tracker_array[category].product_array_of_particular_category[i].get_stock());

            }
        }
        return 0;
    }

    protected static int reduce_stock(int amount , float id){
        int category = (int)id;
        for(int i = 0 ; i<category_tracker_array[category].product_array_of_particular_category_ptr ; i++) {
            if (category_tracker_array[category].product_array_of_particular_category[i].get_product_ID() == id) {
                int new_value = category_tracker_array[category].product_array_of_particular_category[i].stock_kam_karo(amount);
                if(new_value == -1){
                    return -1;
                }
            }
        }
        return 0;
    }

    protected static int get_product_stock(float id){
        int flag = 0 ;
        int category = (int)id;
        for(int i = 0 ; i<taken_category_ID_array_ptr ; i++){
            if(taken_category_ID_array[i] == category){
                flag = 1;
            }
        }
        if(flag == 0){
            System.out.println("Product doesn't exist");
            return flag;
        }
        for(int i = 0 ; i<category_tracker_array[category].product_array_of_particular_category_ptr ; i++){
            if(category_tracker_array[category].product_array_of_particular_category[i].get_product_ID()==id){
                return category_tracker_array[category].product_array_of_particular_category[i].get_stock();

            }
        }
        return 0;

    }
    protected static void explore_product_catalog(){
        if(taken_category_ID_array_ptr==0){
            System.out.println("No products available yet!");
            return ;
        }
        System.out.println("AVAILABLE PRODUCTS");
        for(int i = 0 ; i < taken_category_ID_array_ptr; i++){
            for(int j = 0 ; j< category_tracker_array[taken_category_ID_array[i]].product_array_of_particular_category_ptr ; j++) {
                get_product_details(category_tracker_array[taken_category_ID_array[i]].product_array_of_particular_category[j].get_product_ID());
                System.out.println("------------------------------");
            }

        }
    }
    protected void set_category_name(){
        System.out.println("Enter category name: ");
        String x = input.nextLine();
        this.category_name = x;
    }
    protected static void delete_a_category(int id){
        for(int i = 0 ; i<taken_category_ID_array_ptr ; i++){
            if(taken_category_ID_array[i]==id){
                System.out.println("Category name: " + category_tracker_array[id].category_name);
                System.out.println("Category ID: "+ taken_category_ID_array[i]);
                System.out.println("HAS BEEN DELETED SUCCESSFULLY!");
                taken_category_ID_array[i]= 0;
                category_tracker_array[id] = null;
                return;
            }
        }
        System.out.println("Category doesn't exist!");
    }
    protected int set_category_ID(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter category ID: ");
        int x = input.nextInt();
        input.nextLine();
        for(int i = 0 ;  i <taken_category_ID_array_ptr ; i++){
            if(taken_category_ID_array[i]==x){
                System.out.println("Category with name: " + category_tracker_array[x].category_name + " already exists!!");
                return -1;
            }
        }
        this.categoryID = x;
        taken_category_ID_array[taken_category_ID_array_ptr++] = x;
        return 0;
    }
    protected void add_product_to_category(){
        Product latest = new Product();
        product_array_of_particular_category[product_array_of_particular_category_ptr++]=latest;
        latest.set_product_name();
        latest.set_product_ID();
        latest.set_product_price();
        latest.set_product_other_details();
        latest.set_product_stock();
    }

    protected static void append_category_to_tracker_array(Category x){
        category_tracker_array[x.categoryID] = x;
        if(x.product_array_of_particular_category_ptr == 0){
            x.add_product_to_category();
        }
    }
    protected static Category get_category_object(int index){
        return category_tracker_array[index];
    }
    protected void delete_a_product_from_this_category(float product_id) {
        for(int i = 0 ; i< this.product_array_of_particular_category_ptr ; i++){
            if(this.product_array_of_particular_category[i].get_product_ID() == product_id){
                this.product_array_of_particular_category[i].set_product_deleted_status();
            }
        }
    }

    //METHODS CHILD CLASSES
    protected void set_product_stock(){};
    protected void set_product_price(){};
    protected void set_product_other_details(){};
    protected void set_product_deleted_status(){};
    protected int get_product_price(){
        return 0;
    };
    protected void get_other_details(){};
    protected String get_product_name(){
        return null;
    };



}

interface ProductPublic{
    abstract int get_stock();
}

class Product extends Category implements ProductPublic{
    Scanner input = new Scanner(System.in);
    private String product_name;
    private int product_price;
    private String other_details;
    private float product_id;
    private boolean product_deleted_status = false;
    private int elite_discount ;
    private int prime_discount ;
    private int normal_discount ;
    private int product_stock;

    @Override
    protected void set_product_stock(){
        System.out.println("Enter product stock: ");
        int x = input.nextInt();
        this.product_stock = x;
    }


    protected void set_product_name(){
        System.out.println("Enter product name: ");
        String x = input.nextLine();
        this.product_name = x;
    }

    protected void set_product_ID(){
        System.out.println("Enter product ID: ");
        float x = input.nextFloat();
        this.product_id = x;
    }

    @Override
    protected void set_product_price(){
        System.out.println("Enter product price: ");
        int x = input.nextInt();
        input.nextLine();
        this.product_price = x;
    }

    @Override
    protected void set_product_other_details(){
        System.out.println("Enter other details of the product:");
        String x = input.nextLine();
        this.other_details = x;
    }

    protected float get_product_ID(){
        return this.product_id;
    }

    @Override
    protected void set_product_deleted_status(){
        this.product_deleted_status = true ;
    }


    protected int get_product_price(){
        return this.product_price;
    }

    @Override
    protected void get_other_details(){
        System.out.println(this.other_details);
    }

    @Override
    protected String get_product_name(){
        return this.product_name;
    }

    protected void set_product_discount(int elite , int prime , int normal){
        this.normal_discount= normal;
        this.elite_discount= elite;
        this.prime_discount = prime ;
    }

    protected int get_product_discount(String status){
        if(status.equals("normal"))
            return this.normal_discount;
        else if(status.equals("elite"))
            return this.elite_discount;
        else
            return this.prime_discount;
    }

    @Override
    public int get_stock(){
        return this.product_stock;
    } //made public since interface needs functions to be public and a public access to getting stock of a store wont really matter even if its public

    int stock_kam_karo(int amount){
        if(this.get_stock() < amount){
            return -1;
        }
        this.product_stock -= amount ;
        return 0 ;
    }
}
