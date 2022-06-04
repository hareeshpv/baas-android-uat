package com.payu.baas.coreUI

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Environment
import com.ericdecanini.mockwebtest.MockResponseFileReader
import com.payu.baas.core.model.model.*
import com.payu.baas.core.model.responseModels.*
import com.payu.baas.coreUI.nonUI.util.JsonUtils
import com.payu.baas.coreUI.nonUI.interfaces.MockService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MockApiCallManger {
    public lateinit var mockWebServer: MockWebServer
    lateinit var retrofit: Retrofit
    lateinit var service: com.payu.baas.coreUI.nonUI.interfaces.MockService
    fun initialize() {
        mockWebServer = MockWebServer()
        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //Set a response for retrofit to handle. We can copy a sample
//        //response from your server to simulate a correct result or an error.
//        //MockResponse can also be customized with different parameters
//        //to match our test needs

        service = retrofit.create(com.payu.baas.coreUI.nonUI.interfaces.MockService::class.java)
    }

    fun callForSendOtpApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SendOtpResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val sendOtp = com.payu.baas.coreUI.nonUI.model.model.SendOtp()
        sendOtp.mobileNumber = "8544941620"
        return service.getCurrentOtpData(sendOtp)
    }

    fun callForVerifyOtpApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.VerifyOtpResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val verifyOtp: com.payu.baas.coreUI.nonUI.model.model.VerifyOtp =
            com.payu.baas.coreUI.nonUI.model.model.VerifyOtp()
        verifyOtp.code = "1111"
        verifyOtp.identity = "8544941620"
        verifyOtp.type = "SignIn"
        return service.getVerifyOtpData(verifyOtp)
    }

    fun callForVerifyOtpApiWrongOtp(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200) //if set responseCode here as 404. it will throw NullPointer Error
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val verifyOtp: com.payu.baas.coreUI.nonUI.model.model.VerifyOtp =
            com.payu.baas.coreUI.nonUI.model.model.VerifyOtp()
        verifyOtp.code = "1110"
        verifyOtp.identity = "8544941620"
        verifyOtp.type = "SignIn"
        return service.getVerifyOtpErrorData(verifyOtp)
    }

    fun tearDownServer() {
        mockWebServer.shutdown()
    }

    fun callForSaveAddressApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SaveAddressResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val saveAddressRequest = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_save_address_request.json").content, com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel
        return service.getSaveAAdressData(saveAddressRequest)
    }

    fun callForSaveAddressApi_MissingFields(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SaveAddressResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val saveAddressRequest = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_save_address_missing_field_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel
        return service.getSaveAAdressData(saveAddressRequest)
    }

    fun callForSaveAddressApi_WrongFieldType(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SaveAddressResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_save_address_request.json").content, com.payu.baas.coreUI.nonUI.model.model.SaveAddressWrongRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.SaveAddressWrongRequestModel
        return service.getSaveAAdressData(requestModel)
    }

    fun callForEmploymentVerificationApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.VerifyEmployeeResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val verificationRequest = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_employee_verification_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.EmploymentVerificationRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.EmploymentVerificationRequestModel
        return service.getEmployeeVerifiactionData(verificationRequest)
    }

    fun callForEmploymentVerificationApi_WrongFieldType(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.VerifyEmployeeResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val verificationRequest = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_employee_verification_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.EmploymentVerificationWrongTypeRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.EmploymentVerificationWrongTypeRequestModel
        return service.getEmployeeVerifiactionData(verificationRequest)
    }

    fun callForValidatePanApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.PanValidateResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val verificationRequest = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_validate_pan_request.json").content, com.payu.baas.coreUI.nonUI.model.model.ValidatePanRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.ValidatePanRequestModel

        return service.getValidatePanData(verificationRequest)
    }

    fun callForSetPasscodeApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SetPasswordResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val request = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_set_passcode_request.json").content, com.payu.baas.coreUI.nonUI.model.model.SetPasscodeRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.SetPasscodeRequestModel
        return service.getSetPasscodeData(request)
    }

    fun callForLoginApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.LoginResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val request = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_login_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.LoginRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.LoginRequestModel
        return service.getLoginData(request)
    }

    fun callForGetAccountBalanceDetailsApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetAccountBalanceDetailsResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_account_balance_success.json").content)
        )
        return service.getAccountBalanceData()
    }

    fun callForGetTransactionDetailsApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetTransactionDetailsResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_transaction_details_success.json").content)
        )
        return service.getTransactionDetailsData()
    }

    fun callForGetAccountDetailsApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetAccountDetailsResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_account_details_success.json").content)
        )
        return service.getAccountDetailsData()
    }

    fun callForGetUserDetailsApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetUserDetailsResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_user_details_success.json").content)
        )
        return service.getUserDetailsData()
    }

    fun callForUpdateUserEmailApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.UpdateUserDetailsResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_update_user_details_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_update_user_email_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.UpdateUserEmailRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.UpdateUserEmailRequestModel

        return service.getUpdateUserEmailData(requestModel)
    }

    fun callForUpdateUserMobileNumberApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.UpdateUserDetailsResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_update_user_details_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_update_user_mobile_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.UpdateUserMobileRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.UpdateUserMobileRequestModel

        return service.getUpdateUserMobileData(requestModel)
    }

    fun callForUpdateUserAddressApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.UpdateUserDetailsResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_update_user_details_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_update_user_address_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.UpdateUserAddressRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.UpdateUserAddressRequestModel

        return service.getUpdateUserAddressData(requestModel)
    }

    fun callForChangePasscodeApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SetPasswordResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val request = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_change_passcode_request.json").content, com.payu.baas.coreUI.nonUI.model.model.SetPasscodeRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.SetPasscodeRequestModel
        return service.changePasscode(request)
    }

    fun callForResetPasscodeApi(mockResponseJson: String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SetPasswordResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(mockResponseJson).content)
        )
        val request = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_reset_passcode_request.json").content, com.payu.baas.coreUI.nonUI.model.model.SetPasscodeRequestModel::class.java
        ) as com.payu.baas.coreUI.nonUI.model.model.ResetPasscodeRequestModel
        return service.resetPasscodeData(request)
    }

    fun callForGetUserStateApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetUserStateResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_get_user_state_success_response.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_get_user_state_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.GetUserStateModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.GetUserStateModel

        return service.getUserStateData(requestModel)
    }

    fun callForGetUserAddressApi(path : String): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetAddressResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader(path).content)
        )
        return service.getUserAddressData()
    }

    // Beneficiary apis
    fun callForGetUserBenificiaryApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetTransactionChargesResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_get_user_benificiary_success.json").content)
        )
        return service.getTransferChargesData()
    }

    fun callForCreateUserBenificiariesApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.CreateBeneficiaryResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_create_user_benificiary_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_create_user_benificiary_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.CreateAndUpdateBenificiaryRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.CreateAndUpdateBenificiaryRequestModel
        return service.getCreateBenificiaryData(requestModel)
    }

    fun callForGetRecentUserBenificiariesApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetRecentBeneficiaryResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_get_recent_user_benificiary_success.json").content)
        )
        return service.getRecentUserBenificiaryData()
    }

    fun callForUpdateUserBenificiariesApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.UpdateBeneficiaryResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_update_user_benificiary_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_create_user_benificiary_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.CreateAndUpdateBenificiaryRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.CreateAndUpdateBenificiaryRequestModel
        return service.getUpdateUserBenificiaryData(requestModel)
    }

    fun callForUserBenificiaryBankTransferApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.BeneficiaryBankTransferResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_user_benificiary_bank_transfer_success.json").content)
        )

        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_user_benificiary_bank_transfer_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.UserBenificiaryBankTransferRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.UserBenificiaryBankTransferRequestModel
        return service.getUpdateUserBenificiaryBankTransferData(requestModel)
    }
    fun callForUserBenificiaryBankTransferApi_FailureCase(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.BeneficiaryBankTransferResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_user_benificiary_bank_transfer_error.json").content)
        )

        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_user_benificiary_bank_transfer_missing_fields_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.UserBenificiaryBankTransferRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.UserBenificiaryBankTransferRequestModel
        return service.getUpdateUserBenificiaryBankTransferData(requestModel)
    }
    fun callForDeleteUserBenificiary(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.DeleteBeneficiaryResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_delete_user_benificiary_success.json").content)
        )
        return service.getDeleteUserBenificiaryData()
    }

    fun callForGetTransferChargesApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetTransactionChargesResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_transfer_charges_success.json").content)
        )
        return service.getTransferChargesData()
    }

    // Salary advance info
    fun callForGetSalaryAdvanceInfoApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetSalaryAdvanceInfoResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_salary_advance_info_success.json").content)
        )
        return service.getSalaryAdvanceinfoData()
    }

    // KYC SDK
    fun callForKycSelfieApi(myDrawable: Drawable): Call<com.payu.baas.coreUI.nonUI.model.responseModels.KYCSelfieResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_kyc_selfie_success.json").content)
