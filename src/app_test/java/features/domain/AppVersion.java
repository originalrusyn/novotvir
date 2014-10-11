package features.domain;


// @author: Mykhaylo Titov on 13.09.14 15:20.
public enum  AppVersion {
    _1_0("1.0");

    String serverApiVersion;

    AppVersion(String serverApiVersion){
        this.serverApiVersion=serverApiVersion;
    }

    @Override
    public String toString() {
        return name().replaceFirst("_", "").replaceAll("_", ".");
    }
}
