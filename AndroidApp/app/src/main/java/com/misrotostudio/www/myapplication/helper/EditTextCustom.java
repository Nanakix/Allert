package com.misrotostudio.www.myapplication.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by othmaneelmassari on 04/12/2015.
 */
public class EditTextCustom extends EditText{

    public EditTextCustom(Context context) {
        super(context);
    }

    public EditTextCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_ENTER)
        {
            // Just ignore the [Enter] key
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
