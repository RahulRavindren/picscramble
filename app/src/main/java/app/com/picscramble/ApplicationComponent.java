package app.com.picscramble;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

@Singleton
@Component( modules = {ApplicationModule.class})
public interface ApplicationComponent extends ApplicationGraph{
}
