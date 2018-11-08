package translation.bo;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jianyun.zhao
 * Create at 2018/11/07
 */
public class ResourceFile {

    private String path;

    private String name;

    private List<LanguageResource> props;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LanguageResource> getProps() {
        return props;
    }

    public void setProps(List<LanguageResource> props) {
        this.props = props;
    }

    public void addProp(LanguageResource prop) {
        if (props == null) {
            props = new ArrayList<>();
        }
        props.add(prop);
    }
}
