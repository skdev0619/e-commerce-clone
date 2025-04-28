package kr.hhplus.be.server.domain.user

interface UserRepository {
    fun save(user : User) : User
    fun findById(id : Long) : User?
}
