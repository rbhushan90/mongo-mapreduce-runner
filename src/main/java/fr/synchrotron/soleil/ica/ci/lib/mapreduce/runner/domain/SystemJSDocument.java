package fr.synchrotron.soleil.ica.ci.lib.mapreduce.runner.domain;

/**
 * @author Gregory Boissinot
 */
public class SystemJSDocument {

    private String _id;

    private String value;

    public SystemJSDocument(String _id, String value) {
        this._id = _id;
        this.value = value;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
