package dtos;

/**
 * Created by josec on 5/29/2016.
 */
public class PublisherCampaignComssion {
    private int publisherId;
    private int campaignId;
    private PublisherCampaignComssion.Content content;

    public PublisherCampaignComssion() {
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public static class Content{
        private Double comission;

        public Content() {
        }

        public Double getComission() {
            return comission;
        }

        public void setComission(Double comission) {
            this.comission = comission;
        }
    }
}
