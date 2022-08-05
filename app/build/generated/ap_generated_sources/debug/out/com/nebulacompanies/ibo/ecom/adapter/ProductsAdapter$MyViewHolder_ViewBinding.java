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
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ProductsAdapter.MyViewHolder target;

  @UiThread
  public ProductsAdapter$MyViewHolder_ViewBinding(ProductsAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", MyTextViewEcom.class);
    target.nameShortDecs = Utils.findRequiredViewAsType(source, R.id.name_short_decs, "field 'nameShortDecs'", MyTextViewEcom.class);
    target.tvOfferPrice = Utils.findRequiredViewAsType(source, R.id.tv_offer_price_value, "field 'tvOfferPrice'", MyTextViewEcom.class);
    target.tvOriginalPrice = Utils.findRequiredViewAsType(source, R.id.tv_original_price_value, "field 'tvOriginalPrice'", MyTextViewEcom.class);
    target.tvDiscountPrice = Utils.findRequiredViewAsType(source, R.id.tv_price_discount, "field 'tvDiscountPrice'", MyTextViewEcom.class);
    target.tvCount = Utils.findRequiredViewAsType(source, R.id.product_count, "field 'tvCount'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.icAdd = Utils.findRequiredViewAsType(source, R.id.ic_add, "field 'icAdd'", ImageView.class);
    target.icRemove = Utils.findRequiredViewAsType(source, R.id.ic_remove, "field 'icRemove'", ImageView.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.btnAddToCart = Utils.findRequiredViewAsType(source, R.id.btn_add_to_cart, "field 'btnAddToCart'", MyButtonEcom.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.nameShortDecs = null;
    target.tvOfferPrice = null;
    target.tvOriginalPrice = null;
    target.tvDiscountPrice = null;
    target.tvCount = null;
    target.thumbnail = null;
    target.icAdd = null;
    target.icRemove = null;
    target.cardView = null;
    target.btnAddToCart = null;
  }
}
