package testing.com.albumuploadtest.dto;

public class FolderDto {

    private Integer id;
    private String name;
    private String path;
    private Integer numPictures;
    private Integer numOfSelectedPics;
    private String uriImage;
    private String uriThumbnail;

    public FolderDto(Integer id, String name, String path, Integer numPictures, String uriImage, String uriThumbnail) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.numPictures = numPictures;
        this.uriImage = uriImage;
        this.uriThumbnail = uriThumbnail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getNumPictures() {
        return numPictures;
    }

    public void setNumPictures(Integer numPictures) {
        this.numPictures = numPictures;
    }

    public Integer getNumOfSelectedPics() {
        return numOfSelectedPics;
    }

    public void setNumOfSelectedPics(Integer numOfSelectedPics) {
        this.numOfSelectedPics = numOfSelectedPics;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public String getUriThumbnail() {
        return uriThumbnail;
    }

    public void setUriThumbnail(String uriThumbnail) {
        this.uriThumbnail = uriThumbnail;
    }

    public String getUriPreview() {
        if (uriThumbnail != null && uriThumbnail.length() > 0) {
            return "file://" + uriThumbnail;
        } else {
            return "file://" + uriImage;
        }
    }

}
