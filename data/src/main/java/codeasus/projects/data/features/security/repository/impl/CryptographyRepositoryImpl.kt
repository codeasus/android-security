package codeasus.projects.data.features.security.repository.impl

import codeasus.projects.data.features.security.dao.CryptographyDAO
import codeasus.projects.data.features.security.entity.EllipticCurveKeyPairEntity
import codeasus.projects.data.features.security.repository.CryptographyRepository
import kotlinx.coroutines.flow.Flow

class CryptographyRepositoryImpl(
    private val cryptographyDAO: CryptographyDAO
) : CryptographyRepository {
    override suspend fun insertEllipticCurveKeyPairs(ellipticCurveKeyPairs: Set<EllipticCurveKeyPairEntity>) {
        cryptographyDAO.insertEllipticCurveKeyPairs(ellipticCurveKeyPairs)
    }

    override suspend fun insertEllipticCurveKeyPair(ellipticCurveKeyPair: EllipticCurveKeyPairEntity) {
        cryptographyDAO.insertEllipticCurveKeyPair(ellipticCurveKeyPair)
    }

    override suspend fun deleteEllipticCurveKeyPairs() {
        cryptographyDAO.deleteEllipticCurveKeyPairs()
    }

    override fun getAllEllipticCurveKeyPairs(): Flow<List<EllipticCurveKeyPairEntity>> {
        return cryptographyDAO.getAllEllipticCurveKeyPairs()
    }

    override suspend fun deleteEllipticCurveKeyPairByID(id: Long) {
        cryptographyDAO.deleteEllipticCurveKeyPairByID(id)
    }
}