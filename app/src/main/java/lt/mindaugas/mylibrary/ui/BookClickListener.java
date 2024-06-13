package lt.mindaugas.mylibrary.ui;

import androidx.cardview.widget.CardView;

import lt.mindaugas.mylibrary.models.Book;

public interface BookClickListener {

    void onClick(Book book);

    void onLongClick(Book book, CardView cardView);
}
