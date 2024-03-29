package codeasus.projects.data.features.contact.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codeasus.projects.data.features.app.util.DatabaseConstants
import codeasus.projects.data.features.contact.entity.ContactEntity
import codeasus.projects.data.features.contact.model.ContactCryptoParam
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDAO {
    @Query("SELECT * FROM ${DatabaseConstants.ENTITY_CONTACT} WHERE phone_number=:phoneNumber")
    fun getContactByPhoneNumber(phoneNumber: String): ContactEntity

    @Query("SELECT * FROM ${DatabaseConstants.ENTITY_CONTACT}")
    fun getContactsAsFlow(): Flow<List<ContactEntity>>

    @MapInfo(keyColumn = "phoneNumber")
    @Query("SELECT C.phone_number AS phoneNumber, * FROM ${DatabaseConstants.ENTITY_CONTACT} AS C")
    fun getContactsMappedByPhoneNumber(): Map<String, ContactEntity>

    @Query("SELECT phone_number, public_key FROM ${DatabaseConstants.ENTITY_CONTACT} WHERE phone_number IN (:phoneNumbers) ")
    fun getContactCryptoParams(phoneNumbers: List<String>): List<ContactCryptoParam>

    @Query("SELECT * FROM ${DatabaseConstants.ENTITY_CONTACT}")
    fun getContacts(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contactEntity: ContactEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contactEntities: List<ContactEntity>): List<Long>

    @Query("DELETE FROM ${DatabaseConstants.ENTITY_CONTACT} WHERE phone_number=:phoneNumber")
    suspend fun deleteContactByPhoneNumber(phoneNumber: String)

    @Query("DELETE FROM ${DatabaseConstants.ENTITY_CONTACT}")
    suspend fun deleteContacts()
}