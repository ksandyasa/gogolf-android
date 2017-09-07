package co.id.GoGolf.dagger.modules;

import co.id.GoGolf.models.RestAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dedepradana on 2/28/16.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    RestAPI provideRestApi() {
        return new RestAPI();
    }

}
