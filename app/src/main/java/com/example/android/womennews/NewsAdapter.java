package com.example.android.womennews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link NewsAdapter} creates a list item layout for each story
 * in the data source (a list of {@link News} objects).
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context   of the app
     * @param womennews is the list of stories, which is the data source of the adapter
     */

    public NewsAdapter(Context context, List<News> womennews) {
        super(context, 0, womennews);
    }

    /**
     * Returns a list item view that displays information about every story.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.womennews_item, parent, false);
        }

        // Find the news at the given position in the list of women news
        News currentNews = getItem(position);

        // Find the TextView with view ID section name
        TextView sectionNameView = listItemView.findViewById(R.id.sectionname);
        // Display section name of the current news in that TextView
        sectionNameView.setText(currentNews.getSectionName());
        // Find the original publication date in the News object
        String storyFullDate = currentNews.getWebPublicationDate();
        // Create story date & time from original publication date by splitting a string into 2 strings
        // and showing them in two independent views
        String storyDate;
        String storyTime;
        // Split the string
        if (storyFullDate.contains("T")) {
            String[] parts = storyFullDate.split("T");
            storyDate = parts[0];
            storyTime = " " + parts[1];
            // Cut the "Z" (for time zone) from storyTime as it is not needed in this project
            if (storyTime.endsWith("Z")) {
                storyTime = storyTime.substring(0, storyTime.length() - 4);
            }
            // Find a view and display storyDate in in it
            TextView publicationDateView = listItemView.findViewById(R.id.publicationdate);
            publicationDateView.setText(storyDate);
            // Find a view and display storyTime in in it
            TextView publicationTimeView = listItemView.findViewById(R.id.publicationtime);
            publicationTimeView.setText(storyTime);
        }

        // Get the original news title form the News object
        String newsTitle = currentNews.getWebTitle();
        String storyTitle;
        String storyAuthor;
// Cut out everything that randomly shows up after | separator
        if (newsTitle.contains(" | ")) {
            String[] parts = newsTitle.split("[|] ");
            storyTitle = parts[0];
            storyAuthor = parts[1];
            // Find the TextView with view ID webtitle
            TextView webTitleView = listItemView.findViewById(R.id.webtitle);
            // Display the title of the current news in that TextView
            webTitleView.setText(storyTitle);
            // Find the TextView with view ID author and show the author name in it
            TextView storyAuthorView = listItemView.findViewById(R.id.author);
            storyAuthorView.setText(storyAuthor);

        } else {
            storyTitle = newsTitle;
            // Find the TextView with view ID webtitle
            TextView webTitleView = listItemView.findViewById(R.id.webtitle);
            // Display the title of the current news in that TextView
            webTitleView.setText(storyTitle);
        }
        // Find the TextView with view ID url
        TextView newsUrlView = listItemView.findViewById(R.id.url);
        // Display the "Read more" text in that TextView
        //  to let user know it is clickable in case he doesn't know that whole view has a browser intent
        newsUrlView.setText(R.string.read_more);

        // Return the list item view with all news
        return listItemView;
    }
}