package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import database.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm {
    public TextField txtUsernameLoginForm;
    public PasswordField txtPasswordLoginForm; // Changed to PasswordField
    public Hyperlink hykLoginForm;
    public Button btnLoginLoginForm;
    public Hyperlink hykForgetPassword;

    public void txtUsernameLoginFormOnAction(ActionEvent actionEvent) {
    }

    public void txtPasswordLoginFormOnAction(ActionEvent actionEvent) {
    }

    public void hykLoginFormOnAction(ActionEvent actionEvent) {
        try {
            // Load the RegisterForm.fxml
            Parent registerForm = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));

            // Get the current stage
            Stage stage = (Stage) ((Hyperlink) actionEvent.getSource()).getScene().getWindow();

            // Set the scene to the register form
            stage.setScene(new Scene(registerForm, 497, 400));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to open register form: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Login Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnLoginLoginFormOnAction(ActionEvent actionEvent) {
        String username = txtUsernameLoginForm.getText();
        String password = txtPasswordLoginForm.getText();

        if (username.isBlank() || password.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Please enter username and password");
        } else {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT * FROM new_table WHERE username = ? AND password = ?")) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Load the DashboardForm.fxml
                    Parent dashboardForm = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

                    // Get the current stage
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

                    // Set the scene to the dashboard form
                    stage.setScene(new Scene(dashboardForm, 600, 400));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login failed, try again");
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
            }
        }

        // Clear the text fields
        txtUsernameLoginForm.clear();
        txtPasswordLoginForm.clear();
    }

    public void hykForgetPasswordOnAction(ActionEvent actionEvent) {
        try {
            Parent forgetPasswordForm = FXMLLoader.load(getClass().getResource("/view/ForgetPasswordForm.fxml"));
            Stage stage = (Stage) ((Hyperlink) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(forgetPasswordForm, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to open forget password form: " + e.getMessage());
        }
    }
}