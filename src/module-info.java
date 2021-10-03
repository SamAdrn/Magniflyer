module Magniflyer {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.media;

    opens magniflyer.gui;
    opens magniflyer.user;
    opens media;

}