//                .setBody(MockResponseFileReader("json_kyc_selfie_response.json").content)
        )

        var file: File
//        val bitmap = convertToBitmap(myDrawable)
//        if (bitmap != null) {
//            file= bitmapToFile(bitmap, "8544941607_kyc_selfie.png")
//        }
        file = File(myDrawable.toString())
        var filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file", "8544941607_kyc_selfie.png",
            RequestBody.create(MultipartBody.FORM, file)
        )
//        var filePart=   MultipartBody.Builder().setType(MultipartBody.FORM)
//            .addFormDataPart(
//                BaaSConstants.BS_KEY_LIVE_PHOTO,
//                "kyc_selfie_tushar.png",
//                RequestBody.create(
//                   MultipartBody.FORM,
//                    file)
//                ).build()

        return service.getKycSelfieData(filePart)
    }

    fun callForKycAadharApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.KYCAadharResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_kyc_aadhar_success.json").content)
//                .setBody(MockResponseFileReader("json_kyc_aadhar_response.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_kyc_aadhar_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.KycAadharRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.KycAadharRequestModel
        return service.getKycAadharData(requestModel)
    }

    fun callForKycLocationApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.KYCLocationResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_kyc_location_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_kyc_location_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.KycLocationRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.KycLocationRequestModel
        return service.getKycLocationData(requestModel)
    }

    // CARD APIS
    fun callForGetImageApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.CardImageResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_get_card_image_success.json").content)
        )
        return service.getCardImageData()
    }

    fun callForGetCardDetailsDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.CardDetailResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_get_card_details_success.json").content)
        )
        return service.getCardDetailsData()
    }

    fun callForGetCardTransactionModeDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetTransactionModeResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_get_card_transaction_mode_success.json").content)
        )
        return service.getCardTransactionModeData()
    }

    fun callForSetCardTransactionModeDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SetTransactionModeResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_set_card_transaction_mode_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_set_card_transaction_mode_request.json").content,
            com.payu.baas.coreUI.nonUI.model.responseModels.GetTransactionModeResponse::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.responseModels.GetTransactionModeResponse
        return service.setCardTransactionModeData(requestModel)
    }

    fun callForSetBlockCardDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.BlockCardResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_block_card_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_block_card_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.BlockCardRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.BlockCardRequestModel
        return service.setBlockCardData(requestModel)
    }

    fun callForSetUnBlockCardDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.UnblockCardResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_unblock_card_success.json").content)
        )
        return service.setUnBlockCardData()
    }

    fun callForSetPinDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.CardSetPinResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_card_set_pin_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_card_set_pin_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.SetPinRequestModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.SetPinRequestModel
        return service.setCardPinData(requestModel)
    }

    fun callForSetCardLimitDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.SetLimitResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_card_set_limit_success.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_card_set_limit_request.json").content,
            com.payu.baas.coreUI.nonUI.model.responseModels.GetLimitsResponse::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.responseModels.GetLimitsResponse
        return service.setCardLimitsData(requestModel)
    }

    fun callForGetCardLimitDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetLimitsResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_card_get_limit_success.json").content)
        )
        return service.getCardLimitsData()
    }

    fun callForUpdateCardPinSetStatusDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.UpdateCardPinSetStatusResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_card_update_pin_status_success.json").content)
        )
        return service.updateCardPinStatusData()
    }

    fun callForGetCardPinDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetCardPinStatusResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_card_get_pin_success.json").content)
        )
        return service.getCardPinData()
    }
    fun callForCardReorderApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.CardReorderResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_card_reorder_success_responset.json").content)
        )
        return service.cardReOrder()
    }
    fun callForValidateCardKitDataApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.ValidateCardKitResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_validate_card_kit_success_response.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_validate_card_kit_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.ValidateCardKitModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.ValidateCardKitModel
        return service.validateCardKit(requestModel)
    }
    // for validated false
    fun callForValidateFalseCardKitApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.ValidateCardKitResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_validate_card_kit_error_response.json").content)
        )
        val requestModel = com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
            MockResponseFileReader
                ("json_validate_card_kit_error_request.json").content,
            com.payu.baas.coreUI.nonUI.model.model.ValidateCardKitModel::class.java
        )
                as com.payu.baas.coreUI.nonUI.model.model.ValidateCardKitModel
        return service.validateCardKit(requestModel)
    }

    fun callForValidateCardKitStatusApi(): Call<com.payu.baas.coreUI.nonUI.model.responseModels.GetValidateCardKitStatusResponse> {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(MockResponseFileReader("json_validate_card_kit_status_success_response.json").content)
        )
        return service.getValidateCardKitStatus()
    }

    fun convertToBitmap(drawable: Drawable): Bitmap? {
        val mutableBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(mutableBitmap)
        drawable.setBounds(0, 0, 300, 300)
        drawable.draw(canvas)
        return mutableBitmap
    }

    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            file // it will return null
        }
    }
}