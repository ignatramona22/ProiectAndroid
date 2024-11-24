package ro.ase.grupa1094;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FeedbackAdapter extends ArrayAdapter<Feedback>
{
    private Context context;
    private int layoutId;
    private List<Feedback> feedbackList;
    private LayoutInflater  inflater;

    public FeedbackAdapter(@NonNull Context context, int resource, @NonNull List<Feedback> feedbackList, LayoutInflater inflater)
    {
        super(context, resource, feedbackList);
        this.context = context;
        this.layoutId = resource;
        this.feedbackList = feedbackList;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = inflater.inflate(layoutId, parent, false);
        Feedback feedback = feedbackList.get(position);

        TextView tvUser = view.findViewById(R.id.tvUser);
        TextView tvFeedback = view.findViewById(R.id.tvFeedback);
        TextView tvRating = view.findViewById(R.id.tvRating);

        tvUser.setText(feedback.getUsername());
        tvFeedback.setText(feedback.getFeedbackText());
        tvRating.setText(String.valueOf(feedback.getRating()));

        if(feedback.getUsername().length() > 2)
        {
            tvUser.setTextColor(Color.RED);
        }

        return  view;
    }
}
