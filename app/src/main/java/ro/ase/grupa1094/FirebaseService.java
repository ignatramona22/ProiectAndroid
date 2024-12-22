package ro.ase.grupa1094;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService
{
    private final DatabaseReference reference;
    private static  FirebaseService firebaseService;

    private FirebaseService()
    {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseService getInstance()
    {
        if (firebaseService == null)
        {
            synchronized (FirebaseService.class)
            {
                if (firebaseService == null)
                {
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    //CoursePayment
    public void insertPayment(CoursePayment payment)
    {
        if (payment == null ||payment.getPaymentID()!=null)
        {
            return;
        }

        String id = reference.push().getKey();
        payment.setPaymentID(id);
        reference.child(payment.getPaymentID()).setValue(payment);
    }

    public void updatePayment(CoursePayment payment)
    {
        if (payment == null ||payment.getPaymentID() == null)
        {
            return;
        }
        reference.child(payment.getPaymentID()).setValue(payment);
    }

    public void deletePayment(CoursePayment payment)
    {
        if (payment == null ||payment.getPaymentID()==null)
        {
            return;
        }

        reference.child(payment.getPaymentID()).removeValue();
    }

    public void addPaymentsListener(Callback<List<CoursePayment>> callback)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CoursePayment> payments = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren())
                {
                    CoursePayment payment = data.getValue(CoursePayment.class);
                    if(payment!=null)
                    {
                        payments.add(payment);
                    }
                }
                callback.runOnUi(payments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "This payment is not enable");
            }
        });
    }


    public void findPaymentsByStatus(PaymentStatus status, Callback<List<CoursePayment>> callback) {
        if (status == null) {
            return;
        }
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CoursePayment> filteredPayments = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    CoursePayment payment = data.getValue(CoursePayment.class);
                    if (payment != null && payment.getPaymentStatus() == status) {
                        filteredPayments.add(payment);
                    }
                }
                callback.runOnUi(filteredPayments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Error filtering payments by status: " + error.getMessage());
            }
        });
    }


    public void calculateTotalPayments(Callback<Float> callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float totalAmount = 0;

                for (DataSnapshot data : snapshot.getChildren()) {
                    CoursePayment payment = data.getValue(CoursePayment.class);
                    if (payment != null) {
                        totalAmount += payment.getAmount();
                    }
                }

                callback.runOnUi(totalAmount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseService", "Eroare la calculul totalului: " + error.getMessage());
                callback.runOnUi(0f);
            }
        });
    }


    public void findPaymentsByMinAmount(double minAmount, Callback<List<CoursePayment>> callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CoursePayment> filteredPayments = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    CoursePayment payment = data.getValue(CoursePayment.class);
                    if (payment != null && payment.getAmount() >= minAmount) {
                        filteredPayments.add(payment);
                    }
                }
                callback.runOnUi(filteredPayments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Error filtering payments by amount: " + error.getMessage());
            }
        });
    }

    public void getAllCoursesTitle(Callback<List<String>> callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> courseTitles = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    CoursePayment payment = data.getValue(CoursePayment.class);
                    if (payment != null && payment.getCourseName() != null) {
                        courseTitles.add(payment.getCourseName());
                    }
                }

                callback.runOnUi(courseTitles);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseService", "Eroare la obținerea titlurilor cursurilor: " + error.getMessage());
                callback.runOnUi(new ArrayList<>());
            }
        });
    }


    //Invoice

    public void insertInvoice(Invoice invoice) {
        if (invoice == null || invoice.getInvoiceID() != null || invoice.getUsername() == null || invoice.getUsername().isEmpty()) {
            Log.e("FirebaseService", "Cannot insert null or invalid invoice");
            return;
        }

        String id = reference.push().getKey();
        invoice.setInvoiceID(id);
        reference.child(invoice.getInvoiceID()).setValue(invoice);
    }


    public void updateInvoice(Invoice invoice)
    {
        if (invoice == null ||invoice.getInvoiceID() == null)
        {
            return;
        }
        reference.child(invoice.getInvoiceID()).setValue(invoice);
    }

    public void deleteInvoice(Invoice invoice)
    {
        if (invoice == null ||invoice.getInvoiceID()==null)
        {
            return;
        }

        reference.child(invoice.getInvoiceID()).removeValue();
    }

    public void addInvoicesListener(Callback<List<Invoice>> callback)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Invoice> invoices = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren())
                {
                    Invoice invoice = data.getValue(Invoice.class);
                    if(invoice!=null)
                    {
                        invoices.add(invoice);
                    }
                }
                callback.runOnUi(invoices);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "This invoice is not enable");
            }
        });
    }

    public void findInvoicesByUsername(String username, Callback<List<Invoice>> callback) {
        if (username == null || username.isEmpty()) {
            return;
        }
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Invoice> filteredInvoices = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Invoice invoice = data.getValue(Invoice.class);
                    if (invoice != null && username.equals(invoice.getUsername())) {
                        filteredInvoices.add(invoice);
                    }
                }
                callback.runOnUi(filteredInvoices);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Error filtering invoices by username: " + error.getMessage());
            }
        });
    }


    public void calculateNetAmount(String invoiceID, Callback<Double> callback) {
        if (invoiceID == null || invoiceID.isEmpty()) {
            return;
        }

        reference.child(invoiceID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Invoice invoice = snapshot.getValue(Invoice.class);
                if (invoice != null) {
                    double netAmount = invoice.getTotalAmount() + invoice.getTaxAmount() - invoice.getDiscountAmount();
                    callback.runOnUi(netAmount);
                } else {
                    callback.runOnUi(0.0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Error calculating net amount: " + error.getMessage());
            }
        });
    }

    public void findInvoiceWithMaxTotalAmount(Callback<Invoice> callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Invoice maxInvoice = null;
                double maxAmount = Double.MIN_VALUE;

                for (DataSnapshot data : snapshot.getChildren()) {
                    Invoice invoice = data.getValue(Invoice.class);
                    if (invoice != null && invoice.getTotalAmount() > maxAmount) {
                        maxInvoice = invoice;
                        maxAmount = invoice.getTotalAmount();
                    }
                }

                callback.runOnUi(maxInvoice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Error finding invoice with max total amount: " + error.getMessage());
                callback.runOnUi(null);
            }
        });
    }



}
