package br.com.tcc.petsus

import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

//import br.com.tcc.petsus.model.auth.AuthRequest
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.context.junit4.SpringRunner
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import java.net.URI
//
//@RunWith(SpringRunner::class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//class AuthenticationControllerTest {
//
//    @Autowired
//    lateinit var mock: MockMvc
//
//    @Test
//    fun authenticateBadRequest() {
//        val uri = URI.create("/auth/login")
//        val json = AuthRequest().apply {
//            email = "@"
//            password = "@"
//        }.json()
//
//        mock.perform(
//            MockMvcRequestBuilders
//                .post(uri)
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
//    }
//}

class AuthenticationControllerTest {
    @Test
    fun generatePassword() {
        println(BCryptPasswordEncoder().encode("margaret.p4"))
    }
}