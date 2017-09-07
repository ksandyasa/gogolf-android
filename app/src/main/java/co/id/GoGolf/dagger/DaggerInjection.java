package co.id.GoGolf.dagger;

import co.id.GoGolf.dagger.components.AppComponent;
import co.id.GoGolf.dagger.components.DaggerAppComponent;
import co.id.GoGolf.dagger.modules.AppModule;

/**
 * Created by dedepradana on 2/28/16.
 */
public class DaggerInjection {
    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}
