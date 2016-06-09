package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.PublisherCampaign;
import dtos.PublisherCampaignComssion;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by josec on 5/29/2016.
 */
public class GetPublisherCampaignComission extends BaseHandler {
    public JSONObject getPublisherCampaignComission(PublisherCampaignComssion campaign, Context context) throws CustomException {
        JSONObject jsonObject = new JSONObject();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT ID_publisher FROM adsconfiguration.Publisher WHERE ID_publisher = "+campaign.getPublisherId());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Publisher not found.");
            }

            ResultSet rs2 = executeQuery("SELECT ID_campaign FROM adsconfiguration.Campaign_Publisher WHERE ID_campaign = "+campaign.getCampaignId());
            if(!rs2.next()){
                throw new CustomException("notfoundexception: Campaign not found.");
            }

            ResultSet rs3 = executeQuery("SELECT comission FROM adsconfiguration.Campaign_Publisher WHERE ID_publisher = "+campaign.getPublisherId()
                    +" AND ID_campaign ="+campaign.getCampaignId()+";");

            while (rs3.next()) {
                jsonObject.put("comission",rs3.getString("comission"));
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
        System.out.println(jsonObject.toJSONString());
        return jsonObject;
    }
}
