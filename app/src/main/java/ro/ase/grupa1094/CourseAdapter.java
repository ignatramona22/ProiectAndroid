package ro.ase.grupa1094;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private Context context;
    private List<Course> courseList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public CourseAdapter(Context context, List<Course> courseList, OnItemClickListener onItemClickListener,  OnItemLongClickListener itemLongClickListener) {
        this.context = context;
        this.courseList = courseList;
        this.inflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.tvTitle.setText(course.getTitle());
        holder.tvDescription.setText(course.getDescription());
        holder.tvPrice.setText(String.format("Price: $%.2f", course.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(course, position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            itemLongClickListener.onItemLongClick(course, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Course course, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Course course, int position);
    }
    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvPrice;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvAddCourse);
            tvDescription = itemView.findViewById(R.id.tvDescriptionCourse);
            tvPrice = itemView.findViewById(R.id.tvPriceCourse);
        }
    }
}
