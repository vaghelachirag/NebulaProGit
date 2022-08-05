// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyLocationUsingLocationAPI_ViewBinding implements Unbinder {
  private MyLocationUsingLocationAPI target;

  @UiThread
  public MyLocationUsingLocationAPI_ViewBinding(MyLocationUsingLocationAPI target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyLocationUsingLocationAPI_ViewBinding(MyLocationUsingLocationAPI target, View source) {
    this.target = target;

    target.btnProceed = Utils.findRequiredViewAsType(source, R.id.btnLocation, "field 'btnProceed'", Button.class);
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tvAddress, "field 'tvAddress'", TextView.class);
    target.tvEmpty = Utils.findRequiredViewAsType(source, R.id.tvEmpty, "field 'tvEmpty'", TextView.class);
    target.rlPick = Utils.findRequiredViewAsType(source, R.id.rlPickLocation, "field 'rlPick'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyLocationUsingLocationAPI target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnProceed = null;
    target.tvAddress = null;
    target.tvEmpty = null;
    target.rlPick = null;
  }
}
