
package com.srggrch.testapp.core;

import com.srggrch.testapp.core.modules.ApiModule;
import com.srggrch.testapp.core.modules.SharedPreferencesModule;
import com.srggrch.testapp.features.list.ListAdapter;
import com.srggrch.testapp.features.list.ListPresenter;
import com.srggrch.testapp.features.pokemon.PokemonPresenter;
import dagger.Component;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ApiModule.class, SharedPreferencesModule.class})
public interface AppComponent {

    Retrofit getApiModule();

    void inject(ListPresenter listPresenter);

    void inject(PokemonPresenter pokemonPresenter);
}

