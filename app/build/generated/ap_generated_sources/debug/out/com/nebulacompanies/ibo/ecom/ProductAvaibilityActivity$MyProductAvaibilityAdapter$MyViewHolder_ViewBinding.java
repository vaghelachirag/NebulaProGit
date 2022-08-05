// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductAvaibilityActivity$MyProductAvaibilityAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ProductAvaibilityActivity.MyProductAvaibilityAdapter.MyViewHolder target;

  @UiThread
  public ProductAvaibilityActivity$MyProductAvaibilityAdapter$MyViewHolder_ViewBinding(
      ProductAvaibilityActivity.MyProductAvaibilityAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.productQuantity = Utils.findRequiredViewAsType(source, R.id.product_quantity, "field 'productQuantity'", MyTextViewEcom.class);
    target.tvProductName = Utils.findRequiredViewAsType(source, R.id.name, "field 'tvProductName'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.tvMRPPrice = Utils.findRequiredViewAsType(source, R.id.tv_original_price_value, "field 'tvMRPPrice'", MyTextViewEcom.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductAvaibilityActivity.MyProductAvaibilityAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.productQuantity = null;
    target.tvProductName = null;
    target.thumbnail = null;
    target.tvMRPPrice = null;
  }
}
