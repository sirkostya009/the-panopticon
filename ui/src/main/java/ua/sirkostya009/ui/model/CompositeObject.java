package ua.sirkostya009.ui.model;

public class CompositeObject {
    private Object object;

    private String getString() {
        return (String) object;
    }

    private Book getBook() {
        return (Book) object;
    }

    public static CompositeObject create(Object object) {
        var composite = new CompositeObject();
        composite.object = object;
        return composite;
    }
}
