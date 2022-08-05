package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.BuildConfig;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.RateChartsDetailList;
import com.nebulacompanies.ibo.util.DownloadManagerResolver;
import com.nebulacompanies.ibo.view.DownloadProgressView;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightView;

import java.io.File;
import java.util.ArrayList;

//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());


/**
 * Created by Sagar Virvani on 18-01-2018.
 */

public class RateChartsDetailsAdapter extends BaseAdapter {
    Context context;
    String Pdffile;
    String Imagefile;
    Intent intentShareFile;
    ArrayList<RateChartsDetailList> arrayListRateChartsDetailList = new ArrayList<>();
    String PREFS_WALKTHROUGH_DOWNLOAD = "share";
    SpotlightView spotLightDownload, spotLightShare;
    ViewHolder holder = null;
    DownloadManager downloadManager;

    private long myDownloadReference;

    public RateChartsDetailsAdapter(Context context, ArrayList<RateChartsDetailList> RateChartsDetailLists) {
        this.context = context;
        arrayListRateChartsDetailList.clear();
        arrayListRateChartsDetailList.addAll(RateChartsDetailLists);
    }

    private class ViewHolder {
        ImageView imageView, share;
        MyTextView txtTitle, txtSize;
        DownloadProgressView downloadProgressView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        holder = new ViewHolder();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_sub_item_edocuments, null);

