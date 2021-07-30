package com.deep.app.abstraction.abstraction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Puneet Gupta
 * @version 1.0
 * @since on 23/01/17
 */

public abstract class AdsRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 2;
    private static final int AD_TYPE = 1;
    public List<T> list = new ArrayList<>();
    public Context mContext;
    public String brands;
    public AdsParameter adsParameter;
   /* private Map<Integer, AdsViewModel.AdItem> adsMap = new TreeMap<>();
    private SparseArray<PublisherAdView> adsViewCache = new SparseArray<>();*/

    protected AdsRecyclerAdapter(Context context, List<T> list, AdsParameter adsParameter) {
        this.mContext = context;
        this.list = list;
        this.adsParameter = adsParameter;
       /* if(null != adsParameter)
        {
            adsMap.putAll(AdsFactory.getInstance().getAdsMap(context, adsParameter.getScreenName()));
        }*/
    }

    @Override
    public int getItemViewType(int position) {
       /* if(isPositionForAd(position))
        {
            return AD_TYPE;
        }*/

        return TYPE_ITEM;
    }

   /* private boolean isPositionForAd(int position)
    {
        return adsMap.containsKey(position);
    }*/

   /* private AdsViewModel.AdItem getAdForPosition(int position)
    {
        if(adsMap.containsKey(position))
        {
            return adsMap.get(position);
        }
        throw new InvalidParameterException("No ad Exist for position " + position);
    }*/

   /* private void removeAdItemWhenNotLoaded(int position)
    {
        adsMap.remove(position);
        notifyDataSetChanged();
    }*/

    /*private int getAdditionalCount(int size)
    {
        int adAdded = 0;
        for(int itemPosition : adsMap.keySet())
        {
            if(size >= itemPosition)
            {
                adAdded++;
            }
        }

        return adAdded;
    }
*/
    @Override
    public int getItemCount() {
        /*if(list == null)
        {
            return 0;
        }
        int additionalContent;
        additionalContent = getAdditionalCount(list.size());
        return list.size() + additionalContent;*/
        return list.size();
    }

    /*protected int getRealPosition(int position)
    {
        return position - getAdditionalCount(position);
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* if(viewType == AD_TYPE)
        {
            return new AdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adview_list_item, parent, false));
        }*/
        return onCreateViewHolderOverride(parent, viewType);
    }

    protected abstract RecyclerView.ViewHolder onCreateViewHolderOverride(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       /* if(getItemViewType(holder.getAdapterPosition()) == AD_TYPE)
        {
            AdViewHolder adViewHolder = (AdViewHolder) holder;
            AdsViewModel.AdItem adModel = getAdForPosition(holder.getAdapterPosition());
            if(!TextUtils.isEmpty(adModel.getKey()))
            {
                if(!TextUtils.isEmpty(brands))
                {
                    adsParameter.brandName = brands;
                }
                LogUtil.log(LogUtil.DEBUG, "ADS :" + adsParameter.getBrandName() + " " + adsParameter.getModelName());
                PublisherAdView publisherAdView = AdUtil.makeAdRequest(adViewHolder.adView, mContext.getApplicationContext(),
                                                                       adsParameter.getScreenName(),
                                                                       adModel, adsParameter.getBrandName(), adsParameter.getModelName(),
                                                                       adsParameter.getBodyType(), adsParameter.getPriceRange(),
                                                                       new CustomAdListener(new WeakReference<AdsRecyclerAdapter>(this)),
                                                                       adsViewCache.get(holder.getAdapterPosition()));
                adsViewCache.put(holder.getAdapterPosition(), publisherAdView);
            }
            else
            {
                adViewHolder.adView.setVisibility(View.GONE);
            }
        }
        else
        {*/
        onBindViewHolderOverride(holder, holder.getAdapterPosition());
        // }
    }

    public abstract void onBindViewHolderOverride(RecyclerView.ViewHolder holder, int position);

    private static class AdViewHolder extends RecyclerView.ViewHolder {
        View adView;

        AdViewHolder(View itemView) {
            super(itemView);
            adView = itemView;
        }
    }

    /*private static class CustomAdListener implements AdCustomListener
    {
        private WeakReference<AdsRecyclerAdapter> adapter;

        public CustomAdListener(WeakReference<AdsRecyclerAdapter> adapter)
        {
            this.adapter = adapter;
        }

        @Override
        public void onAdFailedToLoad(int position)
        {
            if(adapter != null && adapter.get() != null)
            {
                adapter.get().removeAdItemWhenNotLoaded(position);
            }
        }
    }
*/
    public static class AdsParameter {
        private String screenName;
        private String brandName;
        private String modelName;
        private String priceRange;
        private String bodyType;

        private AdsParameter(String screenName, String brandName, String modelName, String priceRange, String bodyType) {
            this.screenName = screenName;
            this.brandName = brandName;
            this.modelName = modelName;
            this.priceRange = priceRange;
            this.bodyType = bodyType;
        }

        private String getScreenName() {
            return screenName;
        }

        private String getBrandName() {
            return !TextUtils.isEmpty(brandName) ? brandName : "";
        }

        private String getModelName() {
            return !TextUtils.isEmpty(modelName) ? modelName : "";
        }

        private String getPriceRange() {
            return !TextUtils.isEmpty(priceRange) ? priceRange : "";
        }

        private String getBodyType() {
            return !TextUtils.isEmpty(bodyType) ? bodyType : "";
        }

        public static class Builder {
            private String screenName;
            private String brandName;
            private String modelName;
            private String priceRange;
            private String bodyType;

            public Builder(String screenName) {
                this.screenName = screenName;
            }

            public Builder withBrandName(String brandName) {
                this.brandName = brandName;
                return this;
            }

            public Builder withScreenName(String screenName) {
                this.screenName = screenName;
                return this;
            }

            public Builder withModelName(String modelName) {
                this.modelName = modelName;
                return this;
            }

            public Builder withPriceRange(String priceRange) {
                this.priceRange = priceRange;
                return this;
            }

            public Builder withBodyType(String bodyType) {
                this.bodyType = bodyType;
                return this;
            }

            public AdsParameter build() {
                return new AdsParameter(screenName, brandName, modelName, priceRange, bodyType);
            }
        }
    }
}



