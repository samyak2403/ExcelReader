package com.arrowwould.excelreader.model;

public class Language {
    private String keyLanguage;
    private String nameLanguage;
    private int imgResource;

    public Language(String keyLanguage, String nameLanguage, int imgResource) {
        this.keyLanguage = keyLanguage;
        this.nameLanguage = nameLanguage;
        this.imgResource = imgResource;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getKeyLanguage() {
        return keyLanguage;
    }

    public void setKeyLanguage(String keyLanguage) {
        this.keyLanguage = keyLanguage;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public void setNameLanguage(String nameLanguage) {
        this.nameLanguage = nameLanguage;
    }
}
