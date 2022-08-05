package com.nebulacompanies.ibo.util;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.nebulacompanies.ibo.showcaseviewback.ViewTarget;

import java.lang.reflect.Field;

public class ViewTargets {

    public static ViewTarget navigationButtonViewTarget(Toolbar toolbar) throws MissingViewException {
        try {
            Field field = Toolbar.class.getDeclaredField("mNavButtonView");
            field.setAccessible(true);
            View navigationView = (View) field.get(toolbar);
            return new ViewTarget(navigationView);
        } catch (NoSuchFieldException e) {
            throw new MissingViewException(e);
        } catch (IllegalAccessException e) {
            throw new MissingViewException(e);
        }
    }

    public static class MissingViewException extends Exception {

        public MissingViewException(Throwable throwable) {
            super(throwable);
        }
    }
}