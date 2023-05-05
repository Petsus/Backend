package br.com.tcc.petsus

//import br.com.tcc.petsus.model.animal.race.Race
//import br.com.tcc.petsus.repository.RaceRepository
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.context.junit4.SpringRunner
//
//@RunWith(SpringRunner::class)
//@DataJpaTest
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class RaceRepositoryTest {
//
//    @Autowired
//    lateinit var repository: RaceRepository
//
//    @Autowired
//    lateinit var entityManager: TestEntityManager
//
//    @Test
//    fun hasRaces() {
//        entityManager.persist(Race(0, "Any race"))
//        assert(repository.findAll().isNotEmpty())
//    }
//
//}