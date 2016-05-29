
import dtos.Advertiser;
import handlers.BaseHandler;
import handlers.GetAdvertisers;
import handlers.PostAdvertiser;

/**
 * Created by josec on 5/28/2016.
 */
public class Application {
    public static void main(String[] args) throws BaseHandler.CustomException {
        GetAdvertisers handler = new GetAdvertisers();
        Advertiser adv = new Advertiser();
        handler.getAdvertisers(null);
    }
}
