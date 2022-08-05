// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.dwarkaPackage.adapter;

import android.view.View;
import android.widget.RadioButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DwarkaPackageAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private DwarkaPackageAdapter.MyViewHolder target;

  @UiThread
  public DwarkaPackageAdapter$MyViewHolder_ViewBinding(DwarkaPackageAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.rbPackage = Utils.findRequiredViewAsType(source, R.id.rb_package_one, "field 'rbPackage'", RadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DwarkaPackageAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rbPackage = null;
  }
}
