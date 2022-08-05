// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CategoryAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private CategoryAdapter.MyViewHolder target;

  @UiThread
  public CategoryAdapter$MyViewHolder_ViewBinding(CategoryAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.textname, "field 'name'", TextView.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", LinearLayout.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.img_category, "field 'thumbnail'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CategoryAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.cardView = null;
    target.thumbnail = null;
  }
}
