package com.otto.mart.viewmodule.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.adapter.SpinnerBankModelAdapter;
import com.otto.mart.viewmodule.model.BankListingModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.lang.reflect.Field;
import java.util.List;

import glenn.base.viewmodule.IViewBase;
import glenn.base.viewmodule.spinner.CategoryModel;
import glenn.base.viewmodule.spinner.CustomSpinner;

public class HidableBankSpinnerView extends LinearLayout implements IViewBase {
    private Context mContext;
    private boolean hasTitle, hasCustomTypeface, hasCustomLayout;
    private boolean isBeingHidden;
    private String title;
    private Typeface customTypeface;
    private int spinnerLayout, dropdownLayout;
    private CustomSpinner content;
    private ExpandableLayout expandableLayout, expandableLoadingLayout;
    private SpinnerBankModelAdapter adapter;

    private ViewGroup mainLayout;

    HidableSpinnerviewCallback listener = null;


    public HidableBankSpinnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HideableSpinnerView);
        int count = typedArray.getIndexCount();
        int layout = R.layout.cw_spinner_hideable_default;
        spinnerLayout = R.layout.cw_spinnercontent_view_default;
        dropdownLayout = R.layout.cw_spinnercontent_dropdown_default;


        try {
            for (int i = 0; i < count; ++i) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.HideableSpinnerView_title) {
                    hasTitle = true;
                    title = typedArray.getString(attr);
                    if (title.contains("@string")) {
                        title = getResources().getString(Integer.parseInt(title));
                    }
                } else if (attr == R.styleable.HideableSpinnerView_customLayout) {
                    hasCustomLayout = true;
                    layout = typedArray.getResourceId(attr, R.layout.cw_spinner_default);
                } else if (attr == R.styleable.HideableSpinnerView_customSpinnerLayout) {
                    spinnerLayout = typedArray.getResourceId(attr, R.layout.cw_spinnercontent_view_default);
                } else if (attr == R.styleable.HideableSpinnerView_customDropdownLayout) {
                    dropdownLayout = typedArray.getResourceId(attr, R.layout.cw_spinnercontent_dropdown_default);
                } else if (attr == R.styleable.HideableSpinnerView_typeface) {
                    hasCustomTypeface = true;
                    customTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + typedArray.getString(attr));
                } else if (attr == R.styleable.HideableSpinnerView_isHidden) {
                    isBeingHidden = typedArray.getBoolean(attr, false);
                }
            }
        } catch (Exception e) {
            Log.e("SearchEdittext", e.getMessage());
        } finally {
            typedArray.recycle();
        }
        inflater.inflate(layout, this);
        initComponent();
        initContent();
    }

    private void initComponent() {
        adapter = new SpinnerBankModelAdapter(mContext, spinnerLayout, dropdownLayout);
        content = findViewById(R.id.content);
        content.setAdapter(adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(content);
//            popupWindow.setHeight(DeviceUtil.dpToPx(250));
            popupWindow.setHeight(400);

        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }

        TextView titleText = findViewById(R.id.title);
        if (hasTitle)
            titleText.setText(title);
        expandableLayout = findViewById(R.id.eLayout);
        if (isBeingHidden)
            expandableLayout.setExpanded(false);
        else
            expandableLayout.setExpanded(true);
        expandableLoadingLayout = findViewById(R.id.eLayout_loading);
    }

    private void initContent() {
        content.setOnItemSelectedEvenIfUnchangedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.isCallbackReady()) {
                    if (listener != null) {
                        listener.onItemSelected(content, position, (SpinnerBankModelAdapter) content.getAdapter());
                    }
                }
                if (position == 0)
                    adapter.changeFirstItemToText();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addData(List<BankListingModel> models) {
        adapter.addModels(models);
        adapter.notifyDataSetChanged();
        expandSpinner();
        if (listener != null) {
            listener.onDataLoaded(content, adapter);
        }
    }

    public void removeData() {
        adapter.resetAdapter();
        hideSpinner();
        hideLoading();
    }

    public int getSelectedItemId() {
        return ((CategoryModel) adapter.getItem((content.getSelectedItemPosition()))).getId();
    }

    public String getSelectedItemValue() {
        return ((CategoryModel) adapter.getItem((content.getSelectedItemPosition()))).getTitle();
    }

    public int getSelectedItemPos() {
        return content.getSelectedItemPosition();
    }


    public void expandSpinner() {
        if (expandableLoadingLayout.isExpanded()) {
            hideLoading();
        }
        expandableLayout.setExpanded(true);
        if (listener != null) {
            listener.onExpand();
        }

    }

    public void expandLoading() {
        expandableLoadingLayout.setExpanded(true);
    }

    public void hideSpinner() {
        expandableLayout.setExpanded(false);
        if (listener != null) {
            listener.onHide();
        }
    }

    public void hideLoading() {
        expandableLoadingLayout.setExpanded(false);
    }

    public void setOnCallback(HidableSpinnerviewCallback listener) {
        this.listener = listener;
    }


    @Override
    public String getTextContent() {
        return getSelectedItemValue();
    }

    @Override
    public View getTitleComponent() {
        return null;
    }

    @Override
    public View getComponent() {
        return content;
    }

    @Override
    public void setTitle(String text) {
    }

    public void setSelection(int sel) {
        content.setSelection(sel);
    }

    public interface HidableSpinnerviewCallback {
        void onItemSelected(Spinner view, int position, SpinnerBankModelAdapter adapter);

        void onDropdownOpen();

        void onHide();

        void onExpand();

        void onDataLoaded(Spinner view, SpinnerBankModelAdapter adapter);
    }
}
