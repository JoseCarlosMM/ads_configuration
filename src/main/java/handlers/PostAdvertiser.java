package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Advertiser;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by josec on 5/29/2016.
 */
public class PostAdvertiser extends BaseHandler{
    public String postAdvertiser(Advertiser advertiser, Context context) throws CustomException {
        if (isStringNullOrEmpty(advertiser.getName())) {
            throw new CustomException("nullexception: Name must not be null or empty.");
        }
        JSONObject json = new JSONObject();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            executeUpdate("INSERT INTO adsconfiguration.Advertiser (name) VALUES ('"
                    +advertiser.getName()+"');");
            ResultSet rs = executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser ORDER BY ID_advertiser DESC LIMIT 1;");
            while (rs.next()) {
                json.put("id",rs.getString("ID_advertiser"));
            }
            connection.commit();
            closeConnection();
        }
        catch (Exception e)
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
