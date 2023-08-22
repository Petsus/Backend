package br.com.tcc.petsus.infrastructure.notification

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ResourceUtils

@Configuration
class NotificationConfiguration {
    @Bean
    fun firebaseApp(): FirebaseApp {
        val options = FirebaseOptions.builder().setCredentials(
            GoogleCredentials.fromStream(ResourceUtils.getFile("classpath:firebase.json").inputStream())
        ).build()
        return FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseMessaging(firebase: FirebaseApp): FirebaseMessaging {
        return FirebaseMessaging.getInstance(firebase)
    }
}