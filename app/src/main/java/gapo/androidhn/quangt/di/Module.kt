package gapo.androidhn.quangt.di

import android.app.Application
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import gapo.androidhn.quangt.BuildConfig
import gapo.androidhn.quangt.model.api.GapoApi
import gapo.androidhn.quangt.model.db.AppDatabase
import gapo.androidhn.quangt.model.db.FeedDao
import gapo.androidhn.quangt.model.repository.FeedRepository
import gapo.androidhn.quangt.utils.BASE_URL
import gapo.androidhn.quangt.viewmodel.DetailViewModel
import gapo.androidhn.quangt.viewmodel.PagerViewModel
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { PagerViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val apiModule = module {
    fun provideApi(retrofit: Retrofit): GapoApi {
        return retrofit.create(GapoApi::class.java)
    }

    single { provideApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}

fun createLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG)
        logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(database: AppDatabase): FeedDao {
        return database.feedDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideProductRepository(api: GapoApi, dao: FeedDao): FeedRepository {
        return FeedRepository(api, dao)
    }
    single { provideProductRepository(get() as GapoApi, get() as FeedDao) }
}