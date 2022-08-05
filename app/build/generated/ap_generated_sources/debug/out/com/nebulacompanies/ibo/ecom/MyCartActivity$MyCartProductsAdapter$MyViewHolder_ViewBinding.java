// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class MyCartActivity$MyCartProductsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private MyCartActivity.MyCartProductsAdapter.MyViewHolder target;

  @UiThread
  public MyCartActivity$MyCartProductsAdapter$MyViewHolder_ViewBinding(
      MyCartActivity.MyCartProductsAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.tvMyCartName = Utils.findRequiredViewAsType(source, R.id.tv_mycart_name, "field 'tvMyCartName'", MyTextViewEcom.class);
    target.tvMyCartItemPrice = Utils.findRequiredViewAsType(source, R.id.tv_mycart_item_price, "field 'tvMyCartItemPrice'", MyTextViewEcom.class);
    target.tvProductCount = Utils.findRequiredViewAsType(source, R.id.product_count, "field 'tvProductCount'", MyTextViewEcom.class);
    target.tvMyCartTablet = Utils.findRequiredViewAsType(source, R.id.tv_mycart_tablet, "field 'tvMyCartTablet'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.imgDelete = Utils.findRequiredViewAsType(source, R.id.img_my_cart_delete, "field 'imgDelete'", ImageView.class);
    target.btnRemoveItem = Utils.findRequiredViewAsType(source, R.id.btn_remove_item, "field 'btnRemoveItem'", MyButtonEcom.class);
    target.btnAddItem = Utils.findRequiredViewAsType(source, R.id.btn_add_item, "field 'btnAddItem'", MyButtonEcom.class);
    target.btnAddSize = Utils.findRequiredViewAsType(source, R.id.btn_add_size, "field 'btnAddSize'", MyButtonEcom.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.tvCartPV = Utils.findRequiredViewAsType(source, R.id.tv_pv_value, "field 'tvCartPV'", MyTextViewEcom.class);
    target.tvCartNV = Utils.findRequiredViewAsType(source, R.id.tv_nv_value, "field 'tvCartNV'", MyTextViewEcom.class);
    target.tvCartBV = Utils.findRequiredViewAsType(source, R.id.tv_bv_value, "field 'tvCartBV'", MyTextViewEcom.class);
    target.tvMRPPrice = Utils.findRequiredViewAsType(source, R.id.tv_original_price_value, "field 'tvMRPPrice'", MyTextViewEcom.class);
    target.txtReward = Utils.findRequiredViewAsType(source, R.id.txtreward, "field 'txtReward'", MyTextViewEcom.class);
    target.layfree = Utils.findRequiredViewAsType(source, R.id.lv_free, "field 'layfree'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyCartActivity.MyCartProductsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvMyCartName = null;
    target.tvMyCartItemPrice = null;
    target.tvProductCount = null;
    target.tvMyCartTablet = null;
    target.thumbnail = null;
    target.imgDelete = null;
    target.btnRemoveItem = null;
    target.btnAddItem = null;
    target.btnAddSize = null;
    target.cardView = null;
    target.tvCartPV = null;
    target.tvCartNV = null;
    target.tvCartBV = null;
    target.tvMRPPrice = null;
    target.txtReward = null;
    target.layfree = null;
  }
}
