package me.lemuel.adore.items.movie;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import me.drakeet.multitype.ItemViewProvider;
import me.lemuel.adore.MovieActivity;
import me.lemuel.adore.R;
import me.lemuel.adore.util.CommentUtil;

/**
 * Created by lemuel on 2017/2/27.
 */
public class MovieViewProvider
        extends ItemViewProvider<SubjectsBean, MovieViewProvider.ViewHolder> {

    private Activity activity;

    public MovieViewProvider(FragmentActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(root);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final SubjectsBean movie) {
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
        holder.director.setText(CommentUtil.formatDirector(movie.getDirectors()));
        Glide.with(activity).load(movie.getImages().getLarge()).asBitmap().into(holder.pic);
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, MovieActivity.class)
                        .putExtra(activity.getString(R.string.img_url), movie.getImages().getLarge()));
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView year;
        private final ImageView pic;
        private final TextView director;
        private final JCVideoPlayerStandard jcVideoPlayerStandard;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            year = (TextView) itemView.findViewById(R.id.year);
            pic = (ImageView) itemView.findViewById(R.id.movie_pic);
            director = (TextView) itemView.findViewById(R.id.director);
            jcVideoPlayerStandard = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        }
    }
}