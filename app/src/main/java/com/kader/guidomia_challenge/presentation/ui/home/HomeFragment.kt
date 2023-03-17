package com.kader.guidomia_challenge.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kader.guidomia_challenge.R
import com.kader.guidomia_challenge.databinding.CardFilterBinding
import com.kader.guidomia_challenge.databinding.FragmentHomeBinding
import com.kader.guidomia_challenge.databinding.HeaderBinding
import com.kader.guidomia_challenge.presentation.ui.home.cars_list.CarListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mAdapter: CarListAdapter


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val includeHeaderBinding: HeaderBinding get() = _includeHeaderBinding!!
    private var _includeHeaderBinding: HeaderBinding? = null

    private val includeCardFilterBinding: CardFilterBinding get() = _includeCardFilterBinding!!
    private var _includeCardFilterBinding: CardFilterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _includeHeaderBinding = HeaderBinding.bind(binding.root)
        _includeCardFilterBinding = CardFilterBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectList()
        collectEvent()
        populateSpinners()
    }

    private fun initView() {
        includeHeaderBinding.apply {
            headerTitle.text = getString(R.string.header_title)
            headerDescription.text = getString(R.string.header_description)
        }

        binding.recyclerView.apply {
            val mLayoutManager = LinearLayoutManager(context)
            mAdapter = CarListAdapter()
            adapter = mAdapter
            layoutManager = mLayoutManager
            val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            AppCompatResources.getDrawable(context, R.drawable.bg_separator)
                ?.let { decoration.setDrawable(it) }
            addItemDecoration(decoration)
        }
    }

    private fun collectList() {
        lifecycleScope.launchWhenStarted {
            viewModel.cars.collect {
                mAdapter.updateList(it)
            }
        }
    }

    private fun collectEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is HomeViewModel.UIEvent.ShowLoading -> {
                        showLoading(event.show)
                    }
                    is HomeViewModel.UIEvent.ShowToast -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun populateSpinners() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.modelFilterValues.collect { populateModelSpinnerWithEntries(it) }
            }
            launch {
                viewModel.makeFilterValues.collect { populateMakeSpinnerWithEntries(it) }
            }
        }
    }


    private fun populateMakeSpinnerWithEntries(entries: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            entries
        )
        includeCardFilterBinding.cardFilterMakeSpinner.adapter = adapter
    }

    private fun populateModelSpinnerWithEntries(entries: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            entries
        )
        includeCardFilterBinding.cardFilterModelSpinner.adapter = adapter
    }
}