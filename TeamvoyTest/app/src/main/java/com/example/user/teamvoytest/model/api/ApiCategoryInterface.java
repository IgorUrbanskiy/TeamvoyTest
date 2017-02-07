package com.example.user.teamvoytest.model.api;

import com.example.user.teamvoytest.Constants;
import com.example.user.teamvoytest.model.data.LikePhotoResult;
import com.example.user.teamvoytest.model.data.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 04.02.2017.
 */

public interface ApiCategoryInterface {

	@Headers(Constants.API_HEADRES)
	@GET("photos")
	Call<List<Photo>> getPhotos(@Query("page") int page,
								@Query("per_page") int per_page,
								@Query("order_by") String order_by);

	@Headers(Constants.API_HEADRES)
	@GET("photos/random")
	Call<List<Photo>> getRandomPhotos(@Query("count") int count);

	@Headers(Constants.API_HEADRES)
	@POST("photos/{id}/like")
	Call<LikePhotoResult> likePhoto(@Path("id") String id);

	@Headers(Constants.API_HEADRES)
	@DELETE("photos/{id}/like")
	Call<LikePhotoResult> unlikePhoto(@Path("id") String id);
}
