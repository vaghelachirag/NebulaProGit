// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyCategoryActivity$CategoryDetailsAdapter$MyViewHolderTopBanner_ViewBinding implements Unbinder {
  private MyCategoryActivity.CategoryDetailsAdapter.MyViewHolderTopBanner target;

  @UiThread
  public MyCategoryActivity$CategoryDetailsAdapter$MyViewHolderTopBanner_ViewBinding(
      MyCategoryActivity.CategoryDetailsAdapter.MyViewHolderTopBanner target, View source) {
    this.target = target;

    target.tvSort = Utils.findRequiredViewAsType(source, R.id.tv_sort, "field 'tvSort'", MyTextViewEcom.class);
    target.recyclerCategoryList = Utils.findRequiredViewAsType(source, R.id.recyclerview_category_home, "field 'recyclerCategoryList'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyCategoryActivity.CategoryDetailsAdapter.MyViewHolderTopBanner target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvSort = null;
    target.recyclerCategoryList = null;
  }
}
