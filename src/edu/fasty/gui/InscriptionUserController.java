/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fasty.gui;

import edu.fasty.entities.User;
import edu.fasty.services.IService;
import edu.fasty.services.IServiceUser;
import edu.fasty.utils.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author MSI GAMING
 */
public class InscriptionUserController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfTel;
    @FXML
    private TextField tfAdresse;
    @FXML
    private TextField tfCin;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfMdp;
    @FXML
    private Button BtnInscri;
    private String emailRegex = "\\w+\\.?\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}";
    private String checkEmailQuery = "SELECT COUNT(*) FROM user WHERE email=?";
    Connection cnx = DataSource.getInstance().getCnx();
    @FXML
    private Button BtnInscri1;
    @FXML
    private ImageView imageUploadedID;
     String imagePath;
    File selectedFile;

//    public boolean TestExist(){   
//    try (PreparedStatement checkEmailStmt = cnx.prepareStatement(checkEmailQuery)) {
//        checkEmailStmt.setString(1, tfEmail.getText());
//        ResultSet rs = checkEmailStmt.executeQuery();
//        if (rs.next() && rs.getInt(1) < 0) {
//            Alert alert = new Alert(AlertType.WARNING);
//            alert.setTitle("Attention");
//            alert.setHeaderText(null);
//            alert.setContentText("Email d??ja utilis??");
//            alert.showAndWait();
//            return false;
//        }
//    } catch (SQLException ex) {
//        Logger.getLogger(IServiceUser.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    return true;
//    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void AjouterUser(ActionEvent event) {
 IServiceUser sc = new IServiceUser();
        boolean check = sc.UserExiste(tfEmail.getText());
        if ((tfNom.getText().isEmpty()) || (tfPrenom.getText().isEmpty()) || (tfTel.getText().isEmpty()) || (tfAdresse.getText().isEmpty()) || (tfCin.getText().isEmpty()) || (tfEmail.getText().isEmpty()) || (tfMdp.getText().isEmpty())) {
            Alert alertType = new Alert(AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Enter a valid content !");
            alertType.show();
        } else if (tfNom.getText().toString().matches("[0-9]+")) {
            Alert alertType = new Alert(AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Nom must be string not number !");
            alertType.show();
        } else if (tfPrenom.getText().toString().matches("[0-9]+")) {
            Alert alertType = new Alert(AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Prenom must be string not number !");
            alertType.show();
        } else if (tfAdresse.getText().toString().matches("[0-9]+")) {
            Alert alertType = new Alert(AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Adresse must be string not number !");
            alertType.show();
        } else if (tfCin.getText().toString().matches("[A-Z]+") || tfCin.getText().toString().matches("[a-z]+") || (tfCin.getText().length() != 8)) {
            Alert alertType = new Alert(AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Le num??ro de CIN doit comporter exactement 8 chiffres.");
            alertType.show();
        } else if (tfTel.getText().toString().matches("[A-Z]+") || tfTel.getText().toString().matches("[a-z]+") || (tfTel.getText().length() != 8)) {
            Alert alertType = new Alert(AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Le num??ro de t??l??phone doit comporter exactement 8 chiffres.");
            alertType.show();
        } else if (!tfEmail.getText().matches(emailRegex)) {
            Alert alertType = new Alert(AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("L'adresse email est invalide. Veuillez saisir une adresse email valide (ex: nom_utilisateur@domaine.com) !");
            alertType.show();
        }else if(check == true){
          Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Email d??ja utilis??");
            alert.show();
        } else if(selectedFile == null){
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("S??lectionner une image !");
            alert.show();
        } else {

            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();
            int tel = Integer.parseInt(tfTel.getText());
            String adresse = tfAdresse.getText();
            int cin = Integer.parseInt(tfCin.getText());
            String email = tfEmail.getText();
            String mdp = tfMdp.getText();
            String img = "/images/hsan.png";
            User s = new User(cin, tel, nom, prenom, adresse, email, mdp,2,img);
            sc.ajouter(s);
 Alert ok=new Alert(Alert.AlertType.INFORMATION);
               ok.setTitle("FAIT");
               ok.setHeaderText("s'inscrire succ??s !");
              
              Optional<ButtonType> result  = ok.showAndWait();
                        
 if(result.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
            try {
                Parent root = loader.load();

                tfNom.getScene().setRoot(root);

            } catch (IOException ex) {
                System.out.println("Error:" + ex.getMessage());
            }
 }  

        }
    }

    @FXML
    private void ImageUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
         selectedFile = fileChooser.showOpenDialog(imageUploadedID.getScene().getWindow());
        if (selectedFile != null) {
            // The user selected an image file
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            Path path = Paths.get(selectedFile.getAbsolutePath());
    Path relativePath = path.subpath(path.getNameCount() - 2, path.getNameCount()).normalize();
             imagePath = relativePath.toString().replace('\\', '/');
             Image image = new Image(imagePath);
             imageUploadedID.setImage(image);
            // Add your code here to process the selected image file
        }
    }
}
