package bankApp.entity;

import bankApp.validation.ValidateAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int id;

    @Column(name = "number")
    @ValidateAccount
    private String number;

    @Column(name = "amount")
    private double amount;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private Client client;

    // cascade: delete transactions if account deleted
    @OneToMany(mappedBy = "accountTo", // "accountTo" - field in Transaction
            orphanRemoval = true, // delete account in List<Transaction> & save this ValidateAccount object - causes account removal from db (see AccountDaoImpl.delete())
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Transaction> transactionsTo;

    @OneToMany(mappedBy = "accountFrom",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Transaction> transactionsFrom;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(List<Transaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }

    public List<Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    public void setTransactionsFrom(List<Transaction> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ValidateAccount{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", amount=" + amount +
                ", client=" + client +
                '}';
    }
}
