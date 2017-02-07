package com.example.user.teamvoytest.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.user.teamvoytest.Constants;
import com.example.user.teamvoytest.R;
import com.example.user.teamvoytest.adapter.PhotoAdapter;
import com.example.user.teamvoytest.model.data.Photo;
import com.example.user.teamvoytest.presenter.PhotoPresenter;

import java.util.List;

;

public class MainActivity extends AppCompatActivity implements PhotoView, PhotoAdapter.OnLikeButtonClick, SwipeRefreshLayout.OnRefreshListener {

	public static final String TAG = "MainActivity";
	public static final String ORDER_KEY = "order_key";
	private String ORDER;
	private PhotoAdapter mPhotoAdapter;
	private PhotoPresenter mPhotoPresenter;
	private boolean loading;
	int mPastVisibleItems, mVisibleItemCount, mTotalItemCount;
	private SwipeRefreshLayout mSwipeRefreshLayout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState != null) {
			ORDER = savedInstanceState.getString(ORDER_KEY);
		} else {
			ORDER = Constants.ORDER_BY_LATEST;
		}
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setHasFixedSize(true);
		final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
		mRecyclerView.setLayoutManager(layoutManager);
		mPhotoAdapter = new PhotoAdapter();
		mRecyclerView.setAdapter(mPhotoAdapter);
		mPhotoPresenter = new PhotoPresenter(this);
		mPhotoPresenter.getPhotos(ORDER);

		final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				mVisibleItemCount = linearLayoutManager.getChildCount();
				mTotalItemCount = linearLayoutManager.getItemCount();
				mPastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

				if (!loading && (mVisibleItemCount + mPastVisibleItems) >= mTotalItemCount) {
					loading = true;
					mPhotoPresenter.onLoadMore(ORDER);
				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPhotoPresenter.onDestroy();
	}

	@Override
	public void showData(List<Photo> list) {
		mPhotoAdapter.setData(MainActivity.this, list);
	}

	@Override
	public void updateData(List<Photo> list) {
		for (int i = 0; i < list.size(); i++) {
			mPhotoAdapter.insertItem(list.get(i));
		}
		loading = false;
	}

	@Override
	public void updatePhoto(int position, Photo photo) {
		mPhotoAdapter.updatePhoto(position, photo);
	}

	@Override
	public void showError(String error) {
		Log.e(TAG, "Error: " + error);
	}

	@Override
	public void stopRefreshAnimation() {
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onLikeButtonClick(String photoId, int position, boolean likedByUser) {
		if (likedByUser) {
			mPhotoPresenter.unLikePhoto(photoId, position);
		} else {
			mPhotoPresenter.likePhoto(photoId, position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.toolbar_filter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_filter) {
			showPopup();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void showPopup() {
		View menuItemView = findViewById(R.id.action_filter);
		PopupMenu popup = new PopupMenu(this, menuItemView);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.popup_menu, popup.getMenu());

		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.latest:
						ORDER = Constants.ORDER_BY_LATEST;
						mPhotoPresenter.getPhotos(Constants.ORDER_BY_LATEST);
						return true;
					case R.id.popular:
						ORDER = Constants.ORDER_BY_POPULAR;
						mPhotoPresenter.getPhotos(Constants.ORDER_BY_POPULAR);
						return true;
					case R.id.oldest:
						ORDER = Constants.ORDER_BY_OLDEST;
						mPhotoPresenter.getPhotos(Constants.ORDER_BY_OLDEST);
						return true;
					case R.id.random:
						ORDER = Constants.SHOW_RANDOM_PHOTOS;
						mPhotoPresenter.getRandomPhotos();
						return true;
					default:
						return false;
				}

			}
		});
		popup.show();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(ORDER_KEY, ORDER);
	}

	@Override
	public void onRefresh() {
		if (ORDER.equals(Constants.SHOW_RANDOM_PHOTOS)) {
			mPhotoPresenter.getRandomPhotos();
		} else {
			mPhotoPresenter.getPhotos(ORDER);
		}
	}
}
