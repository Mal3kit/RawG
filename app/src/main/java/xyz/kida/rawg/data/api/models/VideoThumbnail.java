package xyz.kida.rawg.data.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoThumbnail {

    @SerializedName("medium")
    @Expose
    private ImageUrl imageUrl;

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }
}
