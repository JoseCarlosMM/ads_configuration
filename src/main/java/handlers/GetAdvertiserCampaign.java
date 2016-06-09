package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Advertiser;
import dtos.AdvertiserCampaign;
import dtos.AdvertiserCampaignForGet;
import org.json.simple.JSONObject;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hugo_ on 29/05/2016.
 */
public class GetAdvertiserCampaign extends BaseHandler {
    public ArrayList getAdvertiserCampaign(AdvertiserCampaignForGet advertiserCampaign, Context context) throws CustomException {
        JSONObject json = new JSONObject();
        ArrayList listCampaign = new ArrayList();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser WHERE ID_Advertiser = "+advertiserCampaign.getId_advertiser());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Advertiser not found.");
            }
            ResultSet rs2 = executeQuery("SELECT ID_campaign, Name, Category, Status FROM adsconfiguration.Campaign_Advertiser WHERE ID_advertiser = '"
                    +advertiserCampaign.getId_advertiser()+"' ORDER BY ID_campaign ASC;");
            while (rs2.next()) {
                AdvertiserCampaignForGet campaign = new AdvertiserCampaignForGet();
                campaign.setContent(new AdvertiserCampaignForGet.Content());
                campaign.getContent().setName(rs2.getString("Name"));
                campaign.getContent().setId(rs2.getInt("ID_campaign"));
                campaign.getContent().setCategory(rs2.getInt("Category"));
                campaign.getContent().setStatus(rs2.getString("Status"));
                listCampaign.add(campaign.getContent());
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

        if (listCampaign.size()==0) {
            throw new CustomException("notfoundexception: No campaigns found.");
        }

        System.out.println(listCampaign.toString());
        return listCampaign;
    }
}
