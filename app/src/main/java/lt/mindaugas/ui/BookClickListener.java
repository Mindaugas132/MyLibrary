package lt.mindaugas.ui;

import androidx.cardview.widget.CardView;

import lt.mindaugas.models.Book;

public interface BookClickListener {

    void onClick(Book book);

    void onLongClick(Book book, CardView cardView);
}
