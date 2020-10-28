package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import me.sungbin.spakchat.database.UserDatabase
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by SungBin on 2020-10-21.
 */

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
    lateinit var storage: StorageReference

    @Inject
    @Named("database")
    lateinit var database: DatabaseReference

    @Inject
    @Named("user-db")
    lateinit var userDb: UserDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = false
    }

}