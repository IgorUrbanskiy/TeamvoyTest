package com.example.user.teamvoytest.model;

import com.example.user.teamvoytest.model.data.LikePhotoResult;
import com.example.user.teamvoytest.model.api.ApiCategoryInterface;
import com.example.user.teamvoytest.model.api.ApiCategoryModule;
import com.example.user.teamvoytest.model.data.Photo;

import java.util.List;

import retrofit2.Call;

public class ModelImpl implements Model {

	private ApiCategoryInterface mApiCategoryInterface = ApiCategoryModule.getApiInterface();

	@Override
	public Call<List<Photo>> getPhotos(int page, int per_page, String order_by) {
		return mApiCategoryInterface.getPhotos(page, per_page, order_by);
	}

	@Override
	public Call<List<Photo>> getRandomPhotos(int count) {
		return mApiCategoryInterface.getRandomPhotos(count);
	}

	@Override
	public Call<LikePhotoResult> likePhoto(String photoId) {
		return mApiCategoryInterface.likePhoto(photoId);
	}

	@Override
	public Call<LikePhotoResult> unLikePhoto(String photoId) {
		return mApiCategoryInterface.unlikePhoto(photoId);
	}
}
