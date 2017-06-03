package com.ramkt.swipeview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramkt.swipeview.SwipeView.SwipeListener;
import com.ramkt.swipeview.SwipeView.SwipeView;

/**
 * Created by Ram_Thirupathy on 5/10/2017.
 */
public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public SwipeAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SwipeAdapter.SwipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SwipeAdapter.SwipeHolder(mLayoutInflater.inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final SwipeAdapter.SwipeHolder holder, int position) {
        holder.text.setText(position + "");
        holder.swipeView.setSwipeListener(new SwipeListener() {
            @Override
            public void onSwipeStart() {

            }

            @Override
            public void onMenuOpen() {

            }

            @Override
            public void onReset() {

            }
        });
    }

    @Override
    public void onViewAttachedToWindow(SwipeHolder holder) {
        super.onViewAttachedToWindow(holder);
        // holder.rel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewDetachedFromWindow(SwipeHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.swipeView.reset();
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class SwipeHolder extends RecyclerView.ViewHolder {
        TextView undo;
        SwipeView swipeView;
        TextView text;

        public SwipeHolder(View itemView) {
            super(itemView);
            undo = (TextView) itemView.findViewById(R.id.swipe_undo);
            swipeView = (SwipeView) itemView.findViewById(R.id.swipeView);
            text = (TextView) itemView.findViewById(R.id.tv_recorded_programTime);
            undo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swipeView.reset();
                }
            });
        }
    }
}
