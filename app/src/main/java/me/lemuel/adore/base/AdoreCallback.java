package me.lemuel.adore.base;

public interface AdoreCallback<T>  {
    void onSuccess(T t);
    void onError(Throwable t);
}
