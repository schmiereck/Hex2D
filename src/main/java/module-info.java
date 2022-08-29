module de.schmiereck.hex2d {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires spring.core;
    requires spring.beans;
    requires spring.aop;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;

    opens de.schmiereck.hex2d to de.schmiereck.hex2d,javafx.controls,javafx.fxml,spring.core,spring.aop,spring.beans,spring.boot.autoconfigure,spring.boot,spring.context;
    //opens de.schmiereck.hex2d;

    exports de.schmiereck.hex2d;
}