module fr.ceri.prototypeinterface.ceriplanning {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens fr.ceri.prototypeinterface.ceriplanning to javafx.fxml;
    opens fr.ceri.prototypeinterface.ceriplanning.controller to javafx.fxml;
    exports fr.ceri.prototypeinterface.ceriplanning;
    exports fr.ceri.prototypeinterface.ceriplanning.controller;

}