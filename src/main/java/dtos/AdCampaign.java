package dtos;

import java.util.ArrayList;

/**
 * Created by josec on 5/29/2016.
 */
public class AdCampaign {
    private int campaignId;
    private int advertiserId;
    private AdCampaign.Content content;
    public AdCampaign(){

    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public int getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(int advertiserId) {
        this.advertiserId = advertiserId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public static class Content{
        private ArrayList ads;

        public Content() {
        }

        public ArrayList getAds() {
            return ads;
        }

        public void setAds(ArrayList ads) {
            this.ads = ads;
        }
    }

}
