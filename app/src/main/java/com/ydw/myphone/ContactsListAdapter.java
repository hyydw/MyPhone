package com.ydw.myphone;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ydw.myphone.databinding.ListContentBinding;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/28.
 */

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {
    private MyItemClickListener mItemClickListener;
    private ArrayList<ContactsInfo> contactsInfos = new ArrayList<>();
    private ListContentBinding binding;
    private Context context;

    public ContactsListAdapter(Context context, ArrayList<ContactsInfo> list) {
        this.contactsInfos = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater
                .from(context), R.layout.list_content, parent, false);
        ViewHolder holder = new ViewHolder(binding.getRoot(), mItemClickListener);
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.contactsInfo, contactsInfos.get(position));
        holder.getBinding().setContactsInfo(contactsInfos.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return contactsInfos.size();
    }

    public void setContactList(ArrayList<ContactsInfo> mCMD) {
        this.contactsInfos = mCMD;
        notifyDataSetChanged();
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private MyItemClickListener mListener;
        private ListContentBinding binding;

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
        }

        public void setBinding(ListContentBinding binding) {
            this.binding = binding;
        }

        public ListContentBinding getBinding() {
            return this.binding;
        }

        @OnClick(R.id.left)
        public void left_onClick(){
            mListener.onItemClick(itemView, getPosition(), Edge.left);
        }

        @OnClick(R.id.right)
        public void right_onClick(){
            mListener.onItemClick(itemView, getPosition(), Edge.right);
        }
    }
}
