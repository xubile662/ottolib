package com.otto.mart.viewmodule.model.PickerUiModel;

import android.content.Intent;
import android.support.annotation.Nullable;

public class DashboardIconModel extends BaseUiModel {
    private String title;
    private Intent target;
    private int iconID;
    private String extradataString;
    private int extradataInt;

    public DashboardIconModel(String title, Intent target, int iconID, @Nullable String extradataString, @Nullable int extradataInt) {
        this.title = title;
        this.target = target;
        this.iconID = iconID;
        this.extradataString = extradataString;
        this.extradataInt = extradataInt != 0 ? extradataInt : 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Intent getTarget() {
        return target;
    }

    public void setTarget(Intent target) {
        this.target = target;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getExtradataString() {
        return extradataString;
    }

    public void setExtradataString(String extradataString) {
        this.extradataString = extradataString;
    }

    public int getExtradataInt() {
        return extradataInt;
    }

    public void setExtradataInt(int extradataInt) {
        this.extradataInt = extradataInt;
    }
}
