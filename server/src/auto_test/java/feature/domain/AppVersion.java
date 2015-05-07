package feature.domain;


// @author: Mykhaylo Titov on 13.09.14 15:20.
public enum  AppVersion {
    _1_0;

    @Override
    public String toString() {
        return name().replaceFirst("_", "").replaceAll("_", ".");
    }
}
