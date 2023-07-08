package com.company;
import java.util.*;

//interface CustomerDesign{
//    static void customer_menu() {}
//    static void sign_up() {}
//    abstract void add_products_to_cart();
//    abstract void make_payment();
//    abstract void successful_login_menu();
//    abstract void view_cart();
//    abstract void add_deal_products_to_cart();
//}
//interface not implemented since didn't want to make any function public

interface CustomerMenu{

}

abstract class Customer{
    abstract void upgrade_status();
    abstract void make_payment();
    abstract void successful_login_menu();
    abstract void view_cart();
    abstract void add_deal_products_to_cart();
    abstract void checkout_cart();
    abstract void setCustomer_name(String name);
    abstract void setCustomer_balance(float customer_balance);
    abstract void setCustomer_status(String customer_status);
    abstract void setCustomer_password(String customer_password);
    abstract String getCustomer_name();
    abstract String getCustomer_password();
    abstract String getCustomer_status();
    abstract float getCustomer_balance();
    abstract String getCustomer_password(String username);
}

class Customer1 extends Customer {
    Scanner input = new Scanner(System.in);
    static Scanner input1 = new Scanner(System.in);
    private String customer_name ;
    private String customer_password ;
    private float customer_balance=1000 ;
    private String customer_status="normal";
    private String other_details;
    private static Customer1[] customer_tracking_array = new Customer1[100];
    private static int customer_tracking_array_ptr = 0 ;
    private int[] cart_price_array = new int[100];
    private int[] cart_quantity_array = new int[100];
    private int[] cart_discount_array =new int[100];
    private float[] cart_product_id_array = new float[100];
    private int cart_ptr = 0 ;
    private double instantaneous_total_price = 0 ;
    private int[] coupon_array = new int[100];
    private int coupon_array_ptr = 0;
    private int[] array_to_track_if_giveaway_deal_product = new int[100];

    protected static void customer_menu(){
        Scanner input = new Scanner(System.in);
        System.out.println("1) Sign up\n" +
                "2) Log in\n" +
                "3) Back");
        int choice = input.nextInt();input.nextLine();
        if(choice ==1){
            sign_up();
        }
        if(choice ==3){
            return ;
        }
        if(choice ==2){
            login();
        }
    }
    protected void set_other_details(String z){
        this.other_details = z;
    }

