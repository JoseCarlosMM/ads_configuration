package com.pruebas;

import com.amazonaws.services.lambda.runtime.Context;
import handlers.BaseHandler;
import org.json.simple.JSONObject;

import java.sql.ResultSet;


public class Hello extends BaseHandler {
    public String myHandler(Context context) {
        JSONObject json = new JSONObject();
        try
        {

            openConnection();
            ResultSet rs = executeQuery("SELECT * FROM adsconfiguration.Persons;");

            while (rs.next()) {
                json.put("name",rs.getString("last_name"));
            }

            closeConnection();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        System.out.println(json.toJSONString());
        return json.toJSONString();

    }
}