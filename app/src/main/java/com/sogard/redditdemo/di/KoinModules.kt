package com.sogard.redditdemo.di

import com.sogard.data.apis.ApiServiceGenerator
import com.sogard.data.apis.CommentsApi
import com.sogard.data.apis.PostApi
import com.sogard.data.repositories.AuthenticationRepositoryImpl
import com.sogard.data.repositories.CommentRepositoryImpl
import com.sogard.data.repositories.ArticleRepositoryImpl
import com.sogard.domain.repositories.AuthenticationRepository
import com.sogard.domain.repositories.CommentRepository
import com.sogard.domain.repositories.ArticleRepository
import com.sogard.domain.usecases.ApplicationInitializationUseCase
import com.sogard.domain.usecases.CommentListingUseCase
import com.sogard.domain.usecases.TopArticleListingUseCase
import com.sogard.ui.helpers.ResourceProvider
import com.sogard.data.datasources.SharedPreferencesHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val helperModule: Module = module {
    single { SharedPreferencesHelper(androidContext()) }
    single { ResourceProvider(androidContext()) }
}
private val API_GENERATOR = "API_GENERATOR"

val networkModule: Module = module {
    single(named(API_GENERATOR)) { ApiServiceGenerator(get()) }
    single { get<ApiServiceGenerator>(named(API_GENERATOR)).createService(PostApi::class.java) }
    single { get<ApiServiceGenerator>(named(API_GENERATOR)).createAuthenticationService() }
    single { get<ApiServiceGenerator>(named(API_GENERATOR)).createService(CommentsApi::class.java) }
}

val repositoryModule: Module = module {
    single<CommentRepository> { CommentRepositoryImpl(commentsApi = get()) }
    single<ArticleRepository> { ArticleRepositoryImpl(postApi = get()) }
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            sharedPrefHelper = get(),
            authenticationApi = get()
        )
    }
}

val useCaseModule: Module = module {
    single {
        ApplicationInitializationUseCase(authenticationRepository = get(), articleRepository = get())
    }
    single { TopArticleListingUseCase(articleRepository = get()) }
    single { CommentListingUseCase(commentRepository = get()) }
}

