package com.aeroclubcargo.warehouse.domain.repository

import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

interface Remote {
    suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto
    suspend fun refreshToken(refreshToken: String): AuthenticateResponseDto
    suspend fun apiGetSectorsList(): String
    suspend fun getCargoBooking(pageSize: Int, pageIndex: Int): Pagination<PackageListItem>
    suspend fun getPackageDetails(packageRefNumber: String): PackageDetails
    suspend fun acceptCargo(
        configType: Constants.AircraftConfigType,
        id: String,
        @Body @Constants.BookingStatus bookingStatus: Int
    ): Boolean
    suspend fun cargoBookingSummaryList(
        FlightNumber: String,
        FlightDate: String,
        PageIndex: Int,
        PageSize: Int,
    ): Pagination<CutOffTimeModel>

    suspend fun updateCutOffTIme(id: String,body: CutOffTimeRequest): Boolean
    suspend fun updatePackageStatus(@Body body: UpdatePackageStatus): Response<Boolean?>

}