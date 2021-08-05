package com.icampus.common.bean;

import java.util.List;

public class EasyUIResult{

    private Long total;
    private Integer currentPage;
    private List<?> rows;

    public EasyUIResult() {

    }
    
    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public EasyUIResult(Long total, List<?> rows, Integer currentPage) {
        this.total = total;
        this.rows = rows;
        this.currentPage = currentPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
