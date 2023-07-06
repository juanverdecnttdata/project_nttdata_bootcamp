package com.nttdata.entity;

import java.io.Serializable;

public class Constant implements Serializable {
    public static final String urlAccountHistory = "http://localhost:8088";
    public static final String urlClient = "http://localhost:8086";
    public static final String saveAccountHistory = "/accountHistory/save";
    public static final String listAccountHistoryByAccount = "/accountHistory/listAccountHistoryByAccount";
    public static final String listAccountHistoryByClientProduct = "/accountHistory/listAccountHistoryByClientProduct";
    public static final String saveClientProduct = "/clientProduct/save";

    public static final String getClientById = "/client/getClientById/";
    public static final String getAllClientProduct = "/clientProduct/all/";

    public static final String getAllClientProductLog = "/clientProduct/getAllClientProductLog/";

    public static final String urlClientProduct = "http://localhost:8087";
    public static final String getClientProductById = "/clientProduct/getClientProductById/";
    public static final String getProductById = "/product/getProductById/";
    public static final String urlPerson = "http://localhost:8083";
    public static final String getPersonById = "/person/getPersonById/";
    public static final String urlProduct = "http://localhost:8084";
    public static final String urlClient_webCLient = "http://localhost:8086/client";

    public static final Integer number_transaction_account = 20;

    public static final Integer id_type_operation_automatic_payment = 5;
    public static final Integer id_type_operation_transfer = 6;
}
