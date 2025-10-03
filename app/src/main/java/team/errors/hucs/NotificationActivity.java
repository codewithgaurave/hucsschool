package team.errors.hucs;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;   // ✅ Toolbar import add
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.errors.hucs.adapter.NewsAdapter;
import team.errors.hucs.models.NewsItem;
import team.errors.hucs.models.NewsResponse;
import team.errors.hucs.network.ApiService;
import team.errors.hucs.network.RetrofitClient;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Notifications"); // Title set karo
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Back arrow enable
        }

        initializeViews();
        setupRecyclerView();
        fetchNews();

        // Setup swipe to refresh
        swipeRefreshLayout.setOnRefreshListener(this::fetchNews);
    }

    private void initializeViews() {
        newsRecyclerView = findViewById(R.id.news_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
    }

    private void setupRecyclerView() {
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(new ArrayList<>());
        newsRecyclerView.setAdapter(newsAdapter);
    }

    private void fetchNews() {
        swipeRefreshLayout.setRefreshing(true);

        ApiService apiService = RetrofitClient.getApiService();
        Call<NewsResponse> call = apiService.getNews();

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    NewsResponse newsResponse = response.body();
                    if ("success".equals(newsResponse.getStatus())) {
                        List<NewsItem> newsList = newsResponse.getData();
                        newsAdapter.updateData(newsList);
                    } else {
                        Toast.makeText(NotificationActivity.this,
                                "Failed to fetch news", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NotificationActivity.this,
                            "Failed to fetch news", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NotificationActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Back arrow action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
