package com.kader.guidomia_challenge.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kader.guidomia_challenge.R
import com.kader.guidomia_challenge.databinding.FragmentHomeBinding
import com.kader.guidomia_challenge.databinding.HeaderBinding

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val includeHeaderBinding: HeaderBinding get() = _includeHeaderBinding!!
    private var _includeHeaderBinding: HeaderBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _includeHeaderBinding = HeaderBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        includeHeaderBinding.apply {
            headerTitle.text = getString(R.string.header_title)
            headerDescription.text = getString(R.string.header_description)
        }
    }
}