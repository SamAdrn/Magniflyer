package magniflyer.user;

import java.io.Serializable;
import java.util.*;

public class AccountManager {

    private Account selectedAccount;

    private AccountDatabase accounts;

    public AccountManager() {
        selectedAccount = null;
        accounts = new AccountDatabase();
    }

    public boolean checkEmail(String email) {
        return accounts.findAccount(email) != null;
    }

    public boolean logIn(String email, String password) {
        if (accounts.authenticate(email, password)) {
            selectedAccount = accounts.findAccount(email);
            return true;
        }
        return false;
    }

    public void logOut() {
        selectedAccount = null;
    }

    public boolean isLoggedIn() {
        return selectedAccount != null;
    }

    public boolean createNewAccount(String firstName, String middleInitial, String lastName, String email,
                                 String password) {
        return accounts.addAccount(firstName, middleInitial, lastName, email, password);
    }

    public void closeAccount() {
        accounts.removeAccount(selectedAccount.getCustomerID());
        selectedAccount = null;
    }

    public String getAccountFullName() {
        if (isLoggedIn()) {
            return selectedAccount.toString();
        }
        return "Not Logged In";
    }

    protected static class AccountDatabase implements Serializable {

        private final Map<Integer, Account> accountsWithId;
        private final Map<String, Account> accountsWithEmail;

        public AccountDatabase() {
            this.accountsWithId = new HashMap<>();
            this.accountsWithEmail = new HashMap<>();
        }

        public boolean addAccount(String firstName, String middleInitial, String lastName, String email,
                                  String password) {
            if (findAccount(email) != null) {
                return false;
            }
            Account account;
            int id;
            while (true) {
                id = new Random().nextInt(900000000) + 100000000;
                if (findAccount(id) == null) {
                    account = new Account(firstName, middleInitial, lastName, email, password, id);
                    break;
                }
            }
            accountsWithEmail.put(email, account);
            accountsWithId.put(id, account);
            return true;
        }

        public void removeAccount(int customerId) {
            Account a = findAccount(customerId);
            accountsWithId.remove(customerId);
            accountsWithEmail.remove(a.getEmail());
        }

        public Account findAccount(String email) {
            return accountsWithEmail.get(email);
        }

        public Account findAccount(int customerId) {
            return accountsWithId.get(customerId);
        }

        public boolean authenticate(String email, String password) {
            Account a = findAccount(email);
            if (a == null) {
                return false;
            }
            return a.getPassword().equals(password);
        }

    }
}
