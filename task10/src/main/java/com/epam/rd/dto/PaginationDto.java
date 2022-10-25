package com.epam.rd.dto;

public class PaginationDto {
    private int currentPage;
    private int totalPages;
    private String urlPrefix;

    public PaginationDto() {
    }

    public PaginationDto(int currentPage, int totalPages, String urlPrefix) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.urlPrefix = urlPrefix;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
}
