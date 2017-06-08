package me.lemuel.adore.provider;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.drakeet.multitype.ItemViewProvider;
import me.lemuel.adore.R;
import me.lemuel.adore.activity.MovieActivity;
import me.lemuel.adore.bean.movie.SubjectsBean;
import me.lemuel.adore.util.CommentUtil;

/**
 * Created by lemuel on 2017/2/27.
 */
public class SubjectProvider
        extends ItemViewProvider<SubjectsBean, SubjectProvider.ViewHolder> {

    private Activity activity;

    public SubjectProvider(FragmentActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final SubjectsBean movie) {
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
        holder.director.setText(CommentUtil.formatDirector(movie.getDirectors()));
        ViewGroup.LayoutParams lp = holder.pic.getLayoutParams();
        lp.height = movie.getHeight();
        holder.pic.setLayoutParams(lp);//瀑布流效果
        holder.pic.setImageURI(movie.getImages().getLarge());
        holder.pic.setOnClickListener(v -> {
            Intent i = new Intent(activity, MovieActivity.class);
            View sharedView = holder.pic;
            String transitionName = activity.getString(R.string.imageTransition);
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, sharedView, transitionName);
            activity.startActivity(i.putExtra("image", movie.getImages().getLarge()),
                    transitionActivityOptions.toBundle());
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView year;
        private SimpleDraweeView pic;
        private TextView director;
        //private JCVideoPlayerStandard jcVideoPlayerStandard;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            year = (TextView) itemView.findViewById(R.id.year);
            pic = (SimpleDraweeView) itemView.findViewById(R.id.movie_pic);
            director = (TextView) itemView.findViewById(R.id.director);
            //jcVideoPlayerStandard = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        }
    }
}