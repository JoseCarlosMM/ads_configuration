package dtos;

/**
 * Created by hugo_ on 29/05/2016.
 */
public class AdvertiserCampaign {
    int id_advertiser;
        int id_campaign;
    Content content;

    public int getId_advertiser() {
        return id_advertiser;
    }

    public void setId_advertiser(int id_advertiser) {
        this.id_advertiser = id_advertiser;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public int getId_campaign() {
        return id_campaign;
    }

    public void setId_campaign(int id_campaign) {
        this.id_campaign = id_campaign;
    }

    public static class Content{
        private int id;
        private String name;
        private int category;
        private double bid;
        private double budget;
        private String status;

        public Content() {
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setBid(double bid) { this.bid = bid;}

        public double getBid() {return  this.bid;}

        public void setBudget(int budget) {this.budget = budget;}

        public double getBudget() {return this.budget;}

        public void setStatus(String status) {this.status = status;}

        public String getStatus() {return this.status;}
    }
}
