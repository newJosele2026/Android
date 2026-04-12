package com.slt.dependenciajudicial.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.slt.dependenciajudicial.R;
import com.slt.dependenciajudicial.requests.models.ChatModel;
import com.slt.dependenciajudicial.utils.DependenciaJudicialUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nelsy Acuña on 02/02/2018.
 */

public class RVAdapterChat  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_IS_ME = 1;

    private static final int TYPE_IS_OUT = 2;

    private List<ChatModel> chatModelsList;

    private Context mContext;

    public RVAdapterChat(Context context, List<ChatModel> chatModelsList) {
        this.chatModelsList = chatModelsList;
        this.mContext = context;

    }

    public class ChatViewHolderMe extends RecyclerView.ViewHolder {

        @BindView(R.id.item_chat_me_str_msm)
        TextView lblMsmMe;

        @BindView(R.id.item_chat_me_str_date)
        TextView lblFechaMe;

        public ChatViewHolderMe(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public class ChatViewHolderOut extends RecyclerView.ViewHolder {

        @BindView(R.id.item_chat_out_str_msm)
        TextView lblMsmOut;

        @BindView(R.id.item_chat_out_str_date)
        TextView lblFechaOut;

        public ChatViewHolderOut(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void updateRVAdapterChat(List<ChatModel> viewModels) {
        this.chatModelsList = viewModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        ChatModel chatModel = chatModelsList.get(position);
        if(chatModel.getMe()){
            return TYPE_IS_ME;
        }else{
            return TYPE_IS_OUT;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {


        View itemMe = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_me, parent, false);


        View itemOut = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_out, parent, false);

        if(position == TYPE_IS_ME){

            return new ChatViewHolderMe (itemMe);

        }else{

            return new ChatViewHolderOut(itemOut);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

         ChatModel chatModel = chatModelsList.get(position);


        switch(holder.getItemViewType()){

            case TYPE_IS_ME :

                ChatViewHolderMe chatViewHolderMe = (ChatViewHolderMe )holder;

                chatViewHolderMe.lblMsmMe.setText(chatModel.gettMensaje());
                chatViewHolderMe.lblFechaMe.setText(DependenciaJudicialUtils.dateDiffFormater(chatModel.getDtFechaMensaje()));
                break;

            case TYPE_IS_OUT:

                ChatViewHolderOut chatViewHolderOut = (ChatViewHolderOut)holder;

                chatViewHolderOut.lblMsmOut.setText(chatModel.gettMensaje());
                chatViewHolderOut.lblFechaOut.setText(DependenciaJudicialUtils.dateDiffFormater(chatModel.getDtFechaMensaje()));

                break;

        }

    }

    @Override
    public int getItemCount() {
        return chatModelsList.size();
    }

}
