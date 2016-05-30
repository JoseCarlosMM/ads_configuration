package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Ad;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by josec on 5/29/2016.
 */
public class PutAd extends BaseHandler {
    public void putAd(Ad ad, Context context) throws BaseHandler.CustomException {
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

            ResultSet rs2 = executeQuery("SELECT ID_ad FROM adsconfiguration.Ads WHERE ID_ad = "+ad.getAdId());
            if(!rs2.next()){
                throw new CustomException("notfoundexception: Ad not found.");
            }

            executeUpdate("UPDATE adsconfiguration.Ads SET headline = '"+ad.getContent().getHeadline()+"', url = '"+ad.getContent().getUrl()+"', description = '"+ad.getContent().getDescription()
                    +"' WHERE ID_ad = "+ ad.getAdId()+";");
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
    }
}
