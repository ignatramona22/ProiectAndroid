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

public class InvoiceAdapter extends ArrayAdapter<Invoice> {

    private Context context;
    private int layoutId;
    private List<Invoice> invoiceList;
    private LayoutInflater inflater;

    public InvoiceAdapter(@NonNull Context context, int resource, @NonNull List<Invoice> invoiceList) {
        super(context, resource, invoiceList);
        this.context = context;
        this.layoutId = resource;
        this.invoiceList = invoiceList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layoutId, parent, false);

        Invoice invoice = invoiceList.get(position);
        TextView tvUsername = view.findViewById(R.id.tvInvoiceViewUsername);
        TextView tvCourses = view.findViewById(R.id.tvInvoiceViewCourses);
        TextView tvTotalAmount = view.findViewById(R.id.tvInvoiceViewTotalAmount);
        TextView tvTaxAmount = view.findViewById(R.id.tvInvoiceViewTaxAmount);
        TextView tvDiscountAmount = view.findViewById(R.id.tvInvoiceViewDiscountAmount);


        if (invoice.getUsername() != null) {
            tvUsername.setText("Username: " + invoice.getUsername());
        } else {
            tvUsername.setText("Username: Unknown");
        }

        List<String> courseTitles = invoice.getCourseTitles();
        if (courseTitles != null && !courseTitles.isEmpty()) {
            tvCourses.setText("Courses: " + String.join(", ", courseTitles));
        } else {
            tvCourses.setText("Courses: None");
        }

        tvTotalAmount.setText("Total: $" + invoice.getTotalAmount());
        tvTaxAmount.setText("Tax: $" + invoice.getTaxAmount());
        tvDiscountAmount.setText("Discount: $" + invoice.getDiscountAmount());

        if (invoice.getTotalAmount() > 1000) {
            view.setBackgroundColor(Color.parseColor("#FFE4B5"));
        } else {
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        return view;
    }
}
