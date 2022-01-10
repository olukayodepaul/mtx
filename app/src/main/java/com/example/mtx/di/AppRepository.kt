package com.example.mtx.di

import com.example.mtx.datasource.AppDao
import com.example.mtx.datasource.RetrofitService
import com.example.mtx.datasource.RetrofitServices
import com.example.mtx.ui.attendant.repository.AttendantRepo
import com.example.mtx.ui.attendant.repository.AttendantRepoImpl
import com.example.mtx.ui.customers.repository.AddCustomerRep
import com.example.mtx.ui.customers.repository.AddCustomerRepoImpl
import com.example.mtx.ui.login.repository.LoginRepo
import com.example.mtx.ui.login.repository.LoginRepoImpl
import com.example.mtx.ui.module.repository.ModulesRepo
import com.example.mtx.ui.module.repository.ModulesRepoImpl
import com.example.mtx.ui.order.repository.OrderRepo
import com.example.mtx.ui.order.repository.OrderRepoImpl
import com.example.mtx.ui.orderpurchase.repository.OrderPurchaseRepo
import com.example.mtx.ui.orderpurchase.repository.OrderPurchaseRepoImpl
import com.example.mtx.ui.sales.repository.SalesRepo
import com.example.mtx.ui.sales.repository.SalesRepoImpl
import com.example.mtx.ui.salesentry.repository.SalesEntryRepo
import com.example.mtx.ui.salesentry.repository.SalesEntryRepoImpl
import com.example.mtx.ui.salesrecord.repository.SalesRecordRepo
import com.example.mtx.ui.salesrecord.repository.SalesRecordRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppRepository {

    @Singleton
    @Provides
    fun provideLoginRepository(
        retrofitClient: RetrofitService,
        appdoa: AppDao
    ): LoginRepo {
        return LoginRepoImpl(
            retrofitClient, appdoa
        )
    }

    @Singleton
    @Provides
    fun provideModulesRepository(
        retrofitClient: RetrofitService,
        appdoa: AppDao
    ): ModulesRepo {
        return ModulesRepoImpl(
            retrofitClient, appdoa
        )
    }

    @Singleton
    @Provides
    fun provideSalesRepository(
        retrofitClient: RetrofitService,
        appdoa: AppDao,
        retrofitServices: RetrofitServices
    ): SalesRepo {
        return SalesRepoImpl(
            retrofitClient, appdoa, retrofitServices
        )
    }

    @Singleton
    @Provides
    fun provideSalesEntryRepository(
        retrofitClient: RetrofitService,
        appdoa: AppDao
    ): SalesEntryRepo {
        return SalesEntryRepoImpl(
            retrofitClient, appdoa
        )
    }

    @Singleton
    @Provides
    fun provideSalesRecordRepository(
        retrofitClient: RetrofitService,
        appdoa: AppDao,
        retrofitServices: RetrofitServices
    ): SalesRecordRepo {
        return SalesRecordRepoImpl(
            retrofitClient, appdoa, retrofitServices
        )
    }

    @Singleton
    @Provides
    fun provideAttendantRepository(
        retrofitClient: RetrofitService,
        appdoa: AppDao
    ): AttendantRepo {
        return AttendantRepoImpl(
            retrofitClient, appdoa
        )
    }

    @Singleton
    @Provides
    fun provideAddCustomersRepository(
        retrofitClient: RetrofitService,
        appdoa: AppDao,
        retrofitServices: RetrofitServices
    ): AddCustomerRep {
        return AddCustomerRepoImpl(
            retrofitClient, appdoa, retrofitServices
        )
    }

    @Singleton
    @Provides
    fun provideOrderPurchaseRepository(
        retrofitClient: RetrofitService,
        appdoa: AppDao,
        networkHelper: NetworkHelper
    ): OrderPurchaseRepo {
        return OrderPurchaseRepoImpl(
            retrofitClient, appdoa, networkHelper
        )
    }

    @Singleton
    @Provides
    fun provideReOrderRepository(
        retrofitClient: RetrofitServices,
        appdoa: AppDao
    ): OrderRepo {
        return OrderRepoImpl(
            retrofitClient, appdoa
        )
    }


}