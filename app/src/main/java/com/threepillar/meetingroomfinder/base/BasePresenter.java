package com.threepillar.meetingroomfinder.base;

/**
 * Created by pranav.dixit on 03/04/17.
 */

public abstract class BasePresenter<T extends BaseView> {

    private T view;

    protected void attachView(T view){
        this.view = view;
    };

    public T getView(){
        return view;
    }

    public  void detachView(){
        view = null;
    };

    public abstract void initView(String url);

    public abstract void cancelRequest();
}
