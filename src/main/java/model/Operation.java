package model;

public class Operation {
    private int id;
    private String date_time;
    private String transaction_type;
    private String description;
    private double amount;
    private String source_account;
    private String destination_account;
    private int manager_id;
    private int attorney_id;
    private String payment_method;
    private String voucher_number;

    public Operation(int id, String date_time, String transaction_type, String description, double amount,
                     String source_account, String destination_account, int manager_id, int attorney_id,
                     String payment_method, String voucher_number) {
        this.id = id;
        this.date_time = date_time;
        this.transaction_type = transaction_type;
        this.description = description;
        this.amount = amount;
        this.source_account = source_account;
        this.destination_account = destination_account;
        this.manager_id = manager_id;
        this.attorney_id = attorney_id;
        this.payment_method = payment_method;
        this.voucher_number = voucher_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getTransaction_Type() {
        return transaction_type;
    }

    public void setTransaction_Type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSource_Account() {
        return source_account;
    }

    public void setSource_Account(String sourceAccount) {
        this.source_account = sourceAccount;
    }

    public String getDestinationAccount() {
        return destination_account;
    }

    public void setDestinationAccount(String destination_account) {
        this.destination_account = destination_account;
    }

    public int getManagerId() {
        return manager_id;
    }

    public void setManagerId(int manager_id) {
        this.manager_id = manager_id;
    }

    public int getAttorneyId() {
        return attorney_id;
    }

    public void setAttorneyId(int attorney_id) {
        this.attorney_id = attorney_id;
    }

    public String getPayment_Method() {
        return payment_method;
    }

    public void setPayment_Method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getVoucher_Number() {
        return voucher_number;
    }

    public void setVoucher_Number(String voucher_number) {
        this.voucher_number = voucher_number;
    }
    
    public Operation() {
        // Constructor vacío
    }

    
    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", date_time='" + date_time + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", source_account='" + source_account + '\'' +
                ", destination_account='" + destination_account + '\'' +
                ", manager_id=" + manager_id +
                ", attorney_id=" + attorney_id +
                ", payment_method='" + payment_method + '\'' +
                ", voucher_number='" + voucher_number + '\'' +
                '}';
    }
}
