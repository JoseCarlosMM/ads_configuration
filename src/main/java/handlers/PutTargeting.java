package handlers;

/**
 * Created by susi3_000 on 07/06/2016.
 */
import com.amazonaws.services.lambda.runtime.Context;
import dtos.Targeting;
import handlers.BaseHandler;
import handlers.BaseHandler.CustomException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class PutTargeting extends BaseHandler{
    public PutTargeting() {
    }
    public void putTargeting(JSONObject json, Context context) throws CustomException {
        LinkedHashMap jsonContent = (LinkedHashMap) json.get("content");
        ArrayList jsonZipCodes = (ArrayList) jsonContent.get("zip-codes");
        if(jsonZipCodes == null || jsonZipCodes.isEmpty()) {
            throw new CustomException("nullexception: Zip-codes null or empty.");
        } else {
            try {
                this.openConnection();
                this.connection.setAutoCommit(false);
                ResultSet e = this.executeQuery("SELECT ID_advertiser FROM adsconfiguration.Advertiser WHERE ID_advertiser = " + json.get("id_advertiser"));
                if(!e.next()) {
                    throw new CustomException("notfoundexception: Advertiser not found.");
                }

                e = this.executeQuery("SELECT ID_campaign FROM adsconfiguration.Campaign_Advertiser WHERE ID_campaign = " + json.get("id_campaign"));
                if(!e.next()) {
                    throw new CustomException("notfoundexception: Campaign not found.");
                }

                for(int i = 0; i < jsonZipCodes.size(); ++i) {
                    this.executeUpdate("INSERT INTO adsconfiguration.Targeting (ID_campaign,Zip_code) VALUES (" + json.get("id_campaign") + "," + jsonZipCodes.get(i) + ");");
                }

                this.connection.commit();
                this.closeConnection();
            } catch (SQLException var8) {
                System.err.println(var8.getMessage());

                try {
                    this.connection.rollback();
                } catch (SQLException var7) {
                    ;
                }
            }

        }
    }
}
