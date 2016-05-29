package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Advertiser;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by josec on 5/29/2016.
 */
public class GetAdvertisers extends BaseHandler {
    public ArrayList getAdvertisers(Context context) throws CustomException {
        JSONObject json = new JSONObject();
        ArrayList listAdvertisers = new ArrayList();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT * FROM adsconfiguration.Advertiser ORDER BY ID_advertiser ASC;");
            while (rs.next()) {
                Advertiser advertiser = new Advertiser();
                advertiser.setId(rs.getInt("ID_advertiser"));
                advertiser.setName(rs.getString("name"));
                listAdvertisers.add(advertiser);
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

        if (listAdvertisers.size()==0) {
            throw new CustomException("notfoundexception: No advertisers found.");
        }

        System.out.println(listAdvertisers.toString());
        return listAdvertisers;
    }
}
