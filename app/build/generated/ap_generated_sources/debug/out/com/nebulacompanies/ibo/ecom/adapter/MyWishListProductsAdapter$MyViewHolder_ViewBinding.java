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

public class MyWishListProductsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private MyWishListProductsAdapter.MyViewHolder target;

  @UiThread
  public MyWishListProductsAdapter$MyViewHolder_ViewBinding(
      MyWishListProductsAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.btnAddToCart = Utils.findRequiredViewAsType(source, R.id.btn_add_to_cart, "field 'btnAddToCart'", MyButtonEcom.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyWishListProductsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.thumbnail = null;
    target.cardView = null;
    target.btnAddToCart = null;
  }
}
