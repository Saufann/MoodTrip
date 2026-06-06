package com.moodtrip.app;

import java.util.Arrays;
import java.util.List;

class TravelSpot {
    final int id;
    final String name;
    final String category;
    final String location;
    final String description;
    final String uniqueness;
    final String price;
    final String hours;
    final float rating;
    final float mapX;
    final float mapY;
    final List<String> tags;

    TravelSpot(
            int id,
            String name,
            String category,
            String location,
            String description,
            String uniqueness,
            String price,
            String hours,
            float rating,
            float mapX,
            float mapY,
            String... tags
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.description = description;
        this.uniqueness = uniqueness;
        this.price = price;
        this.hours = hours;
        this.rating = rating;
        this.mapX = mapX;
        this.mapY = mapY;
        this.tags = Arrays.asList(tags);
    }

    boolean matches(String query, String activeTag) {
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase();
        String normalizedTag = activeTag == null ? "" : activeTag.trim().toLowerCase();

        boolean tagMatch = normalizedTag.isEmpty() || containsTag(normalizedTag);
        if (normalizedQuery.isEmpty()) {
            return tagMatch;
        }

        String haystack = (name + " " + category + " " + location + " " + description + " "
                + uniqueness + " " + tags).toLowerCase();
        return tagMatch && haystack.contains(normalizedQuery);
    }

    boolean containsTag(String tag) {
        for (String item : tags) {
            if (item.toLowerCase().contains(tag)) {
                return true;
            }
        }
        return false;
    }
}
