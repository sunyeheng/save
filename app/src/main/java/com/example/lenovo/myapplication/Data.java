package com.example.lenovo.myapplication;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingin on 2018/5/25.
 */

public class Data extends Application {

    public static List<Business> businessList_data = new ArrayList<>();
    public static List<Goods> goodsList_data = new ArrayList<>();
    public static String url="http://10.7.84.240:8080/sx/";
    public static String urlImage="http://10.7.84.240:8080/sx/user/";
    public static User user =new User();
    public static Business business = new Business();
    public static String BusinessUrlImage="http://10.7.84.240:8080/sx/business/";
    public static Goods goods = new Goods();
    public static String sousuoGoods = "http://10.7.84.240:8080/sx/SouSuoGoods?name=";

    public static String urlImageBusiness="http://10.7.84.240:8080/sx/business/";
    public static List<Goods> goodsList=new ArrayList<>();
    public static List<Map<String,Goods>> goodsListm=new ArrayList<>();
    public static List<String> typeList=new ArrayList<>();



    }




