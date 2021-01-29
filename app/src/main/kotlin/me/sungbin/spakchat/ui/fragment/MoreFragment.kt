package me.sungbin.spakchat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.sungbin.spakchat.databinding.FragmentMoreBinding
import me.sungbin.spakchat.ui.activity.MainActivity

/**
 * Created by SungBin on 2020-09-10.
 */

class MoreFragment : BaseFragment() {

    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMoreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.fabAction.hide()

        binding.ivEdit.setOnClickListener {
        }
    }
}
