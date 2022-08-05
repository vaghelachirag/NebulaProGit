// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CategoryDecsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private CategoryDecsAdapter.MyViewHolder target;

  @UiThread
  public CategoryDecsAdapter$MyViewHolder_ViewBinding(CategoryDecsAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", MyBoldTextViewEcom.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.img_category, "field 'thumbnail'", ImageView.class);
    target.rl = Utils.findRequiredViewAsType(source, R.id.rl, "field 'rl'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CategoryDecsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.cardView = null;
    target.thumbnail = null;
    target.rl = null;
  }
}
