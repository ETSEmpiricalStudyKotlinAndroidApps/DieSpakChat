package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import me.sungbin.spakchat.database.DataBaseViewModel
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by SungBin on 2020-10-21.
 */

@AndroidEntryPoint
@WithFragmentBindings
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

    val db by viewModels<DataBaseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = false
    }

    fun <T : MutableCollection<T?>> MutableCollection<T?>.toList(): List<T> {
        val list = ArrayList<T>()
        this.forEach {
            it?.let { element ->
                list.add(element)
            }
        }
        return list
    }

}