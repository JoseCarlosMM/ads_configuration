package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.PublisherCampaignComssion;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by josec on 5/29/2016.
 */
public class PutPublisherCampaignComission extends BaseHandler {
    public void putPublisherCampaignComission(PublisherCampaignComssion campaign, Context context) throws CustomException {
        JSONObject jsonObject = new JSONObject();
        if(campaign.getContent().getComission()==0){
            throw new CustomException("nullexception: Comission must not be 0.");
        }

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

            executeUpdate("UPDATE adsconfiguration.Campaign_Publisher SET comission = "+campaign.getContent().getComission()+" WHERE ID_publisher = "+campaign.getPublisherId()
                    +" AND ID_campaign ="+campaign.getCampaignId()+";");

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
