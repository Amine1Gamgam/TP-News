package com.example.tnews;


import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;

public class Details extends AppCompatActivity {

    private String newsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_details);

        // Références aux vues
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView imageView = findViewById(R.id.imageView);

        // Récupération des données transmises par l'Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");
        String linkUrl = intent.getStringExtra("linkUrl");

        // Mise à jour des vues avec les données de l'article
        if (title != null) {
            titleTextView.setText(title);
        } else {
            titleTextView.setText(R.string.default_title);
        }

        if (description != null) {
            descriptionTextView.setText(description);
        } else {
            descriptionTextView.setText(R.string.default_description);
        }

        if (imageUrl != null) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.not_available) // Image par défaut si l'URL est vide
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.not_available);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, newsUrl);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}