package com.arrowwould.excelreader.model;

import java.io.Serializable;
import java.util.Comparator;

public class OfficeModel implements Comparable<OfficeModel>, Serializable {
    private String absolutePath;
    private String createAt;
    private boolean isDirectory;
    private boolean isStarred;
    private Long lastModified;
    private Long length;
    private String name;
    private String fileUri;

    private String urlThumbnail;

    private boolean isChecked;

    public OfficeModel() {
    }

    public OfficeModel(String absolutePath, String createAt, boolean isDirectory, boolean isStarred, Long lastModified, Long length, String name, String fileUri, String urlThumbnail) {
        this.absolutePath = absolutePath;
        this.createAt = createAt;
        this.isDirectory = isDirectory;
        this.isStarred = isStarred;
        this.lastModified = lastModified;
        this.length = length;
        this.name = name;
        this.fileUri = fileUri;
        this.urlThumbnail = urlThumbnail;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int compareTo(OfficeModel o) {
        return 0;
    }
    public static Comparator<OfficeModel> sortDateAscending = (OfficeModel o1, OfficeModel o2) -> (int) (o1.getLastModified() - o2.getLastModified());
    public static Comparator<OfficeModel> sortDateDescending = (o1, o2) -> (int) (o2.getLastModified() - o1.getLastModified());
    public static Comparator<OfficeModel> sortNameAscending = Comparator.comparing(OfficeModel::getName);
    public static Comparator<OfficeModel> sortNameDescending = (o1, o2) -> o2.getName().compareTo(o1.getName());
    public static Comparator<OfficeModel> sortFileSizeAscending = (o1, o2) -> (int) (o1.getLength() - o2.getLength());
    public static Comparator<OfficeModel> sortFileSizeDescending = (o1, o2) -> (int) (o2.getLength() - o1.getLength());
}
