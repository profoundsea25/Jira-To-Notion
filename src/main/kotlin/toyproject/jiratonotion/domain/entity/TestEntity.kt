package toyproject.jiratonotion.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class TestEntity {

    @Id @GeneratedValue
    var id: Long? = null

    var message: String? = null

}