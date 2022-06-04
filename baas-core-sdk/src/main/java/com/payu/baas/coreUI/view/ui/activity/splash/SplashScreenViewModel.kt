package com.payu.baas.coreUI.view.ui.activity.splash

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.lifecycle.*
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.params.ApiParams
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.GetUserStateResponse
import com.payu.baas.coreUI.util.ApiCall
import com.payu.baas.coreUI.util.ApiHelperUI
import com.payu.baas.coreUI.util.Resource
import com.payu.baas.coreUI.util.enums.UserStateUI
import com.payu.baas.coreUI.view.callback.BaseCallback
import com.payu.baas.coreUI.view.ui.BaseViewModel
import com.payu.baas.coreUI.view.ui.activity.accountopeningfailure.AccountOpeningFailureScreenActivity
import com.payu.baas.coreUI.view.ui.activity.enterpasscode.PasscodeActivity
import com.payu.baas.coreUI.view.ui.activity.intro.IntroActivity
import com.payu.baas.coreUI.view.ui.activity.kyc.CompleteKYCActivity
import com.payu.baas.coreUI.view.ui.activity.mobileverification.MobileVerificationActivity
import com.payu.baas.coreUI.view.ui.activity.onboarding.WelcomeScreenActivity
import com.payu.baas.coreUI.view.ui.activity.reviewAndSubmit.ReviewSubmitDetailActivity
import com.payu.baas.coreUI.view.ui.activity.set_passcode.SetPasscodeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import java.util.*


class SplashScreenViewModel(
    baseCallBack: BaseCallback?,
    context: Context
) : BaseViewModel(baseCallBack, null, context) {
    //    private val context = getApplication<Application>().applicationContext
    private val userStateResponseObs = MutableLiveData<Resource<Any>>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val ipv4: String? = baseCallBack?.getPublicIP()
            withContext(Dispatchers.Main) {
                ipv4?.let {
                }
            }
        }
    }

    fun getUserState(userRegisteredNumberWithCode: String) {
        viewModelScope.launch {
            userStateResponseObs.postValue(Resource.loading(null))
            try {
                val apiParams = ApiParams().apply {
                    mobileNumber =
                        userRegisteredNumberWithCode?.replace(
                            "+91 ",
                            ""
                        )
                }

                ApiCall.callAPI(ApiName.GET_USER_STATE, apiParams, object : ApiHelperUI {
                    override fun onSuccess(apiResponse: ApiResponse) {
                        if (apiResponse is GetUserStateResponse) {
                            userStateResponseObs.postValue(Resource.success(apiResponse))
                        }
                    }

                    override fun onError(errorResponse: ErrorResponse) {
                        userStateResponseObs.postValue(
                            Resource.error(
                                errorResponse.errorMessage!!,
                                errorResponse
                            )
                        )
                    }
                })
            } catch (e: Exception) {
                userStateResponseObs.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getUserStateResponseObs(): LiveData<Resource<Any>> {
        return userStateResponseObs
    }

    fun showScreenAsPerUserState(userStatusCode: String) {
        when (userStatusCode) {
            UserStateUI.MOBILE_NOT_SUBMITTED.getValue() -> {
                baseCallBack?.callNextScreen(Intent(context, IntroActivity::class.java), null, true)
            }
            UserStateUI.MOBILE_SUBMITTED.getValue(),
            UserStateUI.PERMISSION_ASSIGNED.getValue() -> {
                baseCallBack?.callNextScreen(
                    Intent(
                        context,
                        MobileVerificationActivity::class.java
                    ), null, true
                )
            }
            UserStateUI.MOBILE_VERIFIED.getValue(),
            UserStateUI.KARZA_APPLICATION_GENERATED.getValue(),
            UserStateUI.PAN_SAVED_LOCAL.getValue(),
            UserStateUI.CARD_DELIVERY_ADDRESS_SAVED_LOCAL.getValue(),
            UserStateUI.SELFIE_SAVED_LOCAL.getValue(),
            UserStateUI.AADHARXML_SAVED_LOCAL.getValue() -> {
                baseCallBack?.callNextScreen(
                    Intent(context, CompleteKYCActivity::class.java),
                    null,
                    true
                )
            }
            UserStateUI.KYC_SCREEN_PASSED.getValue(),
            UserStateUI.SELFIE_SAVED.getValue(),
            UserStateUI.AADHARXML_SAVED.getValue(),
            UserStateUI.LAT_LONG_IP_SAVED.getValue(),
            UserStateUI.KYC_RESULT_SAVED.getValue(),
            UserStateUI.KYC_CHECKS_PASSED.getValue(),
            UserStateUI.ONBOARDING_IN_PROGRESS_1.getValue(),
            UserStateUI.ONBOARDING_IN_PROGRESS_2.getValue(),
            UserStateUI.ONBOARDING_IN_PROGRESS_3.getValue(),
            UserStateUI.ONBOARDING_IN_PROGRESS_4.getValue(),
            UserStateUI.ONBOARDING_IN_PROGRESS.getValue() -> {
                baseCallBack?.callNextScreen(
                    Intent(
                        context,
                        ReviewSubmitDetailActivity::class.java
                    ), null, true
                )
            }
            UserStateUI.KYC_CHECKS_FAILED.getValue(),
            UserStateUI.ONBOARDING_FAILED_1.getValue(),
            UserStateUI.ONBOARDING_FAILED_2.getValue(),
            UserStateUI.ONBOARDING_FAILED.getValue() -> {
                baseCallBack?.callNextScreen(
                    Intent(
                        context,
                        AccountOpeningFailureScreenActivity::class.java
                    ), null, true
                )
            }
            UserStateUI.ONBOARDED.getValue(),
            UserStateUI.ONBOARDING_SUCCESS.getValue() -> {
                baseCallBack?.callNextScreen(
                    Intent(context, WelcomeScreenActivity::class.java),
                    null, true
                )
            }
            UserStateUI.WELCOM_SCREEN_REACHED.getValue() -> {
                baseCallBack?.callNextScreen(
                    Intent(context, SetPasscodeActivity::class.java),
                    null,
                    true
                )
            }
            UserStateUI.PASSCODE_SET.getValue(),
            UserStateUI.LOGIN_DONE.getValue(),
            UserStateUI.LOGGED_OUT.getValue() -> {
                baseCallBack?.callNextScreen(
                    Intent(context, PasscodeActivity::class.java),
                    null,
                    true
                )
            }
        }
    }

//    fun callNextScreen(intent: Intent, bundle: Bundle?) {
//        if (bundle != null)
//            intent.putExtras(bundle)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(intent)
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    internal class SplashViewModelFactory(
        private val baseCallBack: BaseCallback?,
        private val context: Context
    ) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SplashScreenViewModel(baseCallBack, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


class MyAssyn : AsyncTask<String?, String?, String>() {

    override fun doInBackground(vararg params: String?): String {
        var publicIP = ""
        try {
            val s = Scanner(
                URL(
                    "https://api.ipify.org"
                )
                    .openStream(), "UTF-8"
            )
                .useDelimiter("\\A")
            publicIP = s.next()
        } catch (e: IOException) {
        }
        return publicIP
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}