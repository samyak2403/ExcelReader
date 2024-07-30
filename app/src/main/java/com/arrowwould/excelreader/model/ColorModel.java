package com.arrowwould.excelreader.model;

public class ColorModel {
    int idSourceBg;
    String codeColor;

    boolean isWhite;

    public ColorModel(int idSourceBg, String codeColor, boolean isWhite) {
        this.idSourceBg = idSourceBg;
        this.codeColor = codeColor;
        this.isWhite = isWhite;
    }

    public int getIdSourceBg() {
        return idSourceBg;
    }

    public void setIdSourceBg(int idSourceBg) {
        this.idSourceBg = idSourceBg;
    }

    public String getCodeColor() {
        return codeColor;
    }

    public void setCodeColor(String codeColor) {
        this.codeColor = codeColor;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }
}
