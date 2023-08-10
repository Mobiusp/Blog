package com.mobiusp.backstage.pojo;

import java.util.List;

public class Page<T> {
    private Integer index;
    private Integer size;
    private List<T> data;

    @Override
    public String toString() {
        return "Page{" +
                "index=" + index +
                ", size=" + size +
                ", data=" + data +
                '}';
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Page(Integer index, Integer size, List<T> data) {
        this.index = index;
        this.size = size;
        this.data = data;
    }
}