            holder.imageView = (ImageView) convertView.findViewById(R.id.edoc_thumnbail);
            holder.txtTitle = (MyTextView) convertView.findViewById(R.id.edoc_text);
            holder.txtSize = (MyTextView) convertView.findViewById(R.id.edoc_size);
            holder.share = (ImageView) convertView.findViewById(R.id.edoc_share);
            holder.downloadProgressView = (DownloadProgressView) convertView.findViewById(R.id.edoc_downloadProgressView);
            //holder.downloadProgressView = (DownloadProgressView) convertView.findViewById(R.id.list_item_downloadProgress);
            convertView.setTag(holder);
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position < arrayListRateChartsDetailList.size()) {
            holder.txtTitle.setText(arrayListRateChartsDetailList.get(position).getRateChartName());
            holder.txtSize.setText(arrayListRateChartsDetailList.get(position).getRateChartFileSize());

           /* Glide.with(context).load(arrayListRateChartsDetailList.get(position).getRateChartThumbnail())
                    .transition( new DrawableTransitionOptions().crossFade() )
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.nebula_placeholder_land)
                    .into(holder.imageView);*/

            Glide.with(context)
                    .load(arrayListRateChartsDetailList.get(position).getRateChartThumbnail())
                    .placeholder(R.drawable.nebula_placeholder_land)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    // .error(R.drawable.imagenotfound)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // log exception

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .into(holder.imageView);


            String filePath = arrayListRateChartsDetailList.get(position).getRateChartFilePath().replace("%20", "");

            final String file_Name = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length());
            String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + file_Name + "/";
            final File file = new File(pdfpath);
            final Uri path;
            if (Build.VERSION.SDK_INT >= 24) {
                path = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
            } else {
                path = Uri.fromFile(file);
            }


            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (file.exists()) {
                        if (file_Name.endsWith("pdf")) {
                            Pdffile = "application/pdf";
                            intentShareFile = new Intent(Intent.ACTION_SEND);
                            intentShareFile.setType(Pdffile);
                            intentShareFile.putExtra(Intent.EXTRA_STREAM, path);
                            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing File...");
                            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                        } else {
                            intentShareFile = new Intent(Intent.ACTION_SEND);
                            intentShareFile.setType("image");
                            intentShareFile.putExtra(Intent.EXTRA_STREAM, path);
                        }

                        context.startActivity(Intent.createChooser(intentShareFile, context.getResources().getString(R.string.share_via)));
                    } else {
                        if (file_Name.endsWith("pdf")) {
                            Toast.makeText(context, R.string.pdf_share_toast, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, R.string.image_share_toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            //final Uri path = Uri.fromFile(file);
           /* SharedPreferences walkthroughProduct = context.getSharedPreferences(PREFS_WALKTHROUGH_DOWNLOAD, 0);
            if (walkthroughProduct.getBoolean("walkthrough_first_time_share", true)) {



                callSpotView();

                holder.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (file.exists()) {
                            if (file_Name.endsWith("pdf")) {
                                Pdffile = "application/pdf";
                                intentShareFile = new Intent(Intent.ACTION_SEND);
                                intentShareFile.setType(Pdffile);
                                intentShareFile.putExtra(Intent.EXTRA_STREAM, path);
                                intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing File...");
                                intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                            } else {
                                intentShareFile = new Intent(Intent.ACTION_SEND);
                                intentShareFile.setType("image");
                                intentShareFile.putExtra(Intent.EXTRA_STREAM, path);
                            }

                            context.startActivity(Intent.createChooser(intentShareFile, context.getResources().getString(R.string.share_via)));
                        } else {
                            if (file_Name.endsWith("pdf")) {
                                Toast.makeText(context, R.string.pdf_share_toast, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, R.string.image_share_toast, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
            walkthroughProduct.edit().putBoolean("walkthrough_first_time_share", false).apply();*/


        }
        return convertView;
    }

    @Override
    public int getCount() {
        return arrayListRateChartsDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        // clear the data
        arrayListRateChartsDetailList.clear();
    }

    public void callSpotView() {
        spotLightShare = new SpotlightView.Builder((Activity) context)
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .setTypeface(FontUtil.get(context, "fonts/Montserrat-Regular.ttf"))
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(18)
                .headingTvText("Download")
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText("Download the document by clicking here")
                .maskColor(Color.parseColor("#dc000000"))
                .target(holder.imageView)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(false)
                .dismissOnBackPress(false)
                .enableDismissAfterShown(false)
                .show();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filepath = arrayListRateChartsDetailList.get(0).getRateChartFilePath();
                String path = filepath.replaceAll("%20", "");

                String fileName = path.substring(path.lastIndexOf('/') + 1, path.length());
                downloadRateCharts(0, arrayListRateChartsDetailList.get(0).getRateChartName(), fileName);
            }
        });
    }


    public void downloadRateCharts(final int position, final String rateChartName, final String rateChartsFileName) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialog.setTitle(R.string.download);
        // if (project_name.equals(Config.HOLIDAY)) {
        alertDialog.setMessage("Are you sure you want to download this " + rateChartName + "?");
       /* } else {
            alertDialog.setMessage(context.getResources().getString(R.string.ratechart_download_toast));
        }*/
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (Utils.isNetworkAvailable(context)) {
                    if (DownloadManagerResolver.resolve(context)) {
                        Uri uri = Uri.parse(arrayListRateChartsDetailList.get(position).getRateChartFilePath());
                        final DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setDescription("Download in progress").setTitle(rateChartName);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, rateChartsFileName);
                        request.setVisibleInDownloadsUi(true);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.allowScanningByMediaScanner();
                        myDownloadReference = downloadManager.enqueue(request);
                        //downloadManager.remove(myDownloadReference);

                        holder.downloadProgressView.show(myDownloadReference, new DownloadProgressView.DownloadStatusListener() {
                            @Override
                            public void downloadFailed(int reason) {

                            }

                            @Override
                            public void downloadSuccessful() {

                                callSpotViewDownload();
                            }

                            @Override
                            public void downloadCancelled() {
                                downloadManager.remove(myDownloadReference);
                            }
                        });
                    } else {
                        Toast.makeText(context, "Network not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //  alertDialog.setIcon(android.R.drawable.stat_sys_download);

        alertDialog.show();
    }


    public void callSpotViewDownload() {
        spotLightDownload = new SpotlightView.Builder((Activity) context)
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .setTypeface(FontUtil.get(context, "fonts/Montserrat-Regular.ttf"))
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(18)
                .headingTvText("Share")
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText("Share this document with others")
                .maskColor(Color.parseColor("#dc000000"))
                .target(holder.share)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(false)
                .dismissOnBackPress(false)
                .enableDismissAfterShown(false)
                .show();
    }

}
