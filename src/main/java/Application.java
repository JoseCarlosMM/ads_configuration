
import dtos.Advertiser;
import dtos.PublisherCampaign;
import handlers.BaseHandler;
import handlers.PostPublisherCampaign;

/**
 * Created by josec on 5/28/2016.
 */
public class Application {
    public static void main(String[] args) throws BaseHandler.CustomException {
        PostPublisherCampaign handler = new PostPublisherCampaign();
        PublisherCampaign adv = new PublisherCampaign();
        adv.setId(1);
        PublisherCampaign.Content content = new PublisherCampaign.Content();
        content.setName("testcamp");
        content.setCategory(1);
        adv.setContent(content);
        handler.postPublisherCampaign(adv,null);
    }
}
