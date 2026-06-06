package com.moodtrip.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity {
    private static final int PRIMARY = Color.rgb(18, 108, 98);
    private static final int PRIMARY_DARK = Color.rgb(12, 76, 69);
    private static final int ACCENT = Color.rgb(230, 90, 61);
    private static final int BG = Color.rgb(246, 243, 238);
    private static final int CARD = Color.WHITE;
    private static final int TEXT = Color.rgb(32, 43, 40);
    private static final int MUTED = Color.rgb(98, 111, 106);

    private final List<TravelSpot> allSpots = SpotRepository.all();
    private final List<TravelSpot> visibleSpots = new ArrayList<>();
    private LinearLayout resultList;
    private TextView resultCount;
    private MoodMapView moodMapView;
    private EditText searchInput;
    private String activeTag = "";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("moodtrip", MODE_PRIVATE);
        setContentView(buildContent());
        applyFilters();
    }

    private View buildContent() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(BG);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(18), dp(18), dp(26));
        scrollView.addView(root, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        root.addView(header());
        root.addView(searchBox());
        root.addView(tagScroller());

        moodMapView = new MoodMapView(this);
        LinearLayout.LayoutParams mapParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(220)
        );
        mapParams.setMargins(0, dp(14), 0, dp(18));
        root.addView(moodMapView, mapParams);

        resultCount = text("Rekomendasi", 20, true, TEXT);
        root.addView(resultCount);

        resultList = new LinearLayout(this);
        resultList.setOrientation(LinearLayout.VERTICAL);
        root.addView(resultList);

        root.addView(sectionTitle("Prioritas MVP"));
        root.addView(moscowSummary());
        return scrollView;
    }

    private View header() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(18), dp(18), dp(18), dp(18));
        box.setBackground(round(PRIMARY_DARK, dp(20)));

        TextView eyebrow = text("SMART TOURISM MVP", 12, true, Color.rgb(200, 230, 221));
        TextView title = text("MoodTrip", 34, true, Color.WHITE);
        TextView subtitle = text(
                "Temukan wisata, kuliner, dan landmark Mataram/Lombok berdasarkan mood, tag, budget, dan keunikan lokal.",
                15,
                false,
                Color.rgb(235, 246, 242)
        );
        subtitle.setPadding(0, dp(8), 0, 0);

        box.addView(eyebrow);
        box.addView(title);
        box.addView(subtitle);
        return box;
    }

    private View searchBox() {
        searchInput = new EditText(this);
        searchInput.setSingleLine(false);
        searchInput.setMinLines(1);
        searchInput.setHint("Cari Lombok: healing, makanan pedas, Senggigi, hidden gem...");
        searchInput.setTextSize(15);
        searchInput.setTextColor(TEXT);
        searchInput.setHintTextColor(MUTED);
        searchInput.setPadding(dp(16), dp(12), dp(16), dp(12));
        searchInput.setBackground(round(CARD, dp(16), Color.rgb(226, 221, 212)));
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(16), 0, dp(10));
        searchInput.setLayoutParams(params);
        return searchInput;
    }

    private View tagScroller() {
        HorizontalScrollView scroll = new HorizontalScrollView(this);
        scroll.setHorizontalScrollBarEnabled(false);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        scroll.addView(row);

        row.addView(chip("Semua", ""));
        for (String tag : SpotRepository.moodTags()) {
            row.addView(chip(tag, tag));
        }
        return scroll;
    }

    private Button chip(String label, String tag) {
        Button button = new Button(this);
        button.setAllCaps(false);
        button.setText(label);
        button.setTextSize(13);
        button.setTextColor(TEXT);
        button.setPadding(dp(12), 0, dp(12), 0);
        button.setMinHeight(dp(40));
        button.setMinimumHeight(dp(40));
        button.setBackground(round(CARD, dp(18), Color.rgb(222, 216, 205)));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                dp(42)
        );
        params.setMargins(0, 0, dp(8), 0);
        button.setLayoutParams(params);
        button.setOnClickListener(v -> {
            activeTag = tag;
            applyFilters();
        });
        return button;
    }

    private View spotCard(TravelSpot spot) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(14), dp(16), dp(14));
        card.setBackground(round(CARD, dp(14), Color.rgb(231, 226, 216)));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, dp(12), 0, 0);
        card.setLayoutParams(cardParams);

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setOrientation(LinearLayout.HORIZONTAL);

        TextView name = text(spot.name, 18, true, TEXT);
        top.addView(name, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        TextView rating = text("Rating " + spot.rating, 13, true, ACCENT);
        top.addView(rating);
        card.addView(top);

        TextView category = text(spot.category + " • " + spot.location, 13, false, MUTED);
        category.setPadding(0, dp(3), 0, dp(8));
        card.addView(category);

        TextView unique = text(spot.uniqueness, 14, false, TEXT);
        card.addView(unique);

        TextView tags = text("Tag: " + String.join(", ", spot.tags), 12, false, MUTED);
        tags.setPadding(0, dp(9), 0, dp(10));
        card.addView(tags);

        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        Button detail = actionButton("Detail", PRIMARY);
        detail.setOnClickListener(v -> showDetail(spot));
        Button save = actionButton(isSaved(spot.id) ? "Tersimpan" : "Simpan", ACCENT);
        save.setOnClickListener(v -> {
            toggleSaved(spot.id);
            applyFilters();
        });
        actions.addView(detail, new LinearLayout.LayoutParams(0, dp(42), 1));
        LinearLayout.LayoutParams saveParams = new LinearLayout.LayoutParams(0, dp(42), 1);
        saveParams.setMargins(dp(10), 0, 0, 0);
        actions.addView(save, saveParams);
        card.addView(actions);
        return card;
    }

    private Button actionButton(String label, int color) {
        Button button = new Button(this);
        button.setAllCaps(false);
        button.setText(label);
        button.setTextColor(Color.WHITE);
        button.setTextSize(14);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setBackground(round(color, dp(12)));
        return button;
    }

    private View moscowSummary() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(16), dp(14), dp(16), dp(14));
        box.setBackground(round(Color.WHITE, dp(14), Color.rgb(231, 226, 216)));

        box.addView(text("Must Have: map, data lokasi, detail lokasi, sistem tag, pencarian mood/tag, filter dasar.", 14, false, TEXT));
        box.addView(space(8));
        box.addView(text("Should Have: wishlist, rating, review, rekomendasi terdekat, kategori tempat.", 14, false, TEXT));
        box.addView(space(8));
        box.addView(text("Could Have: itinerary sederhana, badge eksplorasi, kontribusi foto, rekomendasi riwayat.", 14, false, TEXT));
        box.addView(space(8));
        box.addView(text("Won't Have: booking, payment, subscription, chatbot kompleks, integrasi transportasi.", 14, false, TEXT));
        return box;
    }

    private TextView sectionTitle(String label) {
        TextView title = text(label, 20, true, TEXT);
        title.setPadding(0, dp(22), 0, dp(10));
        return title;
    }

    private void applyFilters() {
        String query = searchInput == null ? "" : searchInput.getText().toString();
        visibleSpots.clear();
        for (TravelSpot spot : allSpots) {
            if (spot.matches(query, activeTag)) {
                visibleSpots.add(spot);
            }
        }
        renderResults();
    }

    private void renderResults() {
        if (resultList == null || resultCount == null || moodMapView == null) {
            return;
        }

        resultList.removeAllViews();
        resultCount.setText("Rekomendasi (" + visibleSpots.size() + ")");
        moodMapView.setSpots(visibleSpots);

        if (visibleSpots.isEmpty()) {
            TextView empty = text("Belum ada lokasi yang cocok. Coba tag lain seperti healing, pedas, atau aesthetic.", 14, false, MUTED);
            empty.setPadding(0, dp(12), 0, dp(12));
            resultList.addView(empty);
            return;
        }

        for (TravelSpot spot : visibleSpots) {
            resultList.addView(spotCard(spot));
        }
    }

    private void showDetail(TravelSpot spot) {
        String message = spot.description + "\n\n"
                + "Keunikan: " + spot.uniqueness + "\n\n"
                + "Harga: " + spot.price + "\n"
                + "Jam buka: " + spot.hours + "\n"
                + "Rating: " + spot.rating + "\n\n"
                + "Tag: " + String.join(", ", spot.tags) + "\n\n"
                + "MVP action: tombol ini mewakili halaman detail dan tombol buka arah lokasi.";

        new AlertDialog.Builder(this)
                .setTitle(spot.name)
                .setMessage(message)
                .setPositiveButton("Buka Arah", null)
                .setNegativeButton(isSaved(spot.id) ? "Hapus Simpan" : "Simpan", (dialog, which) -> {
                    toggleSaved(spot.id);
                    applyFilters();
                })
                .setNeutralButton("Tutup", null)
                .show();
    }

    private boolean isSaved(int spotId) {
        return savedSet().contains(String.valueOf(spotId));
    }

    private Set<String> savedSet() {
        return new HashSet<>(preferences.getStringSet("saved_spots", new HashSet<>()));
    }

    private void toggleSaved(int spotId) {
        Set<String> saved = savedSet();
        String id = String.valueOf(spotId);
        if (saved.contains(id)) {
            saved.remove(id);
        } else {
            saved.add(id);
        }
        preferences.edit().putStringSet("saved_spots", saved).apply();
    }

    private TextView text(String value, int sp, boolean bold, int color) {
        TextView textView = new TextView(this);
        textView.setText(value);
        textView.setTextSize(sp);
        textView.setTextColor(color);
        textView.setLineSpacing(0, 1.08f);
        if (bold) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        return textView;
    }

    private View space(int height) {
        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(1, dp(height)));
        return view;
    }

    private GradientDrawable round(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    private GradientDrawable round(int color, int radius, int strokeColor) {
        GradientDrawable drawable = round(color, radius);
        drawable.setStroke(dp(1), strokeColor);
        return drawable;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
