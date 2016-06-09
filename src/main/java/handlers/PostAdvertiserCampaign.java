package handlers;

import dtos.AdvertiserCampaign;
import com.amazonaws.services.lambda.runtime.Context;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hugo_ on 29/05/2016.
 */
public class PostAdvertiserCampaign extends BaseHandler{

    public String postAdvertiserCampaign(AdvertiserCampaign advertiserCampaign, Context context) throws CustomException {
        if (isStringNullOrEmpty(advertiserCampaign.getContent().getName())) {
            throw new CustomException("nullexception: Name must not be null or empty.");
        }
        JSONObject json = new JSONObject();

        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser WHERE ID_advertiser = "+advertiserCampaign.getId_advertiser());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Advertiser not found.");
            }
            executeUpdate("INSERT INTO adsconfiguration.Campaign_Advertiser (Id_advertiser,name,category,bid,budget,status) VALUES ("+advertiserCampaign.getId_advertiser()+",'"
                    +advertiserCampaign.getContent().getName()+"','"+advertiserCampaign.getContent().getCategory()+"',0, null,'paused');");
            ResultSet rs2 = executeQuery("SELECT ID_campaign FROM adsconfiguration.Campaign_Advertiser WHERE name = '"
                    +advertiserCampaign.getContent().getName()+"' ORDER BY ID_campaign DESC LIMIT 1;");
            while (rs2.next()) {
                json.put("id",rs2.getString("ID_campaign"));
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
        System.out.println(json.toJSONString());
        return json.toJSONString();
    }
}
