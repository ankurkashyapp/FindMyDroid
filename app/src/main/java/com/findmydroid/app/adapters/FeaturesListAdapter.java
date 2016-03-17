package com.findmydroid.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.findmydroid.app.R;
import com.findmydroid.app.custom_views.FmdButton;

/**
 * Created by Ankur Kashyap on 10-03-2016.
 */
public class FeaturesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_HOME_SETTINGS = 1;
    private final int TYPE_FEATURES = 2;
    private Context context;
    private int[] featuresImages;
    private String[] featuresTitles;

    private OnFeatureClickListener featureClickListener;
    private FmdButton.FmdButtonListener fmdButtonListener;

    public FeaturesListAdapter(Context context, int[] featuresImages, String[] featuresTitles, OnFeatureClickListener featureClickListener, FmdButton.FmdButtonListener fmdButtonListener) {
        this.context = context;
        this.featuresImages = featuresImages;
        this.featuresTitles = featuresTitles;
        this.featureClickListener = featureClickListener;
        this.fmdButtonListener = fmdButtonListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_HOME_SETTINGS:
                View viewHomeSettings = LayoutInflater.from(context).inflate(R.layout.item_view_home_settings, parent, false);
                return new MainSettingsViewHolder(viewHomeSettings);

            case TYPE_FEATURES:
                View viewFeature = LayoutInflater.from(context).inflate(R.layout.view_feature, parent, false);
                viewFeature.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        featureClickListener.onFeatureClick(v);
                    }
                });
                return new FeatureViewsHolder(viewFeature);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {

            case TYPE_HOME_SETTINGS: MainSettingsViewHolder settingsViewHolder = (MainSettingsViewHolder)holder;
                settingsViewHolder.deviceAdmin.setDrawableLeft(R.drawable.ic_check);
                settingsViewHolder.uninstallDefence.setDrawableLeft(R.drawable.ic_cross);
                break;

            case TYPE_FEATURES: FeatureViewsHolder featureViewsHolder = (FeatureViewsHolder)holder;
                featureViewsHolder.featureImage.setBackgroundResource(featuresImages[position - 1]);
                featureViewsHolder.featureTitle.setText(featuresTitles[position-1]);
                setFeatureBackground(featureViewsHolder.featureBackground, position);break;
        }
    }

    private void setFeatureBackground(RelativeLayout featureBackground, int position) {
        switch (position) {
            case 1:featureBackground.setBackgroundResource(R.drawable.ic_back_lock);break;
            case 2:featureBackground.setBackgroundResource(R.drawable.ic_back_calls);break;
            case 3:featureBackground.setBackgroundResource(R.drawable.ic_back_sd_card);break;
            case 4:featureBackground.setBackgroundResource(R.drawable.ic_back_ringing);break;
            case 5:featureBackground.setBackgroundResource(R.drawable.ic_back_calls);break;
            case 6:featureBackground.setBackgroundResource(R.drawable.ic_back_wifi);break;
            case 7:featureBackground.setBackgroundResource(R.drawable.ic_back_data);break;
            case 8:featureBackground.setBackgroundResource(R.drawable.ic_back_flashlight);break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return TYPE_HOME_SETTINGS;
        else
            return TYPE_FEATURES;
    }

    @Override
    public int getItemCount() {
        return featuresImages.length+1;
    }

    public class FeatureViewsHolder extends RecyclerView.ViewHolder {

        private RelativeLayout featureBackground;
        private ImageView featureImage;
        private TextView featureTitle;
        public FeatureViewsHolder(View itemView) {
            super(itemView);
            initializeViews(itemView);
        }

        private void initializeViews(View itemView) {
            featureBackground = (RelativeLayout)itemView.findViewById(R.id.feature_background);
            featureImage = (ImageView)itemView.findViewById(R.id.feature_image);
            featureTitle = (TextView)itemView.findViewById(R.id.feature_title);
        }
    }

    public class MainSettingsViewHolder extends RecyclerView.ViewHolder {

        private FmdButton deviceAdmin;
        private FmdButton uninstallDefence;
        public MainSettingsViewHolder(View itemView) {
            super(itemView);
            initializeView(itemView);
        }
        private void initializeView(View itemView) {
            deviceAdmin = (FmdButton)itemView.findViewById(R.id.device_admin);
            uninstallDefence = (FmdButton)itemView.findViewById(R.id.uninstall_defence);
            deviceAdmin.setText("Device Admin");
            uninstallDefence.setText("Uninstall Defence");
            deviceAdmin.setClickListener(fmdButtonListener);
            uninstallDefence.setClickListener(fmdButtonListener);
        }
    }

    public interface OnFeatureClickListener {
        void onFeatureClick(View view);
    }

}
