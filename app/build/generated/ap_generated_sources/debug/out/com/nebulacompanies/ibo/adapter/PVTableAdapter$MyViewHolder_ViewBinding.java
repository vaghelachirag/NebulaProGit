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

public class PVTableAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private PVTableAdapter.MyViewHolder target;

  @UiThread
  public PVTableAdapter$MyViewHolder_ViewBinding(PVTableAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.Families = Utils.findRequiredViewAsType(source, R.id.families, "field 'Families'", MyTextView.class);
    target.Rank = Utils.findRequiredViewAsType(source, R.id.rank, "field 'Rank'", MyTextView.class);
    target.PVToday = Utils.findRequiredViewAsType(source, R.id.PvToday, "field 'PVToday'", MyTextView.class);
    target.WeeklyPV = Utils.findRequiredViewAsType(source, R.id.weeklyPv, "field 'WeeklyPV'", MyTextView.class);
    target.LifetimePV = Utils.findRequiredViewAsType(source, R.id.lifetimePv, "field 'LifetimePV'", MyTextView.class);
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.ln_pv_table, "field 'linearLayout'", LinearLayout.class);
    target.horizontalView = Utils.findRequiredViewAsType(source, R.id.horizontal_view, "field 'horizontalView'", HorizontalScrollView.class);
    target.viewPV = Utils.findRequiredView(source, R.id.view_pv, "field 'viewPV'");
  }

  @Override
  @CallSuper
  public void unbind() {
    PVTableAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Families = null;
    target.Rank = null;
    target.PVToday = null;
    target.WeeklyPV = null;
    target.LifetimePV = null;
    target.linearLayout = null;
    target.horizontalView = null;
    target.viewPV = null;
  }
}
