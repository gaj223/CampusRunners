package com.example.user.campusrunners;

/**
 * Created by Yadi on 3/22/18.
 */

public class Constants {
    private Constants() {
        // restrict instantiation
    }
    //main api paths
    public static final String LOCAL_PATH ="http://172.24.122.158:8000";
    public static final String SERVER_PATH = "http://18.188.229.178/CampusRunnerBack/updated_apis";

    //separate api calls
    public static final String get_business_api="/business/get_business.php";
    public static final String create_order_api="/order/order_create.php";
    public static final String get_detail_order_api="/order/get_individual_order.php";
    public static final String get_open_orders_api="/order/get_all_open_orders_runner.php";
    public static final String update_orders_api="/order/update_order_status.php";
    public static final String get_all_open_orders_api="/order/get_all_open_orders.php";
    public static final String get_items_api ="/item/get_items.php";
    public static final String login_api ="/user/userlogin.php";

    //Order status
    public static final String CLOSED = "CLOSED";
    public static final String OPEN = "OPEN";
    public static final String TRANS = "TRANS";
}