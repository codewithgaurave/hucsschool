package team.errors.hucs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import team.errors.hucs.R;
import team.errors.hucs.models.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsItem> newsList;

    // Soft/light fixed colors
    private final int[] colors = {
            R.color.softBlue,
            R.color.softGreen,
            R.color.softYellow,
            R.color.softPeach,
            R.color.softLavender
    };

    public NewsAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);

        holder.titleTextView.setText(newsItem.getTitle());
        holder.contentTextView.setText(newsItem.getContent());
        holder.dateTextView.setText(newsItem.getCreatedAt());

        // Set background color cycling
        int colorRes = colors[position % colors.length];
        holder.mainLayout.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.getContext(), colorRes)
        );

        // Fix image URL if "pages/" is missing
        if (newsItem.getImageUrl() != null && !newsItem.getImageUrl().isEmpty()) {
            String imageUrl = newsItem.getImageUrl();

            if (!imageUrl.contains("/pages/")) {
                imageUrl = imageUrl.replace("/uploads/", "/pages/uploads/");
            }

            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.splash)
                    .error(R.drawable.splash)
                    .into(holder.newsImageView);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImageView;
        TextView titleTextView, contentTextView, dateTextView;
        LinearLayout mainLayout;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.news_image);
            titleTextView = itemView.findViewById(R.id.title_text);
            contentTextView = itemView.findViewById(R.id.content_text);
            dateTextView = itemView.findViewById(R.id.date_text);
            mainLayout = itemView.findViewById(R.id.main_layout);
        }
    }

    public void updateData(List<NewsItem> newNewsList) {
        newsList.clear();
        newsList.addAll(newNewsList);
        notifyDataSetChanged();
    }
}
