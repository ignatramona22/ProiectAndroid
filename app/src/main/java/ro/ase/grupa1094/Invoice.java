package ro.ase.grupa1094;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Invoice implements Serializable {
    private String invoiceID;
    private String username;
    private List<CoursePayment> coursePayments;
    private double totalAmount;
    private double taxAmount;
    private double discountAmount;

    public Invoice() {
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CoursePayment> getCoursePayments() {
        return coursePayments;
    }

    public void setCoursePayments(List<CoursePayment> coursePayments) {
        this.coursePayments = coursePayments;
    }


    public List<String> getCourseTitles() {
        List<String> courseTitles = new ArrayList<>();
        if (coursePayments != null) {
            for (CoursePayment payment : coursePayments) {
                courseTitles.add(payment.getCourseName());
            }
        }
        return courseTitles;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "username='" + username + '\'' +
                ", courses=" + getCourseTitles() +
                ", totalAmount=" + totalAmount +
                ", taxAmount=" + taxAmount +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
