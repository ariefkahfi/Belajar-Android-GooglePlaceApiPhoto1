package com.kahfi.arief.belajarandroidgoogleplacephotosapi.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.R;
import com.kahfi.arief.belajarandroidgoogleplacephotosapi.model.PhotoUrl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arief on 26/09/17.
 */

public class BankAdapter  extends RecyclerView.Adapter<BankAdapter.BankHolder>{

    private List<PhotoUrl> photos ;
    private Context context;


    public BankAdapter(List<PhotoUrl> photos ){
        this.photos=photos;
    }

    public BankAdapter(Context context , List<PhotoUrl> photos){
        this.context=context;
        this.photos=photos;
    }

    @Override
    public BankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BankHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ui,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(BankHolder holder, int position) {

        PhotoUrl photo = photos.get(position);



        if(photo.getUrl()!=null){
            Glide.with(context).asDrawable()
                    .load(photo.getUrl())
                    .into(holder.imgTempat);
        }else{
            Glide.with(context)
                    .asDrawable()
                    .load(R.drawable.placeholder)
                    .into(holder.imgTempat);
        }

        holder.namaTempat.setText(photo.getNama());
        holder.alamatTempat.setText(photo.getAlamat());

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class BankHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgTempat)ImageView imgTempat;
        @BindView(R.id.namaTempat)TextView namaTempat;
        @BindView(R.id.alamatTempat)TextView alamatTempat;

        public BankHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
