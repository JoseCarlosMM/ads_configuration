package dtos;

/**
 * Created by susi3_000 on 07/06/2016.
 */
public class Targeting {
    int id_advertiser;
    int id_campaign;
    Targeting.Content content;

    public Targeting() {
    }

    public Targeting.Content getContent() {
        return this.content;
    }

    public void setContent(Targeting.Content content) {
        this.content = content;
    }

    public int getId_campaign() {
        return this.id_campaign;
    }

    public void setId_campaign(int id_campaign) {
        this.id_campaign = id_campaign;
    }

    public int getId_advertiser() {
        return this.id_advertiser;
    }

    public void setId_advertiser(int id_advertiser) {
        this.id_advertiser = id_advertiser;
    }

    public static class Content {
        int[] zip_codes;

        public Content() {
        }

        public int[] getZip_codes() {
            return this.zip_codes;
        }

        public void setZip_codes(int[] zip_code) {
            this.zip_codes = zip_code;
        }
    }
}
