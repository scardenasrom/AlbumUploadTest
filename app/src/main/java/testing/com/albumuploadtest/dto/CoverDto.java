package testing.com.albumuploadtest.dto;

public class CoverDto {

    private PictureDto picture;
    private String title;

    public CoverDto() {
    }

    public CoverDto(PictureDto picture) {
        this.picture = picture;
    }

    public CoverDto(PictureDto picture, String title) {
        this.picture = picture;
        this.title = title;
    }

    public PictureDto getPicture() {
        return picture;
    }

    public void setPicture(PictureDto picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
