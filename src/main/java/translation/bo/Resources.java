package translation.bo;

import java.util.List;

/**
 * @author jianyun.zhao
 * Create at 2018/11/07
 */
public class Resources {

    private String version;

    private List<ResourceFile> resourceFiles;

    private List<ResourceFile> diff;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ResourceFile> getResourceFiles() {
        return resourceFiles;
    }

    public void setResourceFiles(List<ResourceFile> resourceFiles) {
        this.resourceFiles = resourceFiles;
    }

    public List<ResourceFile> getDiff() {
        return diff;
    }

    public void setDiff(List<ResourceFile> diff) {
        this.diff = diff;
    }
}
