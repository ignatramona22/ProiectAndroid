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

public class CoursePaymentAdapter extends ArrayAdapter<CoursePayment> {

    private Context context;
    private int layoutId;
    private List<CoursePayment> paymentList;
    private LayoutInflater inflater;

    public CoursePaymentAdapter(@NonNull Context context, int resource, @NonNull List<CoursePayment> paymentList) {
        super(context, resource, paymentList);
        this.context = context;
        this.layoutId = resource;
        this.paymentList = paymentList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layoutId, parent, false);

        CoursePayment payment = paymentList.get(position);

        TextView tvCourseName = view.findViewById(R.id.tvCourseName);
        TextView tvAmount = view.findViewById(R.id.tvAmount);
        TextView tvPaymentMethod = view.findViewById(R.id.tvPaymentMethod);
        TextView tvPaymentStatus = view.findViewById(R.id.tvPaymentStatus);
        if (payment.getCourseName() != null) {
            tvCourseName.setText(payment.getCourseName());
        } else {
            tvCourseName.setText("Unknown Course");
        }

        tvAmount.setText("Amount: $" + payment.getAmount());

        if (payment.getPaymentMethod() != null) {
            tvPaymentMethod.setText("Payment Method: " + payment.getPaymentMethod().toString());
        } else {
            tvPaymentMethod.setText("Payment Method: Unknown Method");
        }

        String paymentStatus;
        if (payment.getPaymentStatus() != null) {
            paymentStatus = payment.getPaymentStatus().toString();
            tvPaymentStatus.setText("Status: " + paymentStatus);
        } else {
            paymentStatus = "Unknown Status";
            tvPaymentStatus.setText("Status: Unknown Status");
        }

        if ("Pending".equals(paymentStatus)) {
            tvPaymentStatus.setBackgroundColor(Color.YELLOW);
        } else if ("Completed".equals(paymentStatus)) {
            tvPaymentStatus.setBackgroundColor(Color.GREEN);
        }

        return view;
    }

}
