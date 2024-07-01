package lt.mindaugas.mylibrary.activities;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import lt.mindaugas.mylibrary.R;
import lt.mindaugas.mylibrary.database.MainDB;
import lt.mindaugas.mylibrary.databinding.ActivityBookDetailsBinding;
import lt.mindaugas.mylibrary.models.Book;

public class BookDetails extends AppCompatActivity {
    private ActivityBookDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int bookId = getIntent().getIntExtra("bookId", -1);
        Book book = fetchBookById(bookId);

        displayBookDetails(book);
        setTitle(book.getTitle());
    }

    private Book fetchBookById(int bookId) {
        if (bookId <= 0) return new Book();

        Book book = MainDB.getInstance(this).bookDAO().getById(bookId);

        if (book == null) {
            book = new Book();
        }
        return book;
    }

    private void displayBookDetails(Book book) {
        if (book == null) return;
        if (book.getId() > 0) {
            binding.imageViewBook.setImageIcon(Icon.createWithResource(
                    this, R.drawable.baseline_broken_image_24));
            binding.textViewTitle.setText(book.getTitle());
            binding.textViewAuthor.setText("By: ".concat(book.getAuthor()));
            binding.starsTextView.setText(String.valueOf(book.getStars()).concat(" ★"));
//            binding.starsTextView.setText(" ★".repeat(book.getStars()));
            binding.textViewDescription.setText(book.getDescription());
        }
        binding.buttonCloseBook.setOnClickListener(
                view -> finish()
        );
    }
}