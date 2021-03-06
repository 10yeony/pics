package com.devils.pics.dao;

import java.util.HashMap;
import java.util.List;

import com.devils.pics.domain.Bookmark;
import com.devils.pics.domain.Review;
import com.devils.pics.domain.Studio;

public interface ExtraDAO {
	int addBookmark(Bookmark bookmark) throws Exception;
	List<Studio> getBookmark(int custId) throws Exception;
	Bookmark getBookId(HashMap<String, Integer> ids) throws Exception;
	int deleteBookmark(int bookId) throws Exception;
	int writeReview(Review review) throws Exception;
	List<Review> getCustomerReivews(int custId) throws Exception;
	int checkReviews(int resId) throws Exception;
	int deleteReview(int reviewId) throws Exception;
	List<Studio> getBookmarkList(int custId) throws Exception;
}
