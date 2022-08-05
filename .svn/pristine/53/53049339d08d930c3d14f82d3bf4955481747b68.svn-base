package com.nebulacompanies.ibo.ecom.customBanner;

import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.nebulacompanies.ibo.ecom.customBanner.model.PagerTextItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RxRead on 2015/9/24.
 */
public class MockDataTextGenerator {


    public static List<PagerTextItem> getViewPagerData2() {
        List<PagerTextItem> list = new ArrayList<>();
        PagerTextItem item = null;
        SpannableString aavaasChennai = new SpannableString(Html.fromHtml("Nebula announces the launch of Aavaas Chennai opposite Mahindra World City. <i>11/01/2019</i>"));
        SpannableString aavaasChangodar = new SpannableString(Html.fromHtml("Construction images of Aavaas Changodar have been uploaded. <i>11/01/2019</i>"));
        SpannableString hawthornDwarka = new SpannableString(Html.fromHtml("Construction images of Hawthorn Dwarka have been uploaded. <i>11/01/2019</i>"));
        SpannableString scrollText = new SpannableString(Html.fromHtml("Aavaas Chennai Launch event images have been updated. <i>10/12/2018</i>"));

        aavaasChennai.setSpan(new ForegroundColorSpan(Color.parseColor("#009CE0")), 31, 45, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        aavaasChennai.setSpan(new ForegroundColorSpan(Color.parseColor("#99ff00")), 76, 86, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        aavaasChennai.setSpan(new RelativeSizeSpan(0.8f), 76,86, 0);

        aavaasChangodar.setSpan(new ForegroundColorSpan(Color.parseColor("#009CE0")), 22, 39, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        aavaasChangodar.setSpan(new ForegroundColorSpan(Color.parseColor("#99ff00")), 60, 70, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        aavaasChangodar.setSpan(new RelativeSizeSpan(0.8f), 60,70, 0);

        hawthornDwarka.setSpan(new ForegroundColorSpan(Color.parseColor("#009CE0")), 22, 38, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        hawthornDwarka.setSpan(new ForegroundColorSpan(Color.parseColor("#99ff00")), 59, 69, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        hawthornDwarka.setSpan(new RelativeSizeSpan(0.8f), 59,69, 0);

        scrollText.setSpan(new ForegroundColorSpan(Color.parseColor("#009CE0")), 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        scrollText.setSpan(new ForegroundColorSpan(Color.parseColor("#99ff00")), 53, 64, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        scrollText.setSpan(new RelativeSizeSpan(0.8f), 53,64, 0);

        for (int i = 0; i < 4; i++) {
            item = new PagerTextItem();
            item.setPosition(i);
            int index = i % 4;
            if (index == 0) {
                item.setDesc(aavaasChennai);
            } else if (index == 1) {
                item.setDesc(aavaasChangodar);
            } else if (index == 2) {
                item.setDesc(hawthornDwarka);
            } else if (index == 3) {
                item.setDesc(scrollText);
            }
            list.add(item);
        }
        return list;
    }
}
