package com.example.androidlab.aidl;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final String TAG = "BMS";

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    //TODO   这个Book.java所在的路径 在gradle应该设置成为源码目录之一
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList
            = new CopyOnWriteArrayList<IOnNewBookArrivedListener>();



    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (! mListenerList.contains(listener)) {
                mListenerList.add(listener);
            } else {
                Log.d(TAG, "already exists.");
            }

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

            if (mListenerList.contains(listener)) {
                mListenerList.remove(listener);
                Log.d(TAG, "unregister listener succeed.");
            } else {
                Log.d(TAG, "not found, can not unregister.");
            }
            Log.d(TAG, "unregisterListener, current size:" + mListenerList.
                    size());


        }

    };



    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Ios"));

        new Thread(new ServiceWorker()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        Log.d(TAG, "onNewBookArrived, notify listeners:" + mListenerList.
                size());
        for (int i = 0; i < mListenerList.size(); i++) {
            IOnNewBookArrivedListener listener = mListenerList.get(i);
            Log.d(TAG, "onNewBookArrived, notify listener:" + listener);
            listener.onNewBookArrived(book);
        }
    }


    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            // do background processing here.....
            while (! mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

