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

import org.w3c.dom.Text;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task>
{
    private Context context;
    private int layoutId;
    private List<Task> taskList;
    private LayoutInflater inflater;

    public TaskAdapter(@NonNull Context context, int resource, @NonNull List<Task> taskList, LayoutInflater inflater)
    {
        super(context, resource, taskList);
        this.context = context;
        this.layoutId = resource;
        this.taskList = taskList;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = inflater.inflate(layoutId, parent, false);
        Task task = taskList.get(position);
        TextView tvTaskTitle = view.findViewById(R.id.tvTitleTask);
        TextView tvTaskDescription = view.findViewById(R.id.tvDescriptionTask);
        TextView tvStatus = view.findViewById(R.id.tvStatusTask);

        tvTaskTitle.setText(task.getTitle());
        tvTaskDescription.setText(task.getDescription());
        tvStatus.setText(String.valueOf(task.getStatus()));


        String status = String.valueOf(task.getStatus());
        tvStatus.setText(status);
        if ("Pending".equals(status)) {
            tvStatus.setBackgroundColor(Color.YELLOW);
        } else if ("In Progress".equals(status)) {
            tvStatus.setBackgroundColor(Color.BLUE);
        } else if ("Completed".equals(status)) {
            tvStatus.setBackgroundColor(Color.GREEN);
        }
        tvTaskTitle.setText(task.getTitle().toUpperCase());
        return view;
    }
}
