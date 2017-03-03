package me.lemuel.blisslemuel.items.movie;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.drakeet.multitype.ItemViewProvider;
import me.lemuel.blisslemuel.MovieActivity;
import me.lemuel.blisslemuel.R;
import me.lemuel.blisslemuel.util.CommentUtil;

/**
 * Created by lemuel on 2017/2/27.
 */
public class MovieViewProvider
        extends ItemViewProvider<SubjectsBean, MovieViewProvider.ViewHolder> {

    private Context context;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_movie, parent, false);
        context = parent.getContext();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SubjectsBean movie) {
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
        holder.director.setText(CommentUtil.formatDirector(movie.getDirectors()));
        Glide.with(context).load(movie.getImages().getLarge()).asBitmap().into(holder.pic);
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MovieActivity.class));
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView year;
        private final ImageView pic;
        private final TextView director;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            year = (TextView) itemView.findViewById(R.id.year);
            pic = (ImageView) itemView.findViewById(R.id.movie_pic);
            director = (TextView) itemView.findViewById(R.id.director);
        }
    }
}