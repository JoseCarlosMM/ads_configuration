package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Ad;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by josec on 5/29/2016.
 */
public class GetAds extends BaseHandler{
    public ArrayList getAds(Ad ad, Context context) throws BaseHandler.CustomException {

        ArrayList<Ad.Content> ads = new ArrayList<Ad.Content>();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser WHERE ID_advertiser = "+ad.getAdvertiserId());
            if(!rs.next()){
                throw new BaseHandler.CustomException("notfoundexception: Advertiser not found.");
            }
            boolean hasAds = false;
            ResultSet rs2 = executeQuery("SELECT * FROM adsconfiguration.Ads WHERE ID_advertiser = " + ad.getAdvertiserId()
                    + " ORDER BY ID_advertiser ASC;");
            while (rs2.next()) {
                hasAds=true;
                Ad.Content content = new Ad.Content();
                content.setHeadline(rs2.getString("headline"));
                content.setId(rs2.getInt("ID_ad"));
                content.setUrl(rs2.getString("url"));
                content.setDescription(rs2.getString("description"));
                ads.add(content);
            }
            if(!hasAds){
                throw new BaseHandler.CustomException("notfoundexception: No ads found.");
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
        System.out.println(ads.toString());
        return ads;
    }
}
