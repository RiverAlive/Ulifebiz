package com.butao.ulifebiz.view.recyclerview.wrapper;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.butao.ulifebiz.R;
import com.butao.ulifebiz.view.recyclerview.utils.WrapperUtils;


/**
 * Created by zhy on 16/6/23.
 */
public class EmptyWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;
    private Drawable res;
    String text;
    public EmptyWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmpty()) {
            DateViewHolder holder= null;
            if (mEmptyView != null) {
                holder = new DateViewHolder(mEmptyView);
//                holder = ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
//            } else {
//                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isEmpty()) {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });


    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isEmpty()) {
            WrapperUtils.setFullSpan(holder);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isEmpty()) {
            setDateView((DateViewHolder) holder);
            setTextView((DateViewHolder) holder);
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (isEmpty()) return 1;
        return mInnerAdapter.getItemCount();
    }

    private void setDateView(final DateViewHolder viewHolder) {
        viewHolder.id_tool_bar_ll.setImageDrawable(res);
    }
    private void setTextView(final DateViewHolder viewHolder) {
        viewHolder.id_tool_bar_text.setText(text);
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(View emptyView, Drawable res) {
        mEmptyView = emptyView;
        this.res = res;
    }
    public void setEmptyView(View emptyView, Drawable res,String text) {
        mEmptyView = emptyView;
        this.res = res;
        this.text = text;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }

    private static class DateViewHolder extends RecyclerView.ViewHolder {
        public ImageView id_tool_bar_ll;
        public TextView id_tool_bar_text;
        public DateViewHolder(View itemView) {
            super(itemView);
            this.id_tool_bar_ll = (ImageView) itemView.findViewById(R.id.id_tool_bar_ll);
            this.id_tool_bar_text = (TextView) itemView.findViewById(R.id.id_tool_bar_text);
        }

    }
}
