package codeasus.projects.encryption.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import codeasus.projects.encryption.data.util.DatabaseConstants

@Entity(tableName = DatabaseConstants.ENTITY_ELLIPTIC_CURVE_KEY_PAIR)
data class EllipticCurveKeyPairEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "public_key")
    val publicKey: String,
    @ColumnInfo(name = "private_key")
    val privateKey: String
)