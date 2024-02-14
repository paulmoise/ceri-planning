module fr.ceri.prototypeinterface.ceriplanning {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens fr.ceri.prototypeinterface.ceriplanning to javafx.fxml;
    exports fr.ceri.prototypeinterface.ceriplanning;
}