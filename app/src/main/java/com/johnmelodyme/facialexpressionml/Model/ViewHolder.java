package com.johnmelodyme.facialexpressionml.Model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.johnmelodyme.facialexpressionml.R;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    private View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        // ON Items Clicked :
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : function on click
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO : function onLong click
                return false;
            }
        });
    }
    //TODO Set details to recycler view row
    public void setData(Context context, String USER_NAME, String USER_IMAGE, String ACCURACY, String DATE, String USER_EMOTION){
        //Views:
        TextView user_name =   mView.findViewById(R.id.Profile);
        TextView user_emotion = mView.findViewById(R.id.eeemotion);
        TextView accuracy = mView.findViewById(R.id.accuracy);
        TextView date = mView.findViewById(R.id.date);
        ImageView profile_pic = mView.findViewById(R.id.face);

        user_name.setText(USER_NAME);
        user_emotion.setText(USER_EMOTION);
        accuracy.setText(ACCURACY);
        date.setText(DATE);
        Picasso.get().load(USER_IMAGE).into(profile_pic);
    }
}
