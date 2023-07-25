package com.aeroclubcargo.warehouse.data.remote

import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.*
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import java.util.concurrent.Flow

interface ApiInterface {

    @POST("api/${Constants.API_VERSION}/User/authenticate")
    suspend fun authenticateUser(@Body authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto

    @POST("api/${Constants.API_VERSION}/User/mobile/refresh-token")
    suspend fun refreshToken(@Body refreshToken: String): AuthenticateResponseDto

    @GET("api/${Constants.API_VERSION}/Airport/getSelectList")
    suspend fun apiGetSectors(): String

    @GET("api/${Constants.API_VERSION}/Package/GetFilteredList")
    suspend fun getCargoPackageList(
        @Query("PageSize") pageSize: Int,
        @Query("PageIndex") pageIndex: Int,
        @Query("IncludeCargoBooking") includeCargoBooking: Boolean,
    ): Pagination<PackageListItem>

    @GET("api/${Constants.API_VERSION}/CargoBooking/GetMobileBooking")
    suspend fun getPackageDetails(
        @Query("AWBTrackingNumber") awbTrackingNumber: String
    ): PackageDetails

    @POST("api/${Constants.API_VERSION}/CargoBooking")
    suspend fun acceptCargoBooking(@Body bookingStatus: BookingStatusUpdateRequest): Boolean


    @POST("api/${Constants.API_VERSION}/CargoBooking")
    suspend fun updateULDCargoBooking(@Body bookingStatus: BookingStatusUpdateRequest): Boolean

    @GET("api/${Constants.API_VERSION}/CargoBookingSummary/GetFilteredList")
    suspend fun cargoBookingSummaryList(
        @Query("FlightNumber") FlightNumber: String,
        @Query("FlightDate") FlightDate: String,
        @Query("PageIndex") PageIndex: Int,
        @Query("PageSize") PageSize: Int,
    ): Pagination<CutOffTimeModel>

    @PUT("api/${Constants.API_VERSION}/FlightSchedule/UpdateCuttOffTime/{id}")
    suspend fun updateCutOffTIme(@Path("id") id: String,@Body body: CutOffTimeRequest): Boolean

    @PUT("api/${Constants.API_VERSION}/Package/UpdateStatus")
    suspend fun updatePackageStatus(@Body body: UpdatePackageStatus): Response<Boolean?>

    @GET("api/${Constants.API_VERSION}/Unit")
    suspend fun getUnitList() : Response<List<UnitVM>>

    @PUT("api/${Constants.API_VERSION}/Package")
    suspend fun updatePackage(@Body body: PackageLineItem): Response<Any>

    @GET("api/${Constants.API_VERSION}/flightScheduleSector/GetFlightScheduleWithULDCount")
    suspend fun getFlightScheduleWithULDCount(
        @Query("ScheduledDepartureStartDateTime") scheduledDepartureStartDateTime : String,
        @Query("ScheduledDepartureEndDateTime") scheduledDepartureEndDateTime : String,
    ): Response<List<FlightScheduleModel>>

}