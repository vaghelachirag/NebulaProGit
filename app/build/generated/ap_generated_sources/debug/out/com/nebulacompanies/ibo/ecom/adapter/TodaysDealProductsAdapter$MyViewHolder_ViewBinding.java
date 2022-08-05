// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TodaysDealProductsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private TodaysDealProductsAdapter.MyViewHolder target;

  @UiThread
  public TodaysDealProductsAdapter$MyViewHolder_ViewBinding(
      TodaysDealProductsAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TodaysDealProductsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.thumbnail = null;
    target.cardView = null;
  }
}
