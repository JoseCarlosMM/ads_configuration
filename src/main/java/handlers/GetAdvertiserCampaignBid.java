package handlers;


import com.amazonaws.services.lambda.runtime.Context;
import dtos.AdvertiserCampaign;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hugo_ on 29/05/2016.
 */
public class GetAdvertiserCampaignBid extends BaseHandler {

    public JSONObject getAdvertiserCampaignBid(AdvertiserCampaign advertiserCampaign, Context context) throws CustomException {

        JSONObject json = new JSONObject();

        try
        {
            openConnection();
            connection.setAutoCommit(false);

            ResultSet rs = executeQuery("SELECT ID_campaign FROM adsconfiguration.Campaign_Advertiser WHERE ID_campaign = "+advertiserCampaign.getId_campaign());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Campaign not found.");
            }

            ResultSet rs2 = executeQuery("SELECT Bid FROM adsconfiguration.Campaign_Advertiser WHERE ID_campaign = "+advertiserCampaign.getId_campaign());
            while (rs2.next()) {
                json.put("Bid",rs2.getDouble("Bid"));
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
        return json;
    }
}
