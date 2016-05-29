package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Publisher;
import dtos.PublisherCampaign;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by josec on 5/29/2016.
 */
public class GetPublisherCampaigns extends BaseHandler {
    public ArrayList getPublisherCampaigns(PublisherCampaign campaign, Context context) throws CustomException {
        ArrayList<PublisherCampaign.Content> campaigns= new ArrayList<PublisherCampaign.Content>();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT ID_publisher FROM adsconfiguration.Publisher WHERE ID_publisher = "+campaign.getId());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Publisher not found.");
            }

            ResultSet rs2 = executeQuery("SELECT * FROM adsconfiguration.Campaign_Publisher WHERE ID_publisher = "+campaign.getId()
                    +" ORDER BY ID_campaign;");
            while (rs2.next()) {
                PublisherCampaign.Content content = new PublisherCampaign.Content();
                content.setCategory(rs2.getInt("category"));
                content.setName(rs2.getString("name"));
                content.setId(rs2.getInt("ID_campaign"));
                campaigns.add(content);
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
        System.out.println(campaign.toString());
        return campaigns;
    }
}
