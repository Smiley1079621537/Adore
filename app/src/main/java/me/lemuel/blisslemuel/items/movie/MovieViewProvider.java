package me.lemuel.blisslemuel.items.movie;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import me.lemuel.blisslemuel.R;

/**
 * Created by lemuel on 2017/2/27.
 */
public class MovieViewProvider
        extends ItemViewProvider<Movie.SubjectsBean, MovieViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Movie.SubjectsBean movie) {
        holder.title.setText(movie.getTitle());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}