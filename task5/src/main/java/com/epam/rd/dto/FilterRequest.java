package com.epam.rd.dto;

/**
 * Entity that represents a set of parameters for filtration.
 */
public class FilterRequest {
    private String fileName;
    private String fileExtension;
    private String fileSizeDiapason;
    private String fileChangeDateDiapason;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileSizeDiapason() {
        return fileSizeDiapason;
    }

    public void setFileSizeDiapason(String fileSizeDiapason) {
        this.fileSizeDiapason = fileSizeDiapason;
    }

    public String getFileChangeDateDiapason() {
        return fileChangeDateDiapason;
    }

    public void setFileChangeDateDiapason(String fileChangeDateDiapason) {
        this.fileChangeDateDiapason = fileChangeDateDiapason;
    }
}