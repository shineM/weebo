package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.model.SearchModel;
import com.danlvse.weebo.model.imp.SearchModelImp;
import com.danlvse.weebo.presenter.SearchPresenter;
import com.danlvse.weebo.ui.view.SearchView;

import java.util.List;

/**
 * Created by zxy on 16/6/16.
 */
public class SearchPresenterImp implements SearchPresenter {
    private SearchView searchView;
    private SearchModel searchModel;

    public SearchPresenterImp(SearchView searchView) {
        this.searchView = searchView;
        this.searchModel = new SearchModelImp();
    }

    private SearchModel.OnSearchCompleted listener = new SearchModel.OnSearchCompleted() {
        @Override
        public void onSuccess(List<Weibo> list) {
            searchView.hideLoadingIcon();
            searchView.updateResultList(list);
        }

        @Override
        public void onError(String error) {
            searchView.hideLoadingIcon();
            searchView.showErrorInfo();
        }

        @Override
        public void onEmpty() {
            searchView.hideLoadingIcon();
            searchView.showEmptyInfo();
        }
    };
    @Override
    public void searchWeiboByKey(String key, Context context) {
        searchModel.searchWeibo(key,context,listener);
    }

    @Override
    public void searchUserByKey(String key, Context context) {

    }

    @Override
    public void searchTopicByKey(String key, Context context) {

    }
}
