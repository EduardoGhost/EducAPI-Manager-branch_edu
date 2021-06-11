package br.ufpb.dcx.apps4society.educapimanager.control.service.response;

import java.util.List;

import br.ufpb.dcx.apps4society.educapimanager.model.bean.Context;

public class ContextPageResponse {

    private List<Context> content;
    private Integer size;
    private boolean first;
    private boolean last;
    private Integer page;

    public List<Context> getContent() {
        return content;
    }

    public void setContent(List<Context> content) {
        this.content = content;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
