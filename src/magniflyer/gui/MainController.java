package magniflyer.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import magniflyer.user.AccountManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainController {

    /*
    Panes
     */
    @FXML
    private VBox findFlightsPane;

    @FXML
    private VBox registerPane;

    @FXML
    private VBox signInPane;

    @FXML
    private ScrollPane viewAccountPane;

    /*
    ToolBar
     */
    @FXML
    private Label logInRegister;

    @FXML
    private Label viewAccount;

    /*
    Find Flights Pane
     */
    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private DatePicker returnDatePicker;

    /*
    Register Pane
     */
    @FXML
    private TextField firstNameRegister;

    @FXML
    private TextField middleInitialRegister;

    @FXML
    private TextField lastNameRegister;

    @FXML
    private TextField emailRegister;

    @FXML
    private PasswordField passwordRegister;

    @FXML
    private TextField passwordUnmaskedRegister;

    @FXML
    private CheckBox checkboxRegister;

    @FXML
    private PasswordField passwordConfirmRegister;

    @FXML
    private Label statusLabelRegister;

    @FXML
    private CheckBox acceptTC;

    /*
    Sign In Pane
     */
    @FXML
    private TextField emailSignIn;

    @FXML
    private PasswordField passwordSignIn;

    @FXML
    private TextField passwordUnmaskedSignIn;

    @FXML
    private CheckBox checkboxSignIn;

    @FXML
    private Label statusLabelSignIn;

    /*
    View Account Pane
     */
    @FXML
    private Label accountNameLabel;

    /*
    Local Fields
     */
    private final AccountManager accountManager = new AccountManager();

    public void initialize() {
        formatDatePickers(departureDatePicker);
        formatDatePickers(returnDatePicker);

        passwordUnmaskedRegister.managedProperty().bind(checkboxRegister.selectedProperty());
        passwordUnmaskedRegister.visibleProperty().bind(checkboxRegister.selectedProperty());
        passwordRegister.managedProperty().bind(checkboxRegister.selectedProperty().not());
        passwordRegister.visibleProperty().bind(checkboxRegister.selectedProperty().not());
        passwordUnmaskedRegister.textProperty().bindBidirectional(passwordRegister.textProperty());

        passwordUnmaskedSignIn.managedProperty().bind(checkboxSignIn.selectedProperty());
        passwordUnmaskedSignIn.visibleProperty().bind(checkboxSignIn.selectedProperty());
        passwordSignIn.managedProperty().bind(checkboxSignIn.selectedProperty().not());
        passwordSignIn.visibleProperty().bind(checkboxSignIn.selectedProperty().not());
        passwordUnmaskedSignIn.textProperty().bindBidirectional(passwordSignIn.textProperty());

//        switchToFindFlights();
    }

    @FXML
    public void switchToSignIn() {
        findFlightsPane.setVisible(false);
        registerPane.setVisible(false);
        viewAccountPane.setVisible(false);
        signInPane.setVisible(true);
    }

    @FXML
    public void switchToRegister() {
        findFlightsPane.setVisible(false);
        signInPane.setVisible(false);
        viewAccountPane.setVisible(false);
        registerPane.setVisible(true);
    }

    @FXML
    public void switchToFindFlights() {
        signInPane.setVisible(false);
        registerPane.setVisible(false);
        viewAccountPane.setVisible(false);
        findFlightsPane.setVisible(true);
    }

    @FXML
    public void switchToViewAccount() {
        signInPane.setVisible(false);
        registerPane.setVisible(false);
        findFlightsPane.setVisible(true);
        accountNameLabel.setText(accountManager.getAccountFullName().toUpperCase());
        viewAccountPane.setVisible(true);
    }

    @FXML
    public void handleRegister() {
        String firstName = firstNameRegister.getText().trim();
        String middleInitial = middleInitialRegister.getText().trim();
        String lastName = lastNameRegister.getText().trim();
        String email = emailRegister.getText().trim();
        String password = passwordRegister.getText().trim();
        if (firstName.equals("")) {
            statusLabelRegister.setText("First name field must not be empty");
            firstNameRegister.setStyle("-fx-background-color: red , white , white;");
            firstNameRegister.requestFocus();
        } else {
            statusLabelRegister.setText("");
            firstNameRegister.setStyle("-fx-background-color: #a9a9a9 , white , white;");
            if (lastName.equals("")) {
                statusLabelRegister.setText("Last name field must not be empty");
                lastNameRegister.setStyle("-fx-background-color: red , white , white;");
                lastNameRegister.requestFocus();
            } else {
                statusLabelRegister.setText("");
                lastNameRegister.setStyle("-fx-background-color: #a9a9a9 , white , white;");
                if (email.equals("")) {
                    statusLabelRegister.setText("Email field must not be empty");
                    emailRegister.setStyle("-fx-background-color: red , white , white;");
                    emailRegister.requestFocus();
                } else {
                    statusLabelRegister.setText("");
                    emailRegister.setStyle("-fx-background-color: #a9a9a9 , white , white;");
                    if (accountManager.checkEmail(email)) {
                        statusLabelRegister.setText("Email is already associated with an account.");
                        emailRegister.setStyle("-fx-background-color: red , white , white;");
                        emailRegister.requestFocus();
                    } else {
                        statusLabelRegister.setText("");
                        emailRegister.setStyle("-fx-background-color: #a9a9a9 , white , white;");
                        if (password.equals("")) {
                            statusLabelRegister.setText("Password field must not be empty");
                            passwordRegister.setStyle("-fx-background-color: red , white , white;");
                            passwordUnmaskedRegister.setStyle("-fx-background-color: red , white , white;");
                            if (passwordSignIn.isVisible()) {
                                passwordRegister.requestFocus();
                            } else {
                                passwordUnmaskedRegister.requestFocus();
                            }
                        } else {
                            statusLabelRegister.setText("");
                            passwordRegister.setStyle("-fx-background-color: #a9a9a9 , white , white;");
                            passwordUnmaskedRegister.setStyle("-fx-background-color: #a9a9a9 , white , white;");
                            if (!password.equals(passwordConfirmRegister.getText().trim())) {
                                statusLabelRegister.setText("Password does not match");
                                passwordConfirmRegister.setStyle("-fx-background-color: red , white , white;");
                                passwordConfirmRegister.requestFocus();
                            } else {
                                statusLabelRegister.setText("");
                                passwordConfirmRegister.setStyle("-fx-background-color: #a9a9a9 , white , white;");
                                if (!acceptTC.isSelected()) {
                                    statusLabelRegister.setText("Please accept terms and conditions");
                                    acceptTC.requestFocus();
                                } else {
                                    if (accountManager.createNewAccount(firstName, middleInitial, lastName, email, password)) {
                                        clearRegisterPane();
                                        switchToSignIn();
                                        statusLabelSignIn.setText("Account successfully created. Please log in.");
                                    } else {
                                        statusLabelRegister.setText("Something bad happened. Error code: #203");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void handleSignIn() {
        String email = emailSignIn.getText().trim();
        String password = passwordSignIn.getText().trim();
        if (email.equals("")) {
            statusLabelSignIn.setText("Email field must not be empty");
            emailSignIn.setStyle("-fx-background-color: red , white , white;");
            emailSignIn.requestFocus();
        } else {
            statusLabelSignIn.setText("");
            emailSignIn.setStyle("-fx-background-color: #a9a9a9 , white , white;");
            if (!accountManager.checkEmail(email)) {
                statusLabelSignIn.setText("Email is not associated with any account.");
            } else {
                statusLabelSignIn.setText("");
                if (password.equals("")) {
                    statusLabelSignIn.setText("Password field must not be empty");
                    passwordSignIn.setStyle("-fx-background-color: red , white , white;");
                    passwordUnmaskedSignIn.setStyle("-fx-background-color: red , white , white;");
                    if (passwordSignIn.isVisible()) {
                        passwordSignIn.requestFocus();
                    } else {
                        passwordUnmaskedSignIn.requestFocus();
                    }
                } else {
                    statusLabelSignIn.setText("");
                    passwordSignIn.setStyle("-fx-background-color: #a9a9a9 , white , white;");
                    if (accountManager.logIn(email, password)) {
                        logInRegister.managedProperty().setValue(false);
                        logInRegister.visibleProperty().setValue(false);
                        viewAccount.managedProperty().setValue(true);
                        viewAccount.visibleProperty().setValue(true);
                        viewAccount.setText("Hi " + accountManager.getAccountFullName());
                        clearSignInPane();
                        switchToFindFlights();
                    } else {
                        statusLabelSignIn.setText("Incorrect Password");
                        passwordSignIn.setStyle("-fx-background-color: red , white , white;");
                        passwordUnmaskedSignIn.setStyle("-fx-background-color: red , white , white;");
                        if (passwordSignIn.isVisible()) {
                            passwordSignIn.requestFocus();
                        } else {
                            passwordUnmaskedSignIn.requestFocus();
                        }
                    }
                }
            }
        }
    }

    private void formatDatePickers(DatePicker datePicker) {
        String pattern = "MMMM dd yyyy";
        datePicker.setConverter(new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    private void clearRegisterPane() {
        firstNameRegister.clear();
        middleInitialRegister.clear();
        lastNameRegister.clear();
        emailRegister.clear();
        passwordRegister.clear();
        checkboxRegister.selectedProperty().setValue(false);
        passwordConfirmRegister.clear();
        acceptTC.selectedProperty().setValue(false);
        statusLabelRegister.setText("");
    }

    private void clearSignInPane() {
        emailSignIn.clear();
        passwordSignIn.clear();
        checkboxSignIn.selectedProperty().setValue(false);
    }

}
