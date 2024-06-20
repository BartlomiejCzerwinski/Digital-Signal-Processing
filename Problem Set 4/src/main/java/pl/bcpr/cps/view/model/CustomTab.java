package pl.bcpr.cps.view.model;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public class CustomTab extends Tab {
    public CustomTab(String text, Node content, boolean isClosable) {
        super(text, content);
        super.setClosable(isClosable);
    }
}
    