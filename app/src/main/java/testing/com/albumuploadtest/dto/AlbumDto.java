package testing.com.albumuploadtest.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumDto {

    private int totalNumOfPics;
    private List<PictureDto> pictures;

    public AlbumDto(int totalNumOfPics) {
        this.totalNumOfPics = totalNumOfPics;
        this.pictures = new ArrayList<>();
    }

    public int getTotalNumOfPics() {
        return totalNumOfPics;
    }

    public void setTotalNumOfPics(int totalNumOfPics) {
        this.totalNumOfPics = totalNumOfPics;
    }

    public List<PictureDto> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureDto> pictures) {
        this.pictures = pictures;
    }

    public void addNewPicture(PictureDto picture) {
        this.pictures.add(picture);
    }

    public void removePicture(PictureDto picture) {
        this.pictures.remove(picture);
    }

    public void swapPictures(PictureDto pic1, PictureDto pic2) {
        int pic1Index = pictures.indexOf(pic1);
        int pic2Index = pictures.indexOf(pic2);
        Collections.swap(pictures, pic1Index, pic2Index);
    }

}
