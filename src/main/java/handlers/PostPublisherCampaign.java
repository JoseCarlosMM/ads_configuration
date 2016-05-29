package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Publisher;
import dtos.PublisherCampaign;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by josec on 5/29/2016.
 */
public class PostPublisherCampaign extends BaseHandler{
    public String postPublisherCampaign(PublisherCampaign campaign, Context context) throws CustomException {
        if (isStringNullOrEmpty(campaign.getContent().getName())) {
            throw new CustomException("nullexception: Name must not be null or empty.");
        }
        JSONObject json = new JSONObject();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT ID_publisher FROM adsconfiguration.Publisher WHERE ID_publisher = "+campaign.getId());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Publisher not found.");
            }
            executeUpdate("INSERT INTO adsconfiguration.Campaign_Publisher (Id_publisher,name,category,bid,comission) VALUES ("+campaign.getId()+",'"
                    +campaign.getContent().getName()+"','"+campaign.getContent().getCategory()+"',0,0.50);");
            ResultSet rs2 = executeQuery("SELECT ID_campaign FROM adsconfiguration.Campaign_Publisher WHERE name = '"
                    +campaign.getContent().getName()+"' ORDER BY ID_campaign DESC LIMIT 1;");
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
