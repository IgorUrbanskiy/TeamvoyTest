package com.example.user.teamvoytest.view;

import com.example.user.teamvoytest.model.data.Photo;

import java.util.List;

/**
 * Created by User on 05.02.2017.
 */

public interface PhotoView {

	void showData(List<Photo> list);

	void updateData(List<Photo> list);

	void updatePhoto(int position, Photo photo);

	void showError(String error);

	void stopRefreshAnimation();
}
