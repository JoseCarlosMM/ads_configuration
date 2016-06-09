package dtos;

/**
 * Created by hugo_ on 29/05/2016.
 */
public class AdvertiserCampaignForGet {
    int id_advertiser;
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

    public static class Content{
        private int id;
        private String name;
        private int category;
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

        public void setStatus(String status) {this.status = status;}

        public String getStatus() {return status;}
    }
}
