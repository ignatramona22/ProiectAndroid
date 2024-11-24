package ro.ase.grupa1094;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public  class CourseAdapter extends ArrayAdapter<Course>
{
    private Context context;
    private int layoutId;
    private List<Course> courseList;
    private LayoutInflater inflater;

    public CourseAdapter(@NonNull Context context, int resource, @NonNull List<Course> courseList, LayoutInflater inflater) {
        super(context, resource, courseList);
        this.context = context;
        this.layoutId = resource;
        this.courseList = courseList;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = inflater.inflate(layoutId, parent, false);
        Course course = courseList.get(position);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        TextView tvPrice = view.findViewById(R.id.tvPrice);

        tvTitle.setText(course.getTitle());
        tvDescription.setText(course.getDescription());
        tvPrice.setText(String.valueOf(course.getPrice()));

        if(course.getTitle() !=null)
        {
            tvTitle.setText(course.getTitle().toUpperCase());
            tvTitle.setTypeface(tvTitle.getTypeface(), Typeface.BOLD);

        }

        tvPrice.setText(String.format("$%.2f", course.getPrice()));
        tvPrice.setTypeface(tvPrice.getTypeface(), Typeface.ITALIC);

        String description = course.getDescription();
        if (description.length() > 100) {
            description = description.substring(0, 100) + "...";
        }
        tvDescription.setText(description);

        return view;
    }
}