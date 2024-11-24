package ro.ase.grupa1094;

import java.io.Serializable;

public class Feedback implements Serializable {
    private String username;
    private String feedbackText;
    private int rating;

    public Feedback(String username, String feedbackText, int rating)
    {
        this.username = username;
        this.feedbackText = feedbackText;
        this.rating = rating;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }



    @Override
    public String toString() {
        return "Feedback from " + username + ":\n" + feedbackText + "\nRating: " + rating + "/5";
    }

}
