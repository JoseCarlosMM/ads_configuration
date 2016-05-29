package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import dtos.Advertiser;
import dtos.Publisher;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by josec on 5/29/2016.
 */
public class GetPublishers extends BaseHandler {
    public ArrayList getPublishers(Context context) throws CustomException {
        JSONObject json = new JSONObject();
        ArrayList listPublishers = new ArrayList();
        try
        {
            openConnection();
            connection.setAutoCommit(false);
            ResultSet rs = executeQuery("SELECT * FROM adsconfiguration.Publisher ORDER BY ID_publisher ASC;");
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setId(rs.getInt("ID_publisher"));
                publisher.setName(rs.getString("name"));
                listPublishers.add(publisher);
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

        if (listPublishers.size()==0) {
            throw new CustomException("notfoundexception: No publishers found.");
        }

        System.out.println(listPublishers.toString());
        return listPublishers;
    }
}
