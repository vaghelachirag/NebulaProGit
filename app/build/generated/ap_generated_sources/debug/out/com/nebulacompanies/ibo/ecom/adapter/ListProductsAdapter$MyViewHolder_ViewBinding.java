// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListProductsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ListProductsAdapter.MyViewHolder target;

  @UiThread
  public ListProductsAdapter$MyViewHolder_ViewBinding(ListProductsAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.img_thumb, "field 'thumbnail'", AspectRatioImageView.class);
    target.imageViewHolder = Utils.findRequiredViewAsType(source, R.id.img_thumb_visible, "field 'imageViewHolder'", ImageView.class);
    target.imageViewHolderInactive = Utils.findRequiredViewAsType(source, R.id.img_thumb_invisible, "field 'imageViewHolderInactive'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListProductsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.thumbnail = null;
    target.imageViewHolder = null;
    target.imageViewHolderInactive = null;
  }
}
