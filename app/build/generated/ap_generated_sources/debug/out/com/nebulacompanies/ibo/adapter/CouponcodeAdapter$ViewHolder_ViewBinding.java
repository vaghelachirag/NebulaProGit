// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.adapter;

import android.view.View;
import android.widget.RadioButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CouponcodeAdapter$ViewHolder_ViewBinding implements Unbinder {
  private CouponcodeAdapter.ViewHolder target;

  @UiThread
  public CouponcodeAdapter$ViewHolder_ViewBinding(CouponcodeAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.radioButton = Utils.findRequiredViewAsType(source, R.id.radio_sel, "field 'radioButton'", RadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CouponcodeAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.radioButton = null;
  }
}
