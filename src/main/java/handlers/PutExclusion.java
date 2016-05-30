package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Ad;
import dtos.Exclusion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by josec on 5/29/2016.
 */
public class PutExclusion extends BaseHandler {
    public void putExclusion(JSONObject json, Context context) throws BaseHandler.CustomException {
        ArrayList arrPublishers = (ArrayList) json.get("content");
        if (arrPublishers==null || arrPublishers.size()==0) {
            throw new BaseHandler.CustomException("nullexception: Publishers must not be null or empty.");
        }

        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs1 = executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser WHERE ID_advertiser = "+json.get("advertiserId"));
            if(!rs1.next()){
                throw new BaseHandler.CustomException("notfoundexception: Advertiser not found.");
            }

            for (int i=0;i<arrPublishers.size();i++ ){
                LinkedHashMap jsonObject = (LinkedHashMap) arrPublishers.get(i);
                ResultSet rs = executeQuery("SELECT ID_publisher FROM adsconfiguration.Publisher WHERE ID_publisher = "+jsonObject.get("publisher-id"));
                if(!rs.next()){
                    throw new CustomException("notfoundexception: One or more of the publishers not found.");
                }
                executeUpdate("INSERT INTO adsconfiguration.Exclusion (ID_advertiser, ID_publisher) VALUES ("
                        +json.get("advertiserId")+","+jsonObject.get("publisher-id")+");");
            }
            connection.commit();
            closeConnection();
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {}
        }
    }
}
