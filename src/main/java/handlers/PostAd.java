package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Ad;
import dtos.Advertiser;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by josec on 5/29/2016.
 */
public class PostAd extends BaseHandler{
    public JSONObject postAd(Ad ad, Context context) throws BaseHandler.CustomException {
        if (isStringNullOrEmpty(ad.getContent().getHeadline())) {
            throw new BaseHandler.CustomException("nullexception: Headline must not be null or empty.");
        }
        if (isStringNullOrEmpty(ad.getContent().getUrl())) {
            throw new BaseHandler.CustomException("nullexception: Url must not be null or empty.");
        }
        if (isStringNullOrEmpty(ad.getContent().getDescription())) {
            throw new BaseHandler.CustomException("nullexception: Description must not be null or empty.");
        }
        JSONObject json = new JSONObject();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser WHERE ID_advertiser = "+ad.getAdvertiserId());
            if(!rs.next()){
                throw new CustomException("notfoundexception: Advertiser not found.");
            }

            executeUpdate("INSERT INTO adsconfiguration.Ads (headline, ID_advertiser, description, url) VALUES ('"
                    +ad.getContent().getHeadline()+"',"+ad.getAdvertiserId()+",'"+ad.getContent().getDescription()+"','"+ad.getContent().getUrl()+"');");
            ResultSet rs2 = executeQuery("SELECT ID_ad FROM adsconfiguration.Ads WHERE headline = '"+ad.getContent().getHeadline()+"'ORDER BY ID_advertiser DESC LIMIT 1;");
            while (rs2.next()) {
                json.put("id",rs2.getString("ID_ad"));
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
