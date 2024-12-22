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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InvoiceActivity extends AppCompatActivity {

    private EditText etUsername, etSearchUsername ;
    private ListView lvCourseTitles;
    private ImageView ivBackToPayment;
    private TextView tvTotalAmount, tvTaxAmount, tvDiscountAmount, tvNetAmount;
    private Button btnApplyTax, btnApplyDiscount, btnCreateInvoice, btnClearInvoice, btnSearchInvoices, btnCalculateNetAmount, btnFindMaxInvoice;
    private ListView lvInvoices;
    private int indexSelectedInvoice = -1;
    List<Invoice> invoiceList = new ArrayList<>();

    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);


        etUsername = findViewById(R.id.etUsername);
        lvCourseTitles = findViewById(R.id.lvCourseTitles);
        lvCourseTitles.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTaxAmount = findViewById(R.id.tvTaxAmount);
        tvDiscountAmount = findViewById(R.id.tvDiscountAmount);
        btnApplyTax = findViewById(R.id.btnApplyTax);
        btnApplyDiscount = findViewById(R.id.btnApplyDiscount);
        btnCreateInvoice = findViewById(R.id.btnCreateInvoice);
        btnClearInvoice = findViewById(R.id.btnCleanInvoice);
        etSearchUsername = findViewById(R.id.etSearchUsername);
        btnSearchInvoices = findViewById(R.id.btnSearchInvoices);
        btnCalculateNetAmount = findViewById(R.id.btnCalculateNetAmount);
        tvNetAmount = findViewById(R.id.tvNetAmount);
        btnFindMaxInvoice = findViewById(R.id.btnFindMaxInvoice);
        ivBackToPayment = findViewById(R.id.ivBackToPayment);
        lvInvoices = findViewById(R.id.lvInvoices);

        firebaseService = FirebaseService.getInstance();
        loadCourseTitles();
        loadInvoices();

        btnApplyTax.setOnClickListener(v -> applyTax());

        btnApplyDiscount.setOnClickListener(v -> applyDiscount());
        calculateTotalAmount();

        ivBackToPayment.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), CoursePaymentActivity.class);
            startActivity(intent);
        });
        btnCreateInvoice.setOnClickListener(saveDataEventListenet());
        lvInvoices.setOnItemClickListener(invoicesSelectedEventListener());
        lvInvoices.setOnItemLongClickListener(invoicesLongClickListener());
        btnClearInvoice.setOnClickListener(cleanDataEventListenet());
        btnSearchInvoices.setOnClickListener(v -> {
            String username = etSearchUsername.getText().toString();
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseService.findInvoicesByUsername(username, filteredInvoices -> {
                invoiceList.clear();
                invoiceList.addAll(filteredInvoices);
                InvoiceAdapter adapter = new InvoiceAdapter(getApplicationContext(), R.layout.view_invoice, invoiceList);
                lvInvoices.setAdapter(adapter);

                if (filteredInvoices.isEmpty()) {
                    Toast.makeText(this, "No invoices found for user: " + username, Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnCalculateNetAmount.setOnClickListener(v -> {
            if (indexSelectedInvoice == -1) {
                Toast.makeText(this, "Please select an invoice first", Toast.LENGTH_SHORT).show();
                return;
            }

            Invoice selectedInvoice = invoiceList.get(indexSelectedInvoice);
            firebaseService.calculateNetAmount(selectedInvoice.getInvoiceID(), netAmount -> {
                tvNetAmount.setText("Net Amount: " + netAmount);
                Toast.makeText(this, "Net amount calculated: " + netAmount, Toast.LENGTH_SHORT).show();
            });
        });


        btnFindMaxInvoice.setOnClickListener(v -> findMaxInvoice());
    }

    private void findMaxInvoice() {
        firebaseService.findInvoiceWithMaxTotalAmount(maxInvoice -> {
            if (maxInvoice != null) {
                String message = "Max Invoice: " +
                        "\nUsername: " + maxInvoice.getUsername() +
                        "\nTotal Amount: " + maxInvoice.getTotalAmount();
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No invoices found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private AdapterView.OnItemClickListener invoicesSelectedEventListener() {
        return (adapterView, view, position, l) -> {
            indexSelectedInvoice = position;
            Invoice invoice = invoiceList.get(position);

            etUsername.setText(invoice.getUsername());

            tvTotalAmount.setText("Total: " + invoice.getTotalAmount());
            tvTaxAmount.setText("Tax: " + invoice.getTaxAmount());
            tvDiscountAmount.setText("Discount: " + invoice.getDiscountAmount());

            for (int i = 0; i < lvCourseTitles.getCount(); i++) {
                String courseTitle = (String) lvCourseTitles.getItemAtPosition(i);
                lvCourseTitles.setItemChecked(i, isCourseSelected(courseTitle, invoice.getCoursePayments()));
            }

            Toast.makeText(this, "Invoice selected: " + invoice.getUsername(), Toast.LENGTH_SHORT).show();
        };
    }
    private boolean isCourseSelected(String courseTitle, List<CoursePayment> coursePayments) {
        if (coursePayments == null || coursePayments.isEmpty()) {
            return false;
        }
        for (CoursePayment payment : coursePayments) {
            if (payment.getCourseName().equals(courseTitle)) {
                return true;
            }
        }
        return false;
    }


    private View.OnClickListener cleanDataEventListenet() {
        return view-> cleanText();
    }

    private void cleanText() {
        etUsername.setText(null);
        tvTaxAmount.setText(null);
        tvDiscountAmount.setText(null);
        indexSelectedInvoice = -1;
    }
    private View.OnClickListener saveDataEventListenet() {
        return view->{
            if(validare())
            {
                if(indexSelectedInvoice==-1)
                {
                    Invoice invoice = actualizareInvoiceFromUI(null);
                    firebaseService.insertInvoice(invoice);
                }
                else {
                    Invoice invoice = actualizareInvoiceFromUI(invoiceList.get(indexSelectedInvoice).getInvoiceID());
                    firebaseService.updateInvoice(invoice);
                }
            }
        };

    }

    private Invoice actualizareInvoiceFromUI(String id) {
        if (!validare()) {
            return null;
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceID(id);
        invoice.setUsername(etUsername.getText().toString());

        List<CoursePayment> selectedCoursePayments = new ArrayList<>();
        for (int i = 0; i < lvCourseTitles.getCount(); i++) {
            if (lvCourseTitles.isItemChecked(i)) {
                String courseTitle = (String) lvCourseTitles.getItemAtPosition(i);
                CoursePayment coursePayment = new CoursePayment();
                coursePayment.setCourseName(courseTitle);
                selectedCoursePayments.add(coursePayment);
            }
        }

        invoice.setCoursePayments(selectedCoursePayments);

        try {
            invoice.setTotalAmount(Double.parseDouble(tvTotalAmount.getText().toString().replace("Total: ", "")));
            invoice.setTaxAmount(Double.parseDouble(tvTaxAmount.getText().toString().replace("Tax: ", "")));
            invoice.setDiscountAmount(Double.parseDouble(tvDiscountAmount.getText().toString().replace("Discount: ", "")));
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid amount format", Toast.LENGTH_SHORT).show();
            return null;
        }

        return invoice;
    }



    private boolean validare() {
        if (etUsername.getText() == null || etUsername.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tvTotalAmount.getText() == null || tvTotalAmount.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Total amount is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Double.parseDouble(tvTotalAmount.getText().toString().replace("Total: ", ""));
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid total amount format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    private void loadCourseTitles() {
        firebaseService.getAllCoursesTitle(courseTitles -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(InvoiceActivity.this,
                    android.R.layout.simple_list_item_1, courseTitles);
            lvCourseTitles.setAdapter(adapter);
        });
    }

    private void calculateTotalAmount() {
        firebaseService.calculateTotalPayments(totalAmount -> {
            tvTotalAmount.setText("Total: " + totalAmount);
        });
    }




    private void applyTax() {
        float totalAmount = Float.parseFloat(tvTotalAmount.getText().toString().replace("Total: ", ""));
        float taxAmount = totalAmount * 0.19f;
        tvTaxAmount.setText("Tax: " + taxAmount);
    }


    private void applyDiscount() {
        float totalAmount = Float.parseFloat(tvTotalAmount.getText().toString().replace("Total: ", ""));
        float discountAmount = totalAmount * 0.1f;
        tvDiscountAmount.setText("Discount: " + discountAmount);
    }



    private void loadInvoices() {
        firebaseService.addInvoicesListener(invoices -> {
            invoiceList.clear();
            invoiceList.addAll(invoices);
            InvoiceAdapter adapter = new InvoiceAdapter(getApplicationContext(), R.layout.view_invoice, invoiceList);
            lvInvoices.setAdapter(adapter);
        });
    }


    private AdapterView.OnItemLongClickListener invoicesLongClickListener() {
        return (adapterView, view, position, id) -> {
            new AlertDialog.Builder(InvoiceActivity.this)
                    .setTitle("Delete Invoice")
                    .setMessage("Are you sure you want to delete this invoice?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        indexSelectedInvoice = position;
                        firebaseService.deleteInvoice(invoiceList.get(indexSelectedInvoice));
                        Toast.makeText(getApplicationContext(), "Invoice deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        };
    }


}
