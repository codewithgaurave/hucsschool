package team.errors.hucs.network;

import retrofit2.Call;
import retrofit2.http.GET;
import team.errors.hucs.models.NewsResponse;

public interface ApiService {
    @GET("news_get.php")
    Call<NewsResponse> getNews();
}