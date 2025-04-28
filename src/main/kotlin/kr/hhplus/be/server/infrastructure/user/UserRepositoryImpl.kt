package kr.hhplus.be.server.infrastructure.user

import kr.hhplus.be.server.domain.user.User
import kr.hhplus.be.server.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val jpaRepository: UserJpaRepository
) : UserRepository {
    override fun save(user: User): User {
        return jpaRepository.save(user)
    }

    override fun findById(id: Long): User? {
        return jpaRepository.findByIdOrNull(id)
    }
}
