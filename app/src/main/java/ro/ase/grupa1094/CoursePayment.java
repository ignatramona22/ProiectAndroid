package ro.ase.grupa1094;

import java.io.Serializable;

enum PaymentMethod{Card, PayPal}
enum PaymentStatus{Completed, Pending}
public class CoursePayment implements Serializable
{
   private String paymentID;
   private String courseName;
   private float amount;
   private PaymentMethod paymentMethod;
   private String paymentDate;
   private PaymentStatus paymentStatus;

    public CoursePayment() {
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    @Override
    public String toString() {
        return "CoursePayment{" +
                "courseName='" + courseName + '\'' +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