    private static void sign_up(){
        Scanner input = new Scanner(System.in);
        System.out.println("Set name: ");
        String x = input.nextLine();
        System.out.println("Set password: ");
        String y = input.nextLine();
        System.out.println("Enter age\nEnter email ID\nEnter phone no");
        String z = input.nextLine();


        Customer1 latest = new Customer1();
        latest.customer_name=x;
        latest.customer_password=y;
        latest.set_other_details(z);
        Customer1.append_to_customer_tracking_array(latest);
        System.out.println("Successfully registered!");
    }
    private static void login(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter name: ");
        String x = input.nextLine();
        System.out.println("Enter password: ");
        String y = input.nextLine();
        for(int i = 0 ; i<Customer1.customer_tracking_array_ptr ; i++){
            if(Customer1.customer_tracking_array[i].customer_name.equals(x)&&Customer1.customer_tracking_array[i].customer_password.equals(y)){
                customer_tracking_array[i].successful_login_menu();
            }
        }
    }
    void successful_login_menu(){
        while(true) {
            System.out.println("Welcome " + this.customer_name);
            System.out.println("1) browse products\n" +
                    "2) browse deals\n" +
                    "3) add a product to cart\n" +
                    "4) add products in deal to cart\n" +
                    "5) view coupons\n" +
                    "6) check account balance\n" +
                    "7) view cart\n" +
                    "8) empty cart\n" +
                    "9) checkout cart\n" +
                    "10) upgrade customer status\n" +
                    "11) Add amount to wallet\n" +
                    "12) back\n"+
                    "13) MAKE PAYMENT"
            );
            int choice = input.nextInt();
            input.nextLine();
            if(choice ==1){
                Category.explore_product_catalog();
            }
            if (choice == 12)
                return;
            if(choice == 10){
                upgrade_status();
            }
            if(choice ==11){
                add_amount_to_wallet();
            }
            if(choice ==6){
                System.out.println(this.customer_balance);
            }
            if(choice ==3){
                add_product_to_cart();
            }
            if(choice ==7)
                view_cart();
            if(choice ==8) {
                cart_ptr = 0;
                System.out.println("Cart successfully emptied");
            }
            if(choice ==9 ){
                checkout_cart();
            }
            if(choice ==13)
                make_payment();
            if(choice ==2)
                Category.browse_product_deals(this.customer_status);
            if(choice == 4){
                this.add_deal_products_to_cart();
            }
            if(choice ==5){
                System.out.println("AVAILABLE COUPONS");
                for(int i = 0 ; i<coupon_array_ptr ; i++){
                    if(coupon_array[i]!=0) {
                        System.out.println(coupon_array[i] + "%");
                    }
                }
            }
        }
    }
    void add_deal_products_to_cart(){
        Category.browse_product_deals(this.customer_status);
        System.out.println("Add index of the deal you wish to add to cart (-1 to return)");
        int choice = input.nextInt();
        input.nextLine();
        if(choice ==-1) {
            return;
        }
        cart_product_id_array[cart_ptr] = Category.get_array1_giveaway_element(choice);
        cart_quantity_array[cart_ptr] = 1;
        cart_price_array[cart_ptr]=0;
        array_to_track_if_giveaway_deal_product[cart_ptr]=1;
        cart_ptr++;
        cart_product_id_array[cart_ptr] = Category.get_array2_giveaway_element(choice);
        if(this.customer_status.equals("elite")) {
            cart_price_array[cart_ptr] = (int) Category.get_combined_price_giveaway_element_elite(choice);
        }
        else if(this.customer_status.equals("prime")){
            cart_price_array[cart_ptr] = (int) Category.get_combined_price_giveaway_element_prime(choice);
        }
        else{
            cart_price_array[cart_ptr] = (int) Category.get_combined_price_giveaway_element(choice);
        }

        cart_quantity_array[cart_ptr]= 1;
        array_to_track_if_giveaway_deal_product[cart_ptr]=1;
        cart_ptr++;
        System.out.println("Successfully added to cart!");
    }
    void view_cart(){
        int total_price = 0 ;
        if(cart_ptr==0){
            System.out.println("Nothing added to cart!");
            return;
        }

        for(int i =0 ; i<cart_ptr ; i++){
            if(Category.get_product_stock(cart_product_id_array[i])<cart_quantity_array[i]){
                System.out.println("WARNING! OUT OF STOCK");
                System.out.println("product "+ Category.get_product_name(cart_product_id_array[i]) +" has only "+Category.get_product_stock(cart_product_id_array[i])+" units");
                cart_ptr = 0 ;
                System.out.println("Cart has been emptied!");
                return ;
            }
        }


        for(int i = 0 ; i<cart_ptr ; i++){
            System.out.println(Category.get_product_name(cart_product_id_array[i])+ "   QUANTITY:" + cart_quantity_array[i]);
        }
    }
    protected void add_amount_to_wallet(){
        System.out.println("current amount: " + this.customer_balance);
        System.out.println("how much amount you wanna add: ");
        int x = input.nextInt(); input.nextLine();
        this.customer_balance+=x;
    }
    void upgrade_status() {
        System.out.println("current status: " + this.customer_status);
        System.out.println("Choose new status: ");
        System.out.println("1. prime");
        System.out.println("2. elite");
        int choice = input.nextInt();
        if(choice == 1) {
            this.customer_balance -= 200;
            this.customer_status = "prime";
        }
        if(choice ==2){
            this.customer_balance -= 300;
            this.customer_status = "elite";
        }
    }
    void add_product_to_cart(){
        System.out.println("product ID: ");
        float id = input.nextFloat(); input.nextLine();
        System.out.println("quantity: ");
        int q = input.nextInt(); input.nextLine();
        int category_number = (int)id;
        System.out.println(category_number);
        float product_id = id;
//        int disc_status = Category.get_discount_percent(this.customer_status  , product_id);
        cart_price_array[cart_ptr]=Category.get_price(product_id , category_number);
        cart_quantity_array[cart_ptr]=q;
        cart_product_id_array[cart_ptr]=id ;
        array_to_track_if_giveaway_deal_product[cart_ptr] = 0 ;
        cart_ptr++;
        System.out.println("details: ");
        Category.get_product_details(product_id,category_number);
    }



