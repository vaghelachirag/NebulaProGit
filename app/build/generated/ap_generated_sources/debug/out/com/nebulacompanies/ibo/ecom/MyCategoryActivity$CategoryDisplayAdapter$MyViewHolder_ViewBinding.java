// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyCategoryActivity$CategoryDisplayAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private MyCategoryActivity.CategoryDisplayAdapter.MyViewHolder target;

  @UiThread
  public MyCategoryActivity$CategoryDisplayAdapter$MyViewHolder_ViewBinding(
      MyCategoryActivity.CategoryDisplayAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.textViewDispname, "field 'name'", MyTextViewEcom.class);
    target.lineview = Utils.findRequiredView(source, R.id.line, "field 'lineview'");
  }

  @Override
  @CallSuper
  public void unbind() {
    MyCategoryActivity.CategoryDisplayAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.lineview = null;
  }
}
