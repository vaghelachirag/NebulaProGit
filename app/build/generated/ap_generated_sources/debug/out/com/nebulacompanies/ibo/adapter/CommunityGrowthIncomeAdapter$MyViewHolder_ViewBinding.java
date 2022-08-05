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

public class CommunityGrowthIncomeAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private CommunityGrowthIncomeAdapter.MyViewHolder target;

  @UiThread
  public CommunityGrowthIncomeAdapter$MyViewHolder_ViewBinding(
      CommunityGrowthIncomeAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.date = Utils.findRequiredViewAsType(source, R.id.community_growth_date, "field 'date'", MyTextView.class);
    target.PVF1 = Utils.findRequiredViewAsType(source, R.id.community_growth_PVF1, "field 'PVF1'", MyTextView.class);
    target.PVF2 = Utils.findRequiredViewAsType(source, R.id.community_growth_PVF2, "field 'PVF2'", MyTextView.class);
    target.matching = Utils.findRequiredViewAsType(source, R.id.community_growth_matching, "field 'matching'", MyTextView.class);
    target.community_growth = Utils.findRequiredViewAsType(source, R.id.community_growth, "field 'community_growth'", MyTextView.class);
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.ln_community_growth, "field 'linearLayout'", LinearLayout.class);
    target.horizontalScrollView = Utils.findRequiredViewAsType(source, R.id.horizontal_community_growth, "field 'horizontalScrollView'", HorizontalScrollView.class);
    target.viewCommunity = Utils.findRequiredView(source, R.id.view_community, "field 'viewCommunity'");
  }

  @Override
  @CallSuper
  public void unbind() {
    CommunityGrowthIncomeAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.date = null;
    target.PVF1 = null;
    target.PVF2 = null;
    target.matching = null;
    target.community_growth = null;
    target.linearLayout = null;
    target.horizontalScrollView = null;
    target.viewCommunity = null;
  }
}
