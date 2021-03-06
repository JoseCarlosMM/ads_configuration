package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.AdvertiserCampaign;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hugo_ on 29/05/2016.
 */
public class PutAdvertiserCampaign extends BaseHandler {
    public void putAdvertiserCampaign(AdvertiserCampaign advertiserCampaign, Context context) throws CustomException {
        if (isStringNullOrEmpty(advertiserCampaign.getContent().getStatus())) {
            throw new CustomException("nullexception: Status must not be null or empty.");
        }
        JSONObject json = new JSONObject();

        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT ID_campaign FROM adsconfiguration.Campaign_Advertiser WHERE ID_campaign = "+advertiserCampaign.getId_campaign());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Campaign not found.");
            }
            executeUpdate("UPDATE adsconfiguration.Campaign_Advertiser SET status = '"+advertiserCampaign.getContent().getStatus()+"' where ID_campaign = "+advertiserCampaign.getId_campaign());
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
