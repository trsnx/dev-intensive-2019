package ru.skillbranch.devintensive.viewmodels

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class ProfileViewModel : ViewModel() {
    private val repository: PreferencesRepository = PreferencesRepository
    private val profileDate = MutableLiveData<Profile>()
    private val appTheme = MutableLiveData<Int>()
    private val repositoryValid = MutableLiveData<Boolean>()

    init {
        profileDate.value = repository.getProfile()
        appTheme.value = repository.getAppTheme()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("M_ProfileViewModel", "onCleared")
    }

    fun getProfileData(): LiveData<Profile> = profileDate

    fun getTheme(): LiveData<Int> = appTheme

    fun saveProfileData(profile: Profile) {
        repository.saveProfile(profile)
        profileDate.value = profile
    }

    fun switchTheme() {
        if (appTheme.value == AppCompatDelegate.MODE_NIGHT_YES) {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        } else {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(appTheme.value!!)
    }

    fun getRepositoryValid(): LiveData<Boolean> = repositoryValid

    fun validateRepository(text: String) {
        repositoryValid.value = text.isEmpty() || text.matches(
            Regex("^(https://)?(www.)?(github.com/)(?!(${getRegexExceptions()})(?=/|$))(?![\\W])(?!\\w+[-]{2})[a-zA-Z0-9-]+(?<![-])(/)?$")
        )
    }

    private fun getRegexExceptions(): String {
        val exceptions = arrayOf(
            "enterprise",
            "features",
            "topics",
            "collections",
            "trending",
            "events",
            "join",
            "pricing",
            "nonprofit",
            "customer-stories",
            "security",
            "login",
            "marketplace"
        )
        return exceptions.joinToString("|")
    }
}