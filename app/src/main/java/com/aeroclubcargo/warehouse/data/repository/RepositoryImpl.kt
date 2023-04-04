package com.aeroclubcargo.warehouse.data.repository

import com.aeroclubcargo.warehouse.common.Constants
import com.aeroclubcargo.warehouse.data.local.DataStorePreferenceRepository
import com.aeroclubcargo.warehouse.data.local.dto.CredentialDto
import com.aeroclubcargo.warehouse.data.remote.ApiInterface
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateRequestDto
import com.aeroclubcargo.warehouse.data.remote.dto.AuthenticateResponseDto
import com.aeroclubcargo.warehouse.domain.model.BookingStatusUpdateRequest
import com.aeroclubcargo.warehouse.domain.model.PackageDetails
import com.aeroclubcargo.warehouse.domain.model.PackageListItem
import com.aeroclubcargo.warehouse.domain.model.Pagination
import com.aeroclubcargo.warehouse.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private var datastore: DataStorePreferenceRepository
) : Repository {

    override suspend fun authenticateUser(authenticateRequestDto: AuthenticateRequestDto): AuthenticateResponseDto {
        return apiInterface.authenticateUser(authenticateRequestDto = authenticateRequestDto)
    }

    override suspend fun refreshToken(refreshToken: String): AuthenticateResponseDto {
        return apiInterface.refreshToken(refreshToken)
    }

    override suspend fun apiGetSectorsList(): String {
        return apiInterface.apiGetSectors()
    }

    override suspend fun getCargoBooking(
        pageSize: Int,
        pageIndex: Int
    ): Pagination<PackageListItem> {
        return apiInterface.getCargoPackageList(
            pageIndex = pageIndex,
            pageSize = pageSize,
            includeCargoBooking = true
        )
    }

    override suspend fun getPackageDetails(packageRefNumber: String): PackageDetails {
        return apiInterface.getPackageDetails(packageRefNumber = packageRefNumber)
    }

    override suspend fun acceptCargo(configType: Constants.AircraftConfigType,id: String,@Constants.BookingStatus bookingStatus: Int): Boolean {
        return when(configType){
            Constants.AircraftConfigType.None -> {
                TODO()
            }
            Constants.AircraftConfigType.P2C -> {
                apiInterface.acceptCargoBooking(BookingStatusUpdateRequest(id, bookingStatus))
            }
            Constants.AircraftConfigType.Freighter -> {
                apiInterface.updateULDCargoBooking(BookingStatusUpdateRequest(id, bookingStatus))
            }
        }

    }

    override suspend fun saveCredential(credentialDto: CredentialDto) {
        datastore.saveCredential(credentialDto = credentialDto)
    }

    override suspend fun getCredential(): Flow<CredentialDto?> {
        return datastore.getCredential
    }

    override suspend fun clearCredential() {
        return datastore.clearCredential()
    }

    override suspend fun removeLoginUserDetails() {
        return datastore.removeLoginUserDetails()
    }

    override suspend fun saveLoggedInUser(authenticateRequestDto: AuthenticateResponseDto) {
        return datastore.saveAuthenticatedLoggedInUser(authenticateRequestDto = authenticateRequestDto)
    }

    override suspend fun saveJwtToken(jwtToken: String) {
        return datastore.saveJwtToken(jwtToken)
    }

    override suspend fun getJwtToken(): Flow<String> {
        return datastore.getJwtToken
    }

    override suspend fun getLoggedInUser(): Flow<AuthenticateResponseDto?> {
        return datastore.authenticatedLoggedInUser
    }


}