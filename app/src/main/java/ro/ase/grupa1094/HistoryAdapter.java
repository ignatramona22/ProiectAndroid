package ro.ase.grupa1094;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private List<History> historyList;
    private LayoutInflater inflater;

    public HistoryAdapter(Context context, List<History> historyList) {
        this.context = context;
        this.historyList = historyList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History item = historyList.get(position);
        holder.courseTitle.setText(item.getCourseTitle());
        holder.lessonTitle.setText(item.getLessonTitle());
        holder.lastAccessedDate.setText("Last accessed: " + item.getLastAccessedDate());
        holder.progressBar.setProgress(item.getProgress());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle, lessonTitle, lastAccessedDate;
        ProgressBar progressBar;

        public HistoryViewHolder(@NonNull View itemView)
        {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.course_title);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            lastAccessedDate = itemView.findViewById(R.id.last_accessed_date);
            progressBar = itemView.findViewById(R.id.course_progress);
        }
    }
}
