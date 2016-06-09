package handlers;

/**
 * Created by hugo_ on 29/05/2016.
 */

import com.amazonaws.services.lambda.runtime.Context;
import dtos.AdvertiserCampaign;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PutAdvertiserCampaignBid extends BaseHandler {

    public void putAdvertiserCampaignBid(AdvertiserCampaign advertiserCampaign, Context context) throws CustomException {

        if ((isStringNullOrEmpty(Double.toString(advertiserCampaign.getContent().getBid()))) || (advertiserCampaign.getContent().getBid()<0)) {
            throw new CustomException("nullexception: Bin must not be null or valid.");
        }
        JSONObject json = new JSONObject();

        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT Bid FROM adsconfiguration.Campaign_Advertiser WHERE ID_campaign = "+advertiserCampaign.getId_campaign());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Campaign not found.");
            }
            executeUpdate("UPDATE adsconfiguration.Campaign_Advertiser SET Bid = "+advertiserCampaign.getContent().getBid()+" where ID_campaign = "+advertiserCampaign.getId_campaign());
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
