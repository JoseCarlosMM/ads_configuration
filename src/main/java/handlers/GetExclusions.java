package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Advertiser;
import dtos.Exclusion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by josec on 5/29/2016.
 */
public class GetExclusions extends BaseHandler{
    public JSONArray getExclusions(Exclusion exclusion, Context context) throws BaseHandler.CustomException {
        JSONArray jsonArray = new JSONArray();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs1 = executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser WHERE ID_advertiser = "+exclusion.getAdvertiserId());
            if(!rs1.next()){
                throw new BaseHandler.CustomException("notfoundexception: Advertiser not found.");
            }

            ResultSet rs = executeQuery("SELECT ID_publisher FROM adsconfiguration.Exclusion WHERE ID_advertiser ORDER BY ID_advertiser ASC;");
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("publisher-id",rs.getInt("ID_publisher"));
                jsonArray.add(jsonObject);
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

        return jsonArray;
    }
}