    void checkout_cart(){
        System.out.println("Details of your order: ");
        double tc = 0 ;

        double max_coupon=0;
        int index=99 ;
        for(int i = 0 ; i <coupon_array_ptr ; i++){
            if(coupon_array[i]>max_coupon){
                max_coupon =coupon_array[i];
                index = i;
            }
        }

        double total_price = 0;

        System.out.println("NON DEAL ITEMS");
        for(int i= 0 ; i<cart_ptr ; i++){
            if(array_to_track_if_giveaway_deal_product[i]==0) {
                tc+= cart_price_array[i]*cart_quantity_array[i];
                System.out.println(Category.get_product_name(cart_product_id_array[i]));
                System.out.println("ID: " + cart_product_id_array[i]);
                System.out.println("PRICE: " + cart_price_array[i]);
                double item_price = cart_price_array[i];
//                if(Category.get_discount_percent(this.customer_status , cart_product_id_array[i]) > max_coupon){
//                    item_price-= (item_price*Category.get_discount_percent(this.customer_status , cart_product_id_array[i]))/100;
//                }
//                else{
//                    item_price-= (item_price*max_coupon)/100;
//                }
                double discount_percentage=0;
                if(this.customer_status.equals("normal")) {
                    discount_percentage = Math.max(max_coupon, Category.get_discount_percent(this.customer_status, cart_product_id_array[i]));
                }
                if(this.customer_status.equals("prime")) {
                    discount_percentage= Math.max(5 ,Math.max(max_coupon, Category.get_discount_percent(this.customer_status, cart_product_id_array[i])));
                }
                else if(this.customer_status.equals("elite")) {
                    discount_percentage= Math.max(10 ,Math.max(max_coupon, Category.get_discount_percent(this.customer_status, cart_product_id_array[i])));
                }
                System.out.println(Category.get_product_name(cart_product_id_array[i]) + "      --> "+ discount_percentage+ "% applied!");
                item_price-= (item_price*discount_percentage)/100;
                total_price += item_price * cart_quantity_array[i];
            }
        }
        System.out.println("--------------------");
        double giveaway_price = 0 ;
        System.out.println("GIVEAWAY DEAL ITEMS");
        for(int i= 0 ; i<cart_ptr ; i++){
            if(array_to_track_if_giveaway_deal_product[i]==1) {
                tc+=cart_price_array[i]*cart_quantity_array[i];
                System.out.println(Category.get_product_name(cart_product_id_array[i]));
                System.out.println("ID: " + cart_product_id_array[i]);
                System.out.println("COMBINED PRICE (Above 2 items combined): " + cart_price_array[i]);
                giveaway_price += cart_price_array[i] * cart_quantity_array[i];
            }
        }
        System.out.println("--------------------");

        if(total_price+giveaway_price>this.customer_balance){
            System.out.println("WARNING!!");
            System.out.println("CART VALUE: "+total_price);
            System.out.println("BALANCE: "+this.customer_balance);
            System.out.println("insufficient balance!");
            return ;
        }
        System.out.println("---------------------------");
        System.out.println("CURRENT STATUS: "+ this.customer_status);
//        double category_discount_amount;
//        if(this.customer_status.equals("elite")) {
//            category_discount_amount = 0.1 * total_price;
//        }
//        else if(this.customer_status.equals("prime")){
//            category_discount_amount = 0.05 * total_price;
//        }
//        else{
//            category_discount_amount=0;
//        }
//        System.out.println(this.customer_status +" discount= "+ category_discount_amount);
//        System.out.println("(only applicable on non-deal products)");
//        System.out.println("---------------------------");
//        total_price-=category_discount_amount;
        System.out.println("TOTAL PRICE: "+ total_price);
//        if(max_coupon >0){
//            System.out.println("Coupon discount of "+max_coupon+"% added");
//            System.out.println("discount = " + ((max_coupon/100)*total_price));
//            total_price-=max_coupon/100;
//            System.out.println("Price after coupon added: " +total_price);
//        }
        System.out.println("---------------------------");
        double delivery_charge = 0;
        System.out.println("BILL");
        System.out.println("final cost of non offer products: " + total_price);
        System.out.println("cost of giveaway products: " + giveaway_price);
        System.out.println("Price without discount: " + tc);
        if(this.customer_status.equals("elite")){
            delivery_charge = 100;
        }
        else if(this.customer_status.equals("prime")){
            delivery_charge = 100 + (0.02*(tc));
        }
        else if(this.customer_status.equals("normal")){
            delivery_charge = 100 + (0.05*(tc));
        }
        System.out.println("Delivery charge: "+ delivery_charge);
        System.out.println("---------------------------");
        System.out.println("FINAL PRICE: \n" +(total_price+giveaway_price) + "+" + delivery_charge);
        System.out.println(total_price+giveaway_price +delivery_charge);
        double total_bill = total_price +delivery_charge+giveaway_price;

        for(int i =0 ; i<cart_ptr ; i++) {
            if (Category.get_product_stock(cart_product_id_array[i]) < cart_quantity_array[i]) {
                System.out.println("product " + Category.get_product_name(cart_product_id_array[i]) + "has only " + Category.get_product_stock(cart_product_id_array[i]) + " units");
                cart_ptr = 0;
                System.out.println("Cart has been emptied!");
                return;
            }
        }
        this.instantaneous_total_price = total_bill;
//        int rounded_off_amount_integer = (int)Math.round(total_bill);
//        System.out.println("(Rounded off amount: ) Rs." + rounded_off_amount_integer);
//        this.instantaneous_total_price = rounded_off_amount_integer;
        coupon_array[index]=0;


    }

