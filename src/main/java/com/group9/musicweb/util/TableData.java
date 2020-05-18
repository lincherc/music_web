package com.group9.musicweb.util;

/**
 * 描述：bootstrap后台分页前台的显示
 */
public class TableData<T> {
    private Integer page;
    private Long total;
    private T rows;

    public TableData() {

    }

    public TableData(Integer page, Long total, T rows) {
        this.page = page;
        this.total = total;
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }
}
