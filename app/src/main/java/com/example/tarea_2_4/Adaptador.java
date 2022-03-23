package com.example.tarea_2_4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {
    ArrayList<firmas> firmList;


    public Adaptador (ArrayList<firmas> firms){
        this.firmList = firms;
    }

    @NonNull
    @Override
    public Adaptador.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType){

        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.activity_pruebalienzo,
                parent,
                false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.MyViewHolder holder, final int position) {

        holder.setData(firmList.get(position));
    }

    @Override
    public int getItemCount() {
        return firmList.size();
    }

    private static Bitmap getSignaturessImage(String encodedImage){
        byte[] bytes = android.util.Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewIcon;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.txtvernombre);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image);

        }

        void setData(firmas firm){
            textViewName.setText(firm.getDescripcion());
            imageViewIcon.setImageBitmap(getSignaturessImage(firm.getImagen()));
        }
    }

}
