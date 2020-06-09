// IOnNewBookArrivedListener.aidl
package com.example.androidlab.aidl;
import com.example.androidlab.aidl.Book;


interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
