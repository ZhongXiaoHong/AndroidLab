// IBookManager.aidl
package com.example.androidlab.aidl;
import com.example.androidlab.aidl.Book;
import com.example.androidlab.aidl.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {

  List<Book> getBookList();
  void addBook(in Book book);
  void registerListener(IOnNewBookArrivedListener listener);
  void unregisterListener(IOnNewBookArrivedListener listener);


}
