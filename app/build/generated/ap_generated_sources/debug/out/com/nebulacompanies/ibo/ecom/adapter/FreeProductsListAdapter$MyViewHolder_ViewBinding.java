// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FreeProductsListAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private FreeProductsListAdapter.MyViewHolder target;

  @UiThread
  public FreeProductsListAdapter$MyViewHolder_ViewBinding(
      FreeProductsListAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.tvMyCartName = Utils.findRequiredViewAsType(source, R.id.tv_mycart_name, "field 'tvMyCartName'", MyTextViewEcom.class);
    target.tvQuntyDesc = Utils.findRequiredViewAsType(source, R.id.tv_mycart_tablet, "field 'tvQuntyDesc'", MyTextViewEcom.class);
    target.tvProductCount = Utils.findRequiredViewAsType(source, R.id.product_count, "field 'tvProductCount'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.tvCartPV = Utils.findRequiredViewAsType(source, R.id.tv_pv_value, "field 'tvCartPV'", MyTextViewEcom.class);
    target.tvCartNV = Utils.findRequiredViewAsType(source, R.id.tv_nv_value, "field 'tvCartNV'", MyTextViewEcom.class);
    target.tvCartBV = Utils.findRequiredViewAsType(source, R.id.tv_bv_value, "field 'tvCartBV'", MyTextViewEcom.class);
    target.tvMRP = Utils.findRequiredViewAsType(source, R.id.tv_original_price_value, "field 'tvMRP'", MyTextViewEcom.class);
    target.tvDiscountedPrice = Utils.findRequiredViewAsType(source, R.id.tv_mycart_item_price, "field 'tvDiscountedPrice'", MyTextViewEcom.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.rdSelect = Utils.findRequiredViewAsType(source, R.id.rd_selected, "field 'rdSelect'", RadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FreeProductsListAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvMyCartName = null;
    target.tvQuntyDesc = null;
    target.tvProductCount = null;
    target.thumbnail = null;
    target.tvCartPV = null;
    target.tvCartNV = null;
    target.tvCartBV = null;
    target.tvMRP = null;
    target.tvDiscountedPrice = null;
    target.cardView = null;
    target.rdSelect = null;
  }
}
