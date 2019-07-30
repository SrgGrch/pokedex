
package com.srggrch.pokedex.core;

import com.srggrch.pokedex.core.modules.ApiModule;
import com.srggrch.pokedex.core.modules.SharedPreferencesModule;
import com.srggrch.pokedex.features.list.ListPresenter;
import com.srggrch.pokedex.features.pokemon.PokemonPresenter;
import dagger.Component;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ApiModule.class, SharedPreferencesModule.class})
public interface AppComponent {

    Retrofit getApiModule();

    void inject(ListPresenter listPresenter);

    void inject(PokemonPresenter pokemonPresenter);
}

