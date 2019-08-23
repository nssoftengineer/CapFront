package com.capfront.assignment.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.capfront.assignment.R;
import com.capfront.assignment.models.Content;
import com.capfront.assignment.models.Product;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by neeraj on 10/01/2019.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LIST = 1;
    private static final int BANNER = 2;
    private static final int IMAGE = 3;
    private ArrayList<Content> sectionDataModels;
    private Context mContext;

    public MainAdapter(Context context) {
        this.sectionDataModels = new ArrayList<>();
        this.mContext = context;
    }

    //update latest data from observer
    public void setData(ArrayList<Content> sectionDataModels) {
        this.sectionDataModels = sectionDataModels;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case LIST:
                View v = inflater.inflate(R.layout.list_item, viewGroup, false);
                viewHolder = new ItemRowHolder(v);
                break;
            case IMAGE:
                View v2 = inflater.inflate(R.layout.banner, viewGroup, false);
                viewHolder = new ItemRowHolderNew(v2);
                break;
            case BANNER:
                View v3 = inflater.inflate(R.layout.single_banner, viewGroup, false);
                viewHolder = new ItemRowHolderSlider(v3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case LIST:
                ItemRowHolder vh1 = (ItemRowHolder) viewHolder;
                configureItemRowHolder(vh1, i);
                break;

            case IMAGE:
                ItemRowHolderNew vh2 = (ItemRowHolderNew) viewHolder;
                configureItemRowHolderNew(vh2, i);
                break;
            case BANNER:
                ItemRowHolderSlider vh3 = (ItemRowHolderSlider) viewHolder;
                configureItemRowHolderSlider(vh3, i);
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = LIST;
        if (sectionDataModels.get(position).getSectionType().equalsIgnoreCase("horizontalFreeScroll")) {
            type = LIST;
        } else if (sectionDataModels.get(position).getSectionType().equalsIgnoreCase("banner")) {
            type = IMAGE;
        } else if (sectionDataModels.get(position).getSectionType().equalsIgnoreCase("splitBanner")) {
            type = BANNER;
        }

        return type;
    }

    @Override
    public int getItemCount() {
        return (null != sectionDataModels ? sectionDataModels.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected RecyclerView recyclerView;


        public ItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);


        }

    }

    public class ItemRowHolderSlider extends RecyclerView.ViewHolder {

        //protected ImageView banner;
        protected SliderLayout imageSlider;


        public ItemRowHolderSlider(View view) {
            super(view);

            // this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            //this.banner = (ImageView) view.findViewById(R.id.banner);
            this.imageSlider = (SliderLayout) view.findViewById(R.id.imageSlider);
            //this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            //this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);


        }

    }

    public class ItemRowHolderNew extends RecyclerView.ViewHolder {

        protected ImageView banner;
        //protected SliderLayout imageSlider;


        public ItemRowHolderNew(View view) {
            super(view);

            // this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.banner = (ImageView) view.findViewById(R.id.banner);
            //this.imageSlider = (SliderLayout) view.findViewById(R.id.imageSlider);
            //this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            //this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);


        }

    }

    private void configureItemRowHolder(ItemRowHolder itemRowHolder, int i) {
        final String sectionName = sectionDataModels.get(i).getName();

        if (sectionDataModels.get(i).getProducts() != null) {
            List<Product> singleSectionItems = sectionDataModels.get(i).getProducts();

            itemRowHolder.tvTitle.setText(sectionName);

            ItemAdapter itemListDataAdapter = new ItemAdapter(mContext, singleSectionItems);

            itemRowHolder.recyclerView.setHasFixedSize(true);
            itemRowHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemRowHolder.recyclerView.setAdapter(itemListDataAdapter);


            itemRowHolder.recyclerView.setNestedScrollingEnabled(false);
        }
    }

    private void configureItemRowHolderNew(ItemRowHolderNew itemRowHolder, int i) {
        if (sectionDataModels.get(i).getSectionType().equalsIgnoreCase("banner")) {
            Glide.with(mContext)
                    .load(sectionDataModels.get(i).getBannerImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background)
                    .into(itemRowHolder.banner);
        }

    }

    private void configureItemRowHolderSlider(ItemRowHolderSlider itemRowHolder, int i) {

        if (sectionDataModels.get(i).getSectionType().equalsIgnoreCase("splitBanner")) {
            itemRowHolder.imageSlider.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            itemRowHolder.imageSlider.setScrollTimeInSec(1); //set scroll delay in seconds :
            for (int j = 0; j < 2; j++) {

                SliderView sliderView = new SliderView(mContext);

                switch (i) {
                    case 0:
                        sliderView.setImageUrl(sectionDataModels.get(i).getFirstImage());
                        break;
                    case 1:
                        sliderView.setImageUrl(sectionDataModels.get(i).getSecondImage());
                        break;
                }

                itemRowHolder.imageSlider.addSliderView(sliderView);
            }


        }
    }
}