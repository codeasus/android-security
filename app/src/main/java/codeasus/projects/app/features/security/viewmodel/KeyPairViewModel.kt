package codeasus.projects.app.features.security.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.data.features.security.model.EllipticCurveKeyPair
import codeasus.projects.data.features.security.repository.EllipticCurveKeyPairRepository
import codeasus.projects.data.features.security.service.ECDHKeyManagementService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeyPairViewModel @Inject constructor(
    application: Application,
    private val ellipticCurveKeyPairRepository: EllipticCurveKeyPairRepository,
    private val eCDHKeyManagementService: ECDHKeyManagementService
) : AndroidViewModel(application) {

    private val mECKeyPairs = MutableStateFlow<List<EllipticCurveKeyPair>?>(null)
    val ecKeyPairs = mECKeyPairs.asStateFlow()

    init {
        getEllipticCurveKeyPairs()
    }

    private fun getEllipticCurveKeyPairs() {
        viewModelScope.launch(Dispatchers.IO) {
            ellipticCurveKeyPairRepository.getEllipticCurveKeyPairs().collectLatest {
                mECKeyPairs.emit(it)
            }
        }
    }

    fun generateEllipticCurveKeyPairs(count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            eCDHKeyManagementService.generateEllipticCurveKeyPairs(count)
        }
    }
}