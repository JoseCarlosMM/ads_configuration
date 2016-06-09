package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Advertiser;
import dtos.Publisher;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by josec on 5/29/2016.
 */
public class PostPublisher extends BaseHandler{
    public JSONObject postPublisher(Publisher publisher, Context context) throws CustomException {
        if (isStringNullOrEmpty(publisher.getName())) {
            throw new CustomException("nullexception: Name must not be null or empty.");
        }
        JSONObject json = new JSONObject();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            executeUpdate("INSERT INTO adsconfiguration.Publisher (name) VALUES ('"
                    +publisher.getName()+"');");
            ResultSet rs = executeQuery("SELECT ID_publisher FROM adsconfiguration.Publisher WHERE name = '"+publisher.getName()
                    +"' ORDER BY ID_publisher DESC LIMIT 1;");
            while (rs.next()) {
                json.put("id",rs.getString("ID_publisher"));
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
