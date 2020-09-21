package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.android.synthetic.main.test_fragment.*
import me.sungbin.spakchat.R
import me.sungbin.spakchat.ui.activity.MainActivity
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by SungBin on 2020-09-10.
 */

@AndroidEntryPoint
@WithFragmentBindings
class CallFragment : Fragment() {

    @Inject
    @Named("firestore")
    lateinit var firestore: FirebaseFirestore

    @Inject
    @Named("storage")
    lateinit var storage: StorageReference

    @Inject
    @Named("database")
    lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.test_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = false

        MainActivity.fabAction.apply {
            text = getString(R.string.main_new_call)
            show()
        }
        tv_test.text = "CallFragment"
    }
}