package concaro.sqlite.app.view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import concaro.sqlite.app.R;
import concaro.sqlite.app.model.TaskEntity;


/**
 * Created by CONCARO on 12/29/2015.
 */

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<View> headers = new ArrayList<>();
    List<View> footers = new ArrayList<>();
    public static final int TYPE_HEADER = 111;
    public static final int TYPE_FOOTER = 222;
    public static final int TYPE_ITEM = 333;

    private List<TaskEntity> items;
    private Activity context;
    private LayoutInflater mInflater;
    ITaskAdapter listener;

    public TaskAdapter(List<TaskEntity> items, ITaskAdapter listener, Activity context) {
        this.context = context;
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        if (items == null) {
            throw new NullPointerException(
                    "items must not be null");
        }
        this.items = items;
    }

    public interface ITaskAdapter {
        void onClickDelete(TaskEntity task);

        void onClickView(TaskEntity task);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.item_task,
                            parent,
                            false);
            return new TaskHolder(itemView);
        } else {
            //create a new framelayout, or inflate from a resource
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            //make sure it fills the space
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new HeaderFooterViewHolder(frameLayout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //check what type of view our position is
        if (position < headers.size()) {
            View v = headers.get(position);
            //add our view to a header view and display it
            bindHeaderFooter((HeaderFooterViewHolder) holder, v);
        } else if (position >= headers.size() + items.size()) {
            View v = footers.get(position - items.size() - headers.size());
            //add oru view to a footer view and display it
            bindHeaderFooter((HeaderFooterViewHolder) holder, v);
        } else {
            //it's one of our items, display as required
            bindHolder((TaskHolder) holder, position - headers.size());
        }
    }

    private void bindHeaderFooter(HeaderFooterViewHolder vh, View view) {
        //empty out our FrameLayout and replace with our header/footer
        vh.base.removeAllViews();
        vh.base.addView(view);
    }

    private void bindHolder(final TaskHolder holder, final int position) {
        final TaskEntity item = getItem(position);
        if (item != null) {
            holder.tvName.setText(item.getName());

            holder.lnRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickView(item);
                    }
                }
            });

            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickDelete(item);
                    }
                }
            });

            if (!TextUtils.isEmpty(item.getDateUpdated())) {
                holder.tvTime.setText(item.getDateUpdated());
            } else if (!TextUtils.isEmpty(item.getDateCreated())) {
                holder.tvTime.setText(item.getDateCreated());
            }
        }


    }


    @Override
    public int getItemCount() {
        return headers.size() + items.size() + footers.size();
    }

    public TaskEntity getItem(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    public final static class TaskHolder extends RecyclerView.ViewHolder {
        RelativeLayout lnRoot;
        TextView tvName;
        TextView tvDelete;
        TextView tvTime;

        public TaskHolder(View itemView) {
            super(itemView);
            lnRoot = (RelativeLayout) itemView.findViewById(R.id.lnRoot);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

    final static class HeaderHolder extends RecyclerView.ViewHolder {
        //@Bind(R.id.header_merchant)
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            header = (TextView) itemView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //check what type our position is, based on the assumption that the order is headers > items > footers
        if (position < headers.size()) {
            return TYPE_HEADER;
        } else if (position >= headers.size() + items.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    //add a header to the adapter
    public void addHeader(View header) {
        if (header != null && !headers.contains(header)) {
            headers.add(header);
            //animate
            notifyItemInserted(headers.size() - 1);
        }
    }

    //remove a header from the adapter
    public void removeHeader(View header) {
        if (header != null && headers.contains(header)) {
            //animate
            notifyItemRemoved(headers.indexOf(header));
            headers.remove(header);
            if (header.getParent() != null) {
                ((ViewGroup) header.getParent()).removeView(header);
            }
        }
    }

    //add a footer to the adapter
    public void addFooter(View footer) {
        if (footer != null && !footers.contains(footer)) {
            footers.add(footer);
            //animate
            notifyItemInserted(headers.size() + items.size() + footers.size() - 1);
        }
    }

    //remove a footer from the adapter
    public void removeFooter(View footer) {
        if (footer != null && footers.contains(footer)) {
            //animate
            notifyItemRemoved(headers.size() + items.size() + footers.indexOf(footer));
            footers.remove(footer);
            if (footer.getParent() != null) {
                ((ViewGroup) footer.getParent()).removeView(footer);
            }
        }
    }

    //our header/footer RecyclerView.ViewHolder is just a FrameLayout
    public static class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
        FrameLayout base;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            this.base = (FrameLayout) itemView;
        }
    }
}

