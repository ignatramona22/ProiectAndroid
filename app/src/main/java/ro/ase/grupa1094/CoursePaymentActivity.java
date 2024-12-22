package ro.ase.grupa1094;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CoursePaymentActivity extends AppCompatActivity
{
  private ImageView ivArrowBackToProfile;
  private EditText etCourseTitle;
  private EditText etAmount;
  private Spinner spnPaymentMethod;
  private Spinner spnPaymentStatus;
  private TextView tvDate;
  private Button btnSave;
  private Button btnClean;
  private List<CoursePayment> coursePaymentList = new ArrayList<>();
  private ListView lvPayments;
  private FirebaseService firebaseService;
  private int indexSelectedPayment = -1;
  private Spinner spnFilterStatus;
  private Button btnFilterStatus;
  private Button btnCalculateTotal;
  private TextView tvTotalPayments;
  private Button btnFilterByMinAmount;
  private EditText etMinAmount;
  private ImageView ivInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_payment);
        initComponents();
        firebaseService = FirebaseService.getInstance();

        ivArrowBackToProfile.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        ivInvoice.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), InvoiceActivity.class);
            startActivity(intent);
        });

        String curentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        tvDate.setText(curentDate);

        firebaseService.addPaymentsListener(dataChangedCallback());
        lvPayments.setOnItemLongClickListener(paymentLongClickListener());


        btnFilterStatus.setOnClickListener(view -> {
            String selectedStatus = spnFilterStatus.getSelectedItem().toString();
            if (selectedStatus.equals("Completed") || selectedStatus.equals("Pending")) {
                PaymentStatus status = PaymentStatus.valueOf(selectedStatus);
                firebaseService.findPaymentsByStatus(status, filteredPayments -> {
                    coursePaymentList.clear();
                    coursePaymentList.addAll(filteredPayments);
                    CoursePaymentAdapter adapter = (CoursePaymentAdapter) lvPayments.getAdapter();
                    adapter.notifyDataSetChanged();
                });
            } else {
                Toast.makeText(getApplicationContext(), "Select a valid status!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCalculateTotal.setOnClickListener(view -> {
            firebaseService.calculateTotalPayments(totalAmount -> {
                tvTotalPayments.setText("Total Payments: " + totalAmount);
            });
        });


        btnFilterByMinAmount.setOnClickListener(view -> {
            String minAmountText = etMinAmount.getText().toString();
            if (minAmountText.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a minimum amount", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double minAmount = Double.parseDouble(minAmountText);
                    if (minAmount <= 0) {
                        Toast.makeText(getApplicationContext(), "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
                    } else {
                        firebaseService.findPaymentsByMinAmount(minAmount, filteredPayments -> {
                            coursePaymentList.clear();
                            coursePaymentList.addAll(filteredPayments);
                            CoursePaymentAdapter adapter = (CoursePaymentAdapter) lvPayments.getAdapter();
                            adapter.notifyDataSetChanged();
                        });
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initComponents()
    {
        ivArrowBackToProfile = findViewById(R.id.ivArrowBackToProfile);
        etCourseTitle = findViewById(R.id.etPayCourseTitle);
        etAmount = findViewById(R.id.etPayAmount);
        spnPaymentMethod = findViewById(R.id.spnPaymentMethod);
        ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.paymentMethods, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnPaymentMethod.setAdapter(methodAdapter);

        spnPaymentStatus = findViewById(R.id.spnPaymentStatus);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.paymentStatus, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnPaymentStatus.setAdapter(statusAdapter);
        tvDate = findViewById(R.id.tvPaymentDate);
        btnSave = findViewById(R.id.btnSavePay);
        btnClean = findViewById(R.id.btnClean);
        spnFilterStatus = findViewById(R.id.spnFilterStatus);
        btnFilterStatus = findViewById(R.id.btnFilterStatus);
        btnCalculateTotal = findViewById(R.id.btnCalculateTotal);
        tvTotalPayments = findViewById(R.id.tvTotalPayments);
        btnFilterByMinAmount = findViewById(R.id.btnFilterByMinAmount);
        etMinAmount = findViewById(R.id.etMinAmount);
        ivInvoice = findViewById(R.id.ivInvoice);
        lvPayments = findViewById(R.id.lvPaymentsList);
        CoursePaymentAdapter adapter = new CoursePaymentAdapter(getApplicationContext(), R.layout.view_course_payment, coursePaymentList);
        lvPayments.setAdapter(adapter);

        btnClean.setOnClickListener(cleanDataEventListenet());
        btnSave.setOnClickListener(saveDataEventListenet());

        lvPayments.setOnItemClickListener(paymentSelectedEventListener());
    }

    private Callback<List<CoursePayment>> dataChangedCallback() {
        return rezultat ->{
            coursePaymentList.clear();
            coursePaymentList.addAll(rezultat);
            CoursePaymentAdapter adapter = (CoursePaymentAdapter) lvPayments.getAdapter();
            adapter.notifyDataSetChanged();
            cleanText();
        };
    }

    private AdapterView.OnItemLongClickListener paymentLongClickListener() {
        return (adapterView, view, position, id) -> {
            indexSelectedPayment = position;
            deleteDataEventListenet().onClick(view);
            return true;
        };
    }

    private AdapterView.OnItemClickListener paymentSelectedEventListener()
    {
        return (adapterView, view, position, l)->{
            indexSelectedPayment = position;
            CoursePayment coursePayment = coursePaymentList.get(position);
            etCourseTitle.setText(coursePayment.getCourseName());
            etAmount.setText(String.valueOf(coursePayment.getAmount()));

            ArrayAdapter<CharSequence> methodAdapter = (ArrayAdapter<CharSequence>) spnPaymentMethod.getAdapter();
            int methodPosition = methodAdapter.getPosition(coursePayment.getPaymentMethod().toString());
            spnPaymentMethod.setSelection(methodPosition);

            ArrayAdapter<CharSequence> statusAdapter = (ArrayAdapter<CharSequence>) spnPaymentStatus.getAdapter();
            int statusPosition = statusAdapter.getPosition(coursePayment.getPaymentStatus().toString());
            spnPaymentStatus.setSelection(statusPosition);

            tvDate.setText(coursePayment.getPaymentDate());
        };
    }

    private View.OnClickListener saveDataEventListenet()
    {
        return view->{
            if(validare())
            {
                if(indexSelectedPayment==-1)
                {
                    CoursePayment coursePayment = actualizarePaymentFromUI(null);
                    firebaseService.insertPayment(coursePayment);
                }
                else {
                    CoursePayment coursePayment = actualizarePaymentFromUI(coursePaymentList.get(indexSelectedPayment).getPaymentID());
                    firebaseService.updatePayment(coursePayment);
                }
            }
        };

    }

    private CoursePayment actualizarePaymentFromUI(String id) {
        CoursePayment coursePayment = new CoursePayment();
        coursePayment.setPaymentID(id);
        coursePayment.setCourseName(etCourseTitle.getText().toString());
        coursePayment.setAmount(Float.valueOf(etAmount.getText().toString()));
        coursePayment.setPaymentMethod(PaymentMethod.valueOf(spnPaymentMethod.getSelectedItem().toString()));
        coursePayment.setPaymentStatus(PaymentStatus.valueOf(spnPaymentStatus.getSelectedItem().toString()));
        coursePayment.setPaymentDate(tvDate.getText().toString());
        return coursePayment;
    }

    private boolean validare() {
        if (etCourseTitle.getText() == null || etCourseTitle.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Course title is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        String amountText = etAmount.getText().toString();
        if (amountText == null || amountText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Amount is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                Toast.makeText(getApplicationContext(), "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spnPaymentMethod.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            Toast.makeText(getApplicationContext(), "Please select a payment method", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spnPaymentStatus.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            Toast.makeText(getApplicationContext(), "Please select a payment status", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private View.OnClickListener cleanDataEventListenet() {
        return view-> cleanText();
    }

    private void cleanText() {
        etCourseTitle.setText(null);
        etAmount.setText(null);
        spnPaymentMethod.setSelection(0);
        spnPaymentStatus.setSelection(0);
        String curentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        tvDate.setText(curentDate);
        indexSelectedPayment = -1;
    }

    private View.OnClickListener deleteDataEventListenet() {
        return view->{
            if(indexSelectedPayment!=-1)
            {
                firebaseService.deletePayment(coursePaymentList.get(indexSelectedPayment));
            }
        };

    }
}