package com.example.InspectionBoard.model.dto.db;

public class FindByPageDto {
    private final int pageNumber;
    private final int itemsPerPage;

    public FindByPageDto(int pageNumber, int itemsPerPage) {
        this.pageNumber = pageNumber;
        this.itemsPerPage = itemsPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }
}
