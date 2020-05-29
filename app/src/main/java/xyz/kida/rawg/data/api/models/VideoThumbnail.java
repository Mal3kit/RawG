package xyz.kida.rawg.data.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoThumbnail {

    @SerializedName("medium")
    @Expose
    private ImageUrl url;

    public ImageUrl getUrl() {
        return url;
    }

    public void setUrl(ImageUrl url) {
        this.url = url;
    }
}
