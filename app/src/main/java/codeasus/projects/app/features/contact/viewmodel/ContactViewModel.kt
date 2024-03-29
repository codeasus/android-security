package codeasus.projects.app.features.contact.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import codeasus.projects.data.features.contact.model.Contact
import codeasus.projects.data.features.contact.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    application: Application,
    private val contactRepository: ContactRepository
) : AndroidViewModel(application) {

    val contacts = MutableStateFlow<List<Contact>?>(null)

    fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.getContactsAsFlow().collectLatest {
                contacts.emit(it)
            }
        }
    }

    fun deleteContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.deleteContacts()
        }
    }
}