package me.lemuel.adore.items.bible;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import me.lemuel.adore.R;

/**
 * author : lemuel
 * time   : 2017/03/23
 */
public class SectionViewProvider
        extends ItemViewProvider<Section, SectionViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_setion, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Section section) {
        holder.mSectionContent.setText(section.getContent());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mSectionContent;

        ViewHolder(View itemView) {
            super(itemView);
            mSectionContent = (TextView) itemView.findViewById(R.id.section_content);
        }
    }
}