package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.AdCampaign;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by josec on 5/29/2016.
 */
public class PutAdsCampaign extends BaseHandler{
    public void putAdsCampaign(AdCampaign campaign, Context context) throws BaseHandler.CustomException {

        if(campaign.getContent().getAds()==null || campaign.getContent().getAds().size()==0){
            throw new BaseHandler.CustomException("nullexception: Ads must not be empty or null.");
        }

        try
        {
            openConnection();
            connection.setAutoCommit(false);

            ResultSet rs = executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser WHERE ID_advertiser = "+campaign.getAdvertiserId());
            if(!rs.next()){
                throw new BaseHandler.CustomException("notfoundexception: Advertiser not found.");
            }

            ResultSet rs4 = executeQuery("SELECT ID_campaign FROM adsconfiguration.Campaign_Advertiser WHERE ID_campaign = "+campaign.getCampaignId());
            if(!rs4.next()){
                throw new BaseHandler.CustomException("notfoundexception: Campaign found.");
            }

            for (int i=0;i<campaign.getContent().getAds().size();i++ ){
                ResultSet rs1 = executeQuery("SELECT ID_ad FROM adsconfiguration.Ads WHERE ID_ad = "+campaign.getContent().getAds().get(i));
                if(!rs1.next()){
                    throw new CustomException("notfoundexception: One or more ads not found.");
                }
                executeUpdate("INSERT INTO adsconfiguration.Campaign_Ads (ID_campaign, ID_ad) VALUES ("
                        +campaign.getCampaignId()+","+campaign.getContent().getAds().get(i)+");");
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
