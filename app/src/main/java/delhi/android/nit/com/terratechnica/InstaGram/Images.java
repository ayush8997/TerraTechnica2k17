package delhi.android.nit.com.terratechnica.InstaGram;

/**
 * Created by Manojit Paul on 1/20/2017.
 */
public class Images {

    private String thumnailLink;
    private String fullImageLink;

    public Images(String thumnailLink, String fullImageLink) {
        this.thumnailLink = thumnailLink;
        this.fullImageLink = fullImageLink;
    }

    public String getFullImageLink() {
        return fullImageLink;
    }

    public void setFullImageLink(String fullImageLink) {
        this.fullImageLink = fullImageLink;
    }

    public String getThumnailLink() {
        return thumnailLink;
    }

    public void setThumnailLink(String thumnailLink) {
        this.thumnailLink = thumnailLink;
    }
}