    void make_payment() {
        System.out.println("----------------");
        System.out.println("YOUR ORDER VALUE IS: "+ this.instantaneous_total_price);
        System.out.println("----------------");

        for(int i = 0 ; i<cart_ptr ; i++){
            int ret = Category.reduce_stock(cart_quantity_array[i] , cart_product_id_array[i]);
            if(ret == -1){
                System.out.println("Out of stock items added");
                System.out.println("Cart emptied");
                cart_ptr = 0 ;
                return ;
            }
        }
        if(this.customer_status.equals("elite")){
            System.out.println("Order placed. ");
            System.out.print("Since you are an elite customer your order will be delivered in ");
            int days = 1 + (int)(Math.random() * ((2 - 1) + 1));
            System.out.println(days +" days");
            int n_coupons = (3 + (int)(Math.random() * ((4 - 3) + 1))) ;
            if(this.instantaneous_total_price>=5000) {
                System.out.println("CONGRATULATIONS! You have received " + n_coupons + "coupons with your order!");
                System.out.println("Coupon details: ");
                for (int i = 0; i < n_coupons; i++) {
                    int value = (5 + (int) (Math.random() * ((15 - 5) + 1)));
                    System.out.println(value + "%");
                    coupon_array[coupon_array_ptr++] = value;
                }
            }
        }
        if(this.customer_status.equals("prime")){
            System.out.println("Order placed. ");
            System.out.print("Since you are an elite customer your order will be delivered in ");
            int days = 3 + (int)(Math.random() * ((6 - 3) + 1));
            System.out.println(days +" days");
            System.out.println("-----------------------------------");
            if(this.instantaneous_total_price>=5000) {
                int n_coupons = (1 + (int) (Math.random() * ((2 - 1) + 1)));
                System.out.println("CONGRATULATIONS! You have received " + n_coupons + "coupons with your order!");
                System.out.println("Coupon details: ");
                for (int i = 0; i < n_coupons; i++) {
                    int value = (5 + (int) (Math.random() * ((15 - 5) + 1)));
                    System.out.println(value + "%");
                    coupon_array[coupon_array_ptr++] = value;
                }
            }
        }
        if(this.customer_status.equals("normal")){
            System.out.println("Order placed. ");
            System.out.print("Since you are an elite customer your order will be delivered in ");
            int days = 7 + (int)(Math.random() * ((10 - 7) + 1));
            System.out.println(days +" days");
            System.out.println("-----------------------------------");
            int n_coupons = 0;
            System.out.println("Available Coupon details: ");
            if(n_coupons==0){
                System.out.println("UNAVAILABLE");
            }
            for(int i = 0 ; i<n_coupons ; i++){
                int value = (5 + (int)(Math.random() * ((15 - 5) + 1)));
                System.out.println(value+"%");
                coupon_array[coupon_array_ptr++]=value;
            }
        }
        cart_ptr=0;
        System.out.println("-----------------------------------");
        if(this.customer_status.equals("elite")){
            if((1 + (int)(Math.random() * ((100 - 5) + 1)%3)==0)){
                System.out.println("WOOHOOOO! You have earned a surprise product being an elite member!");
                System.out.println("DETAILS:");
                System.out.println("Playstation 4 - FIFA special editigon");
                System.out.println("PRICE: Rs.40000");
                System.out.println("Enjoy!");
            }
        }
        this.customer_balance-=this.instantaneous_total_price;
        this.instantaneous_total_price= 0 ;
    }
//    ---------------------------
//    GETTERS AND SETTERS
//    ---------------------------
    void setCustomer_name(String name){
        this.customer_name = name;
    }
    void setCustomer_password(String customer_password){
        this.customer_password = customer_password;
    }
    void setCustomer_balance(float customer_balance){
        this.customer_balance= customer_balance;
    }
    void setCustomer_status(String customer_status){
        this.customer_status = customer_status;
    }
    String getCustomer_name(){
        return this.customer_name;
    }
    String getCustomer_password(String username){
        return this.customer_password;
    }
    String getCustomer_password(){
        return this.customer_password;
    }
    String getCustomer_status(){
        return this.customer_status;
    }
    float getCustomer_balance(){
        return this.customer_balance;
    }
    static void append_to_customer_tracking_array(Customer1 latest){
        customer_tracking_array[customer_tracking_array_ptr++]= latest ;
    }

}






