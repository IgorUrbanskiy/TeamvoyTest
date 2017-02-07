package com.example.user.teamvoytest.model;

import com.example.user.teamvoytest.model.data.LikePhotoResult;
import com.example.user.teamvoytest.model.data.Photo;

import java.util.List;

import retrofit2.Call;

public interface Model {

	Call<List<Photo>> getPhotos(int page, int per_page, String order_by);

	Call<List<Photo>> getRandomPhotos(int count);

	Call<LikePhotoResult> likePhoto(String photoId);

	Call<LikePhotoResult> unLikePhoto(String photoId);
}
