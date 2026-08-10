package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:${env}.properties",
        "classpath:local.properties"
})
public interface ApiConfig extends Config {

    @Key("baseUri")
    String baseUri();

    @Key("basePath")
    String basePath();
}
