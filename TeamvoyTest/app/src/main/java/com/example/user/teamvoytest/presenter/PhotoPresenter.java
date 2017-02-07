package com.example.user.teamvoytest.presenter;

import com.example.user.teamvoytest.Constants;
import com.example.user.teamvoytest.model.Model;
import com.example.user.teamvoytest.model.ModelImpl;
import com.example.user.teamvoytest.model.data.LikePhotoResult;
import com.example.user.teamvoytest.model.data.Photo;
import com.example.user.teamvoytest.view.PhotoView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 05.02.2017.
 */

public class PhotoPresenter implements Presenter {
	private Model model;
	private PhotoView mPhotoView;
	private int page = 1;
	private List<Photo> mPhotoList = new ArrayList<>();

	public PhotoPresenter(PhotoView photoView) {
		mPhotoView = photoView;
		model = new ModelImpl();
	}
	@Override
	public void getPhotos(String sort) {
		model.getPhotos(1, Constants.PHOTO_PER_PAGE, sort).enqueue(new Callback<List<Photo>>() {
			@Override
			public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
				mPhotoView.stopRefreshAnimation();
				mPhotoList.clear();
				mPhotoView.showData(response.body());
				mPhotoList.addAll(response.body());
			}

			@Override
			public void onFailure(Call<List<Photo>> call, Throwable t) {
				mPhotoView.stopRefreshAnimation();
				mPhotoView.showError(t.getMessage());
			}
		});
	}

	@Override
	public void getRandomPhotos() {
		model.getRandomPhotos(Constants.PHOTO_PER_PAGE).enqueue(new Callback<List<Photo>>() {
			@Override
			public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
				mPhotoView.stopRefreshAnimation();
				mPhotoList.clear();
				mPhotoView.showData(response.body());
				mPhotoList.addAll(response.body());
			}
			@Override
			public void onFailure(Call<List<Photo>> call, Throwable t) {
				mPhotoView.stopRefreshAnimation();
			}
		});
	}

	@Override
	public void likePhoto(final String photoId, final int position) {
		model.likePhoto(photoId).enqueue(new Callback<LikePhotoResult>() {
			@Override
			public void onResponse(Call<LikePhotoResult> call, Response<LikePhotoResult> response) {
				Photo photo = mPhotoList.get(position);
				photo.likes = response.body().photo.likes;
				photo.liked_by_user = response.body().photo.liked_by_user;
				mPhotoView.updatePhoto(position, photo);
			}

			@Override
			public void onFailure(Call<LikePhotoResult> call, Throwable t) {

			}
		});
	}

	@Override
	public void unLikePhoto(final String photoId, final int position) {
		model.unLikePhoto(photoId).enqueue(new Callback<LikePhotoResult>() {
			@Override
			public void onResponse(Call<LikePhotoResult> call, Response<LikePhotoResult> response) {
				Photo photo = mPhotoList.get(position);
				photo.likes = response.body().photo.likes;
				photo.liked_by_user = response.body().photo.liked_by_user;
				mPhotoView.updatePhoto(position, photo);
			}

			@Override
			public void onFailure(Call<LikePhotoResult> call, Throwable t) {

			}
		});
	}

	@Override
	public void onLoadMore(String sort) {
		model.getPhotos(++page, Constants.PHOTO_PER_PAGE, sort).enqueue(new Callback<List<Photo>>() {
			@Override
			public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
				mPhotoView.updateData(response.body());
				mPhotoList.addAll(response.body());
			}

			@Override
			public void onFailure(Call<List<Photo>> call, Throwable t) {

			}
		});
	}

	@Override
	public void onDestroy() {
		if (mPhotoView != null) {
			mPhotoView = null;
		}
	}
}
