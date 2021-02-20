package com.idt.stackexchange;

import com.fasterxml.jackson.databind.JsonNode;
import com.idt.stackexchange.pojo.ItemPOJO;
import com.idt.stackexchange.pojo.OwnerPOJO;
import com.idt.stackexchange.pojo.ResponsePOJO;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Tests {
    private static final String API_URL = "https://api.stackexchange.com/2.2/";
    private static final String API_KEY = "ig8aZPv5JnWU8Tfakj8zVQ((";
    private static final String REQ_METHOD = "answers"; //answers comments posts questions
    private static final String REQ_SITE = "stackoverflow";
    private static final int    REQ_PAGE = 1;
    private static final int    REQ_PAGE_SIZE = 10;
    private static final String REQ_SORT = "creation";
    private static final String REQ_SORT_ORDER = "desc";
    private static final String REQ_FILTER = "default";

    private String url = API_URL + REQ_METHOD + "?"
        + "site=" + REQ_SITE
        + "&page=" + REQ_PAGE
        + "&pagesize=" + REQ_PAGE_SIZE
        + "&order=" + REQ_SORT_ORDER
        + "&sort=" + REQ_SORT
        + "&filter=" + REQ_FILTER
        + "&key=" + API_KEY;

    //test for status code
    @Test
    void getResponse() throws IOException {
        assertEquals(200, Api.getResponse(Api.sendRequest(url)).code());
    }

    //test the output and that the array contains no more than 10 records
    @Test
    void parseTest1() throws IOException {
        JsonNode node = Json.parse(Api.getResponse(Api.sendRequest(url)).body().string());
        System.out.println(Json.toString(node));

        ResponsePOJO responsePOJO = Json.fromJson(node, ResponsePOJO.class);

        boolean lessThanTenRecords;

        lessThanTenRecords = responsePOJO.getItems().size() <= 10;

        assertTrue(lessThanTenRecords);
    }

    //test that each element of the array contains an Owner object
    @Test
    void parseTest2() throws IOException {
        JsonNode node = Json.parse(Api.getResponse(Api.sendRequest(url)).body().string());
        ResponsePOJO responsePOJO = Json.fromJson(node, ResponsePOJO.class);

        for (ItemPOJO ip : responsePOJO.getItems()) {
            assertInstanceOf(OwnerPOJO.class, ip.getOwner());

            System.out.println("User id: " + ip.getOwner().getDisplay_name());
            System.out.println("User name: " + ip.getOwner().getUser_id());
            System.out.println("User link: " + ip.getOwner().getLink());
            System.out.println("============================================================");
        }
    }

    //test that for each object the Owner link is generated using display_name and user_id fields
    @Test
    void parseTest3() throws IOException {
        JsonNode node = Json.parse(Api.getResponse(Api.sendRequest(url)).body().string());
        ResponsePOJO responsePOJO = Json.fromJson(node, ResponsePOJO.class);

        for (ItemPOJO ip : responsePOJO.getItems())
        {
            String[] splittedLink = ip.getOwner().getLink().split("/");
            assertEquals(ip.getOwner().getIdToString(), splittedLink[splittedLink.length - 2]);

            String[] splittedNameFromLink = splittedLink[splittedLink.length - 1].split("-");

            //not ideal regex, can work it out if needed :)
            String[] splittedUserName = ip.getOwner().getDisplay_name().toLowerCase().split("[, ?_.@-]+");

            for (String a : splittedUserName)
                System.out.println(a);
            for (String a : splittedNameFromLink)
                System.out.println(a);

            assertEquals(splittedUserName.length, splittedNameFromLink.length);

            for (int i = 0; i< splittedNameFromLink.length; i++)
                assertEquals(splittedNameFromLink[0], splittedUserName[0]);
        }
    }
}