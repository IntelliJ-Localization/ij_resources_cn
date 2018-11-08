package translation.bo;

import java.util.Objects;

/**
 * @author jianyun.zhao
 * Create at 2018/11/07
 */
public class LanguageResource {

    private String key;

    private String cnValue;

    private String enValue;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCnValue() {
        return cnValue;
    }

    public void setCnValue(String cnValue) {
        this.cnValue = cnValue;
    }

    public String getEnValue() {
        return enValue;
    }

    public void setEnValue(String enValue) {
        this.enValue = enValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageResource that = (LanguageResource) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
