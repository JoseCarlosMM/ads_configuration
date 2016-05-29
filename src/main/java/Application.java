
import dtos.Advertiser;
import dtos.PublisherCampaign;
import dtos.PublisherCampaignComssion;
import handlers.*;

/**
 * Created by josec on 5/28/2016.
 */
public class Application {
    public static void main(String[] args) throws BaseHandler.CustomException {
       /* PostPublisherCampaign handler = new PostPublisherCampaign();
        PublisherCampaign adv = new PublisherCampaign();
        adv.setId(1);
        PublisherCampaign.Content content = new PublisherCampaign.Content();
        content.setName("testcamp");
        content.setCategory(1);
        adv.setContent(content);
        handler.postPublisherCampaign(adv,null);*/

        PutPublisherCampaignComission campaigns = new PutPublisherCampaignComission();
        PublisherCampaignComssion camp = new PublisherCampaignComssion();
        camp.setPublisherId(1);
        camp.setCampaignId(7);
        PublisherCampaignComssion.Content content = new PublisherCampaignComssion.Content();
        content.setComission(0.0);
        camp.setContent(content);
        campaigns.putPublisherCampaignComission(camp,null);
    }
}
