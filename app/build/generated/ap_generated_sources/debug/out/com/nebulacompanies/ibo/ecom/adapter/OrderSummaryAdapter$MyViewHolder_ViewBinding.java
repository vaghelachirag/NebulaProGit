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
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderSummaryAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private OrderSummaryAdapter.MyViewHolder target;

  @UiThread
  public OrderSummaryAdapter$MyViewHolder_ViewBinding(OrderSummaryAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.tvMyCartName = Utils.findRequiredViewAsType(source, R.id.tv_mycart_name, "field 'tvMyCartName'", MyTextViewEcom.class);
    target.tvProductCount = Utils.findRequiredViewAsType(source, R.id.product_count, "field 'tvProductCount'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderSummaryAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvMyCartName = null;
    target.tvProductCount = null;
    target.thumbnail = null;
    target.cardView = null;
  }
}
