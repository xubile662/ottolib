package com.otto.mart.viewmodule.editText;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.otto.mart.viewmodule.R;

import glenn.base.viewmodule.editText.LazyEdittext;

public class LazyEdittextLogin extends LazyEdittext {
    private View actionbtn;

    public LazyEdittextLogin(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponent();
    }

    private void initComponent() {
        actionbtn = findViewById(R.id.actionhitbox);
    }

    public void initAction(View.OnClickListener listener) {
        actionbtn.setOnClickListener(listener);
    }
}
