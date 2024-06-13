package lt.mindaugas.mylibrary.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import lt.mindaugas.mylibrary.R;
import lt.mindaugas.mylibrary.models.Book;

public class BookEditor extends AppCompatActivity {
    EditText inputTitle;
    EditText inputAuthor;
    RatingBar inputStars;
    EditText inputDescription;
    Button addButton;
    Book book = new Book();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_editor);

        inputTitle = findViewById(R.id.title_input);
        inputAuthor = findViewById(R.id.author_input);
        inputStars = findViewById(R.id.stars_input);
        inputDescription = findViewById(R.id.description_input);
        addButton = findViewById(R.id.floatButtonMain);
        addButton.setTextColor(Color.WHITE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = inputTitle.getText().toString();
                String author = inputAuthor.getText().toString();
                float stars = inputStars.getRating();
                String description = inputDescription.getText().toString();

                if (title.isEmpty() || stars < 1 || author.isEmpty() || description.isEmpty()) {
                    Toast.makeText(
                            BookEditor.this,
                            "Please fill in all fields",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                book.setTitle(title);
                book.setAuthor(author);
                book.setStars((int) stars);
                book.setDescription(description);

                Intent intent = new Intent();
                intent.putExtra("book", book);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}