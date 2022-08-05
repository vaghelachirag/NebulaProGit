// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.adapter;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WeeklyMatchingAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private WeeklyMatchingAdapter.MyViewHolder target;

  @UiThread
  public WeeklyMatchingAdapter$MyViewHolder_ViewBinding(WeeklyMatchingAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.date = Utils.findRequiredViewAsType(source, R.id.date, "field 'date'", MyTextView.class);
    target.PVF1 = Utils.findRequiredViewAsType(source, R.id.PVF1, "field 'PVF1'", MyTextView.class);
    target.PVF2 = Utils.findRequiredViewAsType(source, R.id.PVF2, "field 'PVF2'", MyTextView.class);
    target.matching = Utils.findRequiredViewAsType(source, R.id.matching, "field 'matching'", MyTextView.class);
    target.tvCf = Utils.findRequiredViewAsType(source, R.id.weekly_cf, "field 'tvCf'", MyTextView.class);
    target.tvCfPv2 = Utils.findRequiredViewAsType(source, R.id.weekly_cfpv2, "field 'tvCfPv2'", MyTextView.class);
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.ln_weekly_matching, "field 'linearLayout'", LinearLayout.class);
    target.linearLayoutCF = Utils.findRequiredViewAsType(source, R.id.weekly_PVcf, "field 'linearLayoutCF'", LinearLayout.class);
    target.linearLayoutCF2 = Utils.findRequiredViewAsType(source, R.id.weekly_cf2, "field 'linearLayoutCF2'", LinearLayout.class);
    target.horizontalScrollView = Utils.findRequiredViewAsType(source, R.id.horizontal_weekly_matching, "field 'horizontalScrollView'", HorizontalScrollView.class);
    target.viewWeeklymatching = Utils.findRequiredView(source, R.id.view_weekly_matching, "field 'viewWeeklymatching'");
    target.tvName = Utils.findRequiredViewAsType(source, R.id.PVF1Name, "field 'tvName'", MyTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WeeklyMatchingAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.date = null;
    target.PVF1 = null;
    target.PVF2 = null;
    target.matching = null;
    target.tvCf = null;
    target.tvCfPv2 = null;
    target.linearLayout = null;
    target.linearLayoutCF = null;
    target.linearLayoutCF2 = null;
    target.horizontalScrollView = null;
    target.viewWeeklymatching = null;
    target.tvName = null;
  }
}
