package testing.com.albumuploadtest.dto;

public class PictureDto {

    private Integer id;
    private String uriImage;
    private String uriThumbnail;
    private Boolean selected;
    private Integer width;
    private Integer height;
    private Boolean sent;
    private String folderName;

    public PictureDto(Integer id, String uriImage, String uriThumbnail) {
        selected = false;
        sent = false;

        this.id = id;
        this.uriImage = uriImage;
        this.uriThumbnail = uriThumbnail;
    }

    public PictureDto(Integer id, String uriImage, String uriThumbnail, Integer width, Integer height) {
        selected = false;
        sent = false;

        this.id = id;
        this.uriImage = uriImage;
        this.uriThumbnail = uriThumbnail;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        boolean res = false;

        if (o instanceof  PictureDto) {
            PictureDto p = (PictureDto)o;
            res = id.equals(p.getId());
        }

        return res;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     * @return El nombre del fichero, sin la ruta y sin el prefijo con la fecha
     */
    public String getStringBareFilename() {
        return getUriImage().substring(getUriImage().lastIndexOf("/") + 1);
    }

    public String getStringDate() {
        return getStringBareFilename().substring(0, 10);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean isSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

}
