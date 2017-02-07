package com.example.user.teamvoytest.presenter;

/**
 * Created by User on 05.02.2017.
 */

public interface Presenter {

	void getPhotos(String sort);

	void getRandomPhotos();

	void likePhoto(String photoId, int position);

	void unLikePhoto(String photoId, int position);

	void onLoadMore(String sort);

	void onDestroy();
}
