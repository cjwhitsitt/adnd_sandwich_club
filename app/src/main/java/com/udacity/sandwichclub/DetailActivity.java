package com.udacity.sandwichclub;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mIngredientsImageView;
    private TextView mDescriptionTextView;
    private TextView mAlsoKnownAsTitleTextView;
    private TextView mAlsoKnownAsTextView;
    private TextView mPlaceOfOriginTitleTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mIngredientsTitleTextView;
    private TextView mIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIngredientsImageView = findViewById(R.id.image_iv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mAlsoKnownAsTitleTextView = findViewById(R.id.also_known_title_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mPlaceOfOriginTitleTextView = findViewById(R.id.origin_title_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mIngredientsTitleTextView = findViewById(R.id.ingredients_title_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        setTitle(sandwich.getMainName());
        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsImageView);

        mDescriptionTextView.setText(sandwich.getDescription());

        mAlsoKnownAsTextView.setText("");
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        showAKAViews(alsoKnownAs.size() > 0);
        for (int i = 0; i < alsoKnownAs.size(); i++) {
            mAlsoKnownAsTextView.append(alsoKnownAs.get(i));
            if (i < alsoKnownAs.size() - 1) {
                mAlsoKnownAsTextView.append("\n");
            }
        }

        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        showOriginViews(mPlaceOfOriginTextView.getText().length() > 0);

        mIngredientsTextView.setText("");
        List<String> ingredients = sandwich.getIngredients();
        showIngredientViews(ingredients.size() > 0);
        for (int i = 0; i < ingredients.size(); i++) {
            mIngredientsTextView.append(ingredients.get(i));
            if (i < ingredients.size() - 1) {
                mIngredientsTextView.append("\n");
            }
        }
    }

    private void showAKAViews(Boolean show) {
        if (show) {
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
            mAlsoKnownAsTitleTextView.setVisibility(View.VISIBLE);
        } else {
            mAlsoKnownAsTextView.setVisibility(View.GONE);
            mAlsoKnownAsTitleTextView.setVisibility(View.GONE);
        }
    }

    private void showOriginViews(Boolean show) {
        if (show) {
            mPlaceOfOriginTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginTitleTextView.setVisibility(View.VISIBLE);
        } else {
            mPlaceOfOriginTextView.setVisibility(View.GONE);
            mPlaceOfOriginTitleTextView.setVisibility(View.GONE);
        }
    }

    private void showIngredientViews(Boolean show) {
        if (show) {
            mIngredientsTextView.setVisibility(View.VISIBLE);
            mIngredientsTitleTextView.setVisibility(View.VISIBLE);
        } else {
            mIngredientsTextView.setVisibility(View.GONE);
            mIngredientsTitleTextView.setVisibility(View.GONE);
        }
    }
}
