package ro.ase.grupa1094;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private Context context;
    private List<Course> courseList;

    public void setSearchList(List<Course> courseSearchList){
        this.courseList = courseSearchList;
        notifyDataSetChanged();
    }

    public ItemAdapter(Context context, List<Course> courseList){
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.recTitle.setText(courseList.get(position).getTitle());
        holder.recPrice.setText(String.valueOf(courseList.get(position).getPrice()));
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;


                switch (course.getTitle()) {
                    case "Mathematics":
                        intent = new Intent(context, MathCourseActivity.class);
                        break;
                    case "Geography":
                        intent = new Intent(context, GeographyCourseActivity.class);
                        break;
                    case "Time Management":
                        intent = new Intent(context, TimeManagementCourseActivity.class);
                        break;
                    case "Business":
                        intent = new Intent(context, BusinessCourseActivity.class);
                        break;
                    default:
                        Toast.makeText(context, "No activity found for this course", Toast.LENGTH_SHORT).show();
                        return;
                }

                intent.putExtra("Title", course.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

}

class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recPrice;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recTitle = itemView.findViewById(R.id.recTitle);
        recDesc = itemView.findViewById(R.id.recDesc);
        recPrice = itemView.findViewById(R.id.recPrice);
        recCard = itemView.findViewById(R.id.recCard);
    }
}