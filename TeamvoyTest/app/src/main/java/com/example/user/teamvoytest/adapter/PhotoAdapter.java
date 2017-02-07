package com.example.user.teamvoytest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.teamvoytest.R;
import com.example.user.teamvoytest.model.data.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 05.02.2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

	private List<Photo> mPhotoList = new ArrayList<>();
	private Context mContext;
	private OnLikeButtonClick mOnLikeButtonClick;

	public interface OnLikeButtonClick {
		void onLikeButtonClick(String photoId, int position, boolean likedByUser);
	}

	public void setData(Context context, List<Photo> photoList) {
		mOnLikeButtonClick = (OnLikeButtonClick) context;
		mContext = context;
		mPhotoList = photoList;
		notifyDataSetChanged();
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);

		MyViewHolder myViewHolder = new MyViewHolder(view);
		return myViewHolder;
	}

	public void insertItem(Photo item) {
		if (item.width != 0 && item.height != 0) {
			mPhotoList.add(item);
			notifyItemInserted(mPhotoList.size() - 1);
		}
	}

	public void updatePhoto(int position, Photo photo) {
		mPhotoList.set(position, photo);
		notifyItemChanged(position);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		final Photo photo = mPhotoList.get(position);
		Glide.with(holder.mImage.getContext()).load(photo.links.download).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.mImage);
		holder.mTitle.setText(photo.user.name + ", " + photo.created_at.split("T")[0]);
		initLikeButton(photo, holder, position);
	}

	private void initLikeButton(final Photo photo, MyViewHolder holder, final int position) {
		if (photo.likes > 0) {
			holder.mButtonLikeWithCount.setText(String.valueOf(photo.likes));
			holder.mButtonLike.setVisibility(View.GONE);
			holder.mButtonLikeWithCount.setVisibility(View.VISIBLE);
			holder.mButtonLikeWithCount.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnLikeButtonClick.onLikeButtonClick(photo.id, position, photo.liked_by_user);
				}
			});
			if (photo.liked_by_user) {
				holder.mButtonLikeWithCount.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_red_button));
				holder.mButtonLikeWithCount.setTextColor(Color.WHITE);
				holder.mButtonLikeWithCount.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_favorite_white, 0, 0, 0);
			} else {
				holder.mButtonLikeWithCount.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_white_button));
				holder.mButtonLikeWithCount.setTextColor(Color.GRAY);
				holder.mButtonLikeWithCount.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_favorite_red, 0, 0, 0);
			}
		} else {
			holder.mButtonLike.setVisibility(View.VISIBLE);
			holder.mButtonLikeWithCount.setVisibility(View.GONE);
			holder.mButtonLike.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnLikeButtonClick.onLikeButtonClick(photo.id, position, photo.liked_by_user);
				}
			});
		}
	}

	@Override
	public void onViewRecycled(MyViewHolder holder) {
		super.onViewRecycled(holder);
		Glide.clear(holder.mImage);
	}

	@Override
	public int getItemCount() {
		return mPhotoList.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		private ImageView mImage;
		private Button mButtonLike;
		private Button mButtonLikeWithCount;
		private TextView mTitle;

		public MyViewHolder(View itemView) {
			super(itemView);
			mImage = (ImageView) itemView.findViewById(R.id.item_photo_image);
			mButtonLike = (Button) itemView.findViewById(R.id.item_photo_like_empty);
			mButtonLikeWithCount = (Button) itemView.findViewById(R.id.item_photo_like);
			mTitle = (TextView) itemView.findViewById(R.id.item_photo_title);
		}
	}
}
