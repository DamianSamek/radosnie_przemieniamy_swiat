package com.wsiz.projekt_zespolowy.data.network

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import com.wsiz.projekt_zespolowy.utils.ImageUtils
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class FirebaseStorageService @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val firebaseStorage
        get() = Firebase.storage

    private fun getPostImageRef(userId: Int, uuid: String) =
        firebaseStorage.reference.child("images/user_id_$userId/posts_images/$uuid.jpg")

    @SuppressLint("CheckResult")
    fun uploadPostImage(
        bitmap: Bitmap,
        onSuccess: (url: String, uuid: String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val uuid = uuid()
        val ref = getPostImageRef(sharedPreferences.getUserId(), uuid)

        bitmap.toByteArray().subscribeOn(Schedulers.computation()).subscribe({
            upload(ref, it).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    ref.downloadUrl.addOnSuccessListener { url ->
                        onSuccess(url.toString(), uuid)
                    }.addOnFailureListener { exception -> onError(exception) }
                }
            }
        }, {})
    }

    fun removePostImage(
        imageUUID: String?,
        onSuccess: () -> Unit
    ) {
        if (imageUUID != null) {
            val ref = getPostImageRef(sharedPreferences.getUserId(), imageUUID)
            delete(ref).addOnCompleteListener {
                onSuccess()
            }
        } else {
            onSuccess()
        }
    }

    private fun uuid() = UUID.randomUUID().toString()

    private fun Bitmap.toByteArray(quality: Int = 100): Single<ByteArray> {
        return Single.fromCallable { ImageUtils.toByteArray(this, quality) }
    }

    private fun upload(ref: StorageReference, data: ByteArray): UploadTask {
        return ref.putBytes(data)
    }

    private fun delete(ref: StorageReference): Task<Void> {
        return ref.delete()
    }
}