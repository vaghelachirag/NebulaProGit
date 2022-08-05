// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyCategoryActivity$CategoryDetailsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private MyCategoryActivity.CategoryDetailsAdapter.MyViewHolder target;

  @UiThread
  public MyCategoryActivity$CategoryDetailsAdapter$MyViewHolder_ViewBinding(
      MyCategoryActivity.CategoryDetailsAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", MyTextViewEcom.class);
    target.tvOutOfStock = Utils.findRequiredViewAsType(source, R.id.tv_out_of_stock, "field 'tvOutOfStock'", MyTextViewEcom.class);
    target.nameShortDecs = Utils.findRequiredViewAsType(source, R.id.name_short_decs, "field 'nameShortDecs'", MyTextViewEcom.class);
    target.tvOfferPrice = Utils.findRequiredViewAsType(source, R.id.tv_offer_price_value, "field 'tvOfferPrice'", MyTextViewEcom.class);
    target.tvOriginalPriceSign = Utils.findRequiredViewAsType(source, R.id.tv_original_price, "field 'tvOriginalPriceSign'", MyTextViewEcom.class);
    target.tvOriginalPrice = Utils.findRequiredViewAsType(source, R.id.tv_original_price_value, "field 'tvOriginalPrice'", MyTextViewEcom.class);
    target.tvDiscountPrice = Utils.findRequiredViewAsType(source, R.id.tv_price_discount, "field 'tvDiscountPrice'", MyTextViewEcom.class);
    target.tvPVValue = Utils.findRequiredViewAsType(source, R.id.tv_pv_value, "field 'tvPVValue'", MyTextViewEcom.class);
    target.tvNVValue = Utils.findRequiredViewAsType(source, R.id.tv_nv_value, "field 'tvNVValue'", MyTextViewEcom.class);
    target.tvBVValue = Utils.findRequiredViewAsType(source, R.id.tv_bv_value, "field 'tvBVValue'", MyTextViewEcom.class);
    target.tvCount = Utils.findRequiredViewAsType(source, R.id.product_count, "field 'tvCount'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", AspectRatioImageView.class);
    target.icAdd = Utils.findRequiredViewAsType(source, R.id.ic_add, "field 'icAdd'", ImageView.class);
    target.imgShare = Utils.findRequiredViewAsType(source, R.id.img_share, "field 'imgShare'", ImageView.class);
    target.icRemove = Utils.findRequiredViewAsType(source, R.id.ic_remove, "field 'icRemove'", ImageView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    target.rlContent = Utils.findRequiredViewAsType(source, R.id.rlContent, "field 'rlContent'", RelativeLayout.class);
    target.productWeight = Utils.findRequiredViewAsType(source, R.id.product_weight, "field 'productWeight'", MyTextViewEcom.class);
    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.ratingBar, "field 'ratingBar'", RatingBar.class);
    target.btnAddToCart = Utils.findRequiredViewAsType(source, R.id.btn_add_to_cart, "field 'btnAddToCart'", MyButtonEcom.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyCategoryActivity.CategoryDetailsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.tvOutOfStock = null;
    target.nameShortDecs = null;
    target.tvOfferPrice = null;
    target.tvOriginalPriceSign = null;
    target.tvOriginalPrice = null;
    target.tvDiscountPrice = null;
    target.tvPVValue = null;
    target.tvNVValue = null;
    target.tvBVValue = null;
    target.tvCount = null;
    target.thumbnail = null;
    target.icAdd = null;
    target.imgShare = null;
    target.icRemove = null;
    target.progressBar = null;
    target.rlContent = null;
    target.productWeight = null;
    target.ratingBar = null;
    target.btnAddToCart = null;
  }
}
