package kr.co.nottodo

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FirebaseMessagingService() : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        sendNotification(message)
    }

    private fun sendNotification(message: RemoteMessage) {
        // 푸시 알림 기능 구현
    }

    fun getFirebaseToken() {
        // 토큰 얻기
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Timber.d("token=$it")
        }
    }
}