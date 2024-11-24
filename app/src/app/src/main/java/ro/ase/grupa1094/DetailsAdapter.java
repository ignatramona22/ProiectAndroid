package ro.ase.grupa1094;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DetailsAdapter extends ArrayAdapter<Details>
{
    private Context context;
    private int layoutId;
    private List<Details> detailsTaskList;
    private LayoutInflater inflater;

    public DetailsAdapter(@NonNull Context context, int resource, @NonNull List<Details> detailsTaskList, LayoutInflater inflater)
    {
        super(context, resource, detailsTaskList);
        this.context = context;
        this.layoutId = resource;
        this.detailsTaskList = detailsTaskList ;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = inflater.inflate(layoutId, parent, false);
        Details detail = detailsTaskList.get(position);

        TextView tvTaskData = view.findViewById(R.id.tvTaskData);
        TextView tvAditionalInfo = view.findViewById(R.id.tvAditionalInfo);

        tvTaskData.setText(detail.getTaskData());
        tvAditionalInfo.setText(detail.getAdditionalInfo());


        return view;
    }
}
