// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom.adapter;

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
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MyViewOrderAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private MyViewOrderAdapter.MyViewHolder target;

  @UiThread
  public MyViewOrderAdapter$MyViewHolder_ViewBinding(MyViewOrderAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", MyTextViewEcom.class);
    target.tvPrice = Utils.findRequiredViewAsType(source, R.id.tv_offer_price_value, "field 'tvPrice'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.btnRemoveItem = Utils.findRequiredViewAsType(source, R.id.btn_remove_item, "field 'btnRemoveItem'", MyButtonEcom.class);
    target.btnAddItem = Utils.findRequiredViewAsType(source, R.id.btn_add_item, "field 'btnAddItem'", MyButtonEcom.class);
    target.btnOrderCancel = Utils.findRequiredViewAsType(source, R.id.btn_cancel, "field 'btnOrderCancel'", MyButtonEcom.class);
    target.tvCancel = Utils.findRequiredViewAsType(source, R.id.tv_cancel, "field 'tvCancel'", MyTextViewEcom.class);
    target.tvConfirmDate = Utils.findRequiredViewAsType(source, R.id.tv_confirm_date, "field 'tvConfirmDate'", MyTextViewEcom.class);
    target.tvPackedDate = Utils.findRequiredViewAsType(source, R.id.tv_packed_date, "field 'tvPackedDate'", MyTextViewEcom.class);
    target.tvDispatchedDate = Utils.findRequiredViewAsType(source, R.id.tv_dispatched_date, "field 'tvDispatchedDate'", MyTextViewEcom.class);
    target.btnOrderReturn = Utils.findRequiredViewAsType(source, R.id.btn_return, "field 'btnOrderReturn'", MyButtonEcom.class);
    target.imgConfirmed = Utils.findRequiredViewAsType(source, R.id.img_confirmed_horizontal, "field 'imgConfirmed'", ImageView.class);
    target.imgPacked = Utils.findRequiredViewAsType(source, R.id.img_packed_horizontal, "field 'imgPacked'", ImageView.class);
    target.imgDispacted = Utils.findRequiredViewAsType(source, R.id.img_dispacted_horizontal, "field 'imgDispacted'", ImageView.class);
    target.imgDelivered = Utils.findRequiredViewAsType(source, R.id.img_delivered_horizontal, "field 'imgDelivered'", ImageView.class);
    target.materialProgressBar = Utils.findRequiredViewAsType(source, R.id.progresbar, "field 'materialProgressBar'", MaterialProgressBar.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.lnTrackHorizontal = Utils.findRequiredViewAsType(source, R.id.ln_track_horizontal, "field 'lnTrackHorizontal'", LinearLayout.class);
    target.cardViewTrack = Utils.findRequiredViewAsType(source, R.id.card_view_track, "field 'cardViewTrack'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyViewOrderAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.tvPrice = null;
    target.thumbnail = null;
    target.btnRemoveItem = null;
    target.btnAddItem = null;
    target.btnOrderCancel = null;
    target.tvCancel = null;
    target.tvConfirmDate = null;
    target.tvPackedDate = null;
    target.tvDispatchedDate = null;
    target.btnOrderReturn = null;
    target.imgConfirmed = null;
    target.imgPacked = null;
    target.imgDispacted = null;
    target.imgDelivered = null;
    target.materialProgressBar = null;
    target.cardView = null;
    target.lnTrackHorizontal = null;
    target.cardViewTrack = null;
  }
}
