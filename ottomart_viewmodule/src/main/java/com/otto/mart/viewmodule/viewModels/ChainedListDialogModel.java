package com.otto.mart.viewmodule.viewModels;

import java.util.List;

public class ChainedListDialogModel {

    int id;
    String code;
    String name;
    Boolean isSelected = false;
    List<ChainedListDialogModel> business_categories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public List<ChainedListDialogModel> getBusiness_categories() {
        return business_categories;
    }

    public void setBusiness_categories(List<ChainedListDialogModel> business_categories) {
        this.business_categories = business_categories;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
