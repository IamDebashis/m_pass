package com.nide.mpass.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.FragmentSearchBinding
import com.nide.mpass.databinding.StandaloneChipBinding
import com.nide.mpass.ui.home.PasswordAdapter
import com.nide.mpass.util.showSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private val adapter: PasswordAdapter =
        PasswordAdapter { password -> navigateToPasswordDetails(password) }
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inpSearch.showSoftKeyboard()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        observeData()
        initClicks()
        initRV()
    }


    private fun initRV() {
        binding.rvResult.adapter = adapter
    }


    private fun initClicks() {
        binding.cgCategoryFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            viewModel.filterBy(checkedIds)
        }

        binding.inpSearch.doOnTextChanged { text, start, before, count ->
            Log.i(TAG, "initClicks outside: $text ,start:$start, before: $before, count:$count")
            if(text?.isNotBlank() == true) {
                viewModel.search(text.toString())
                Log.i(TAG, "initClicks: $text")
            }
            /*    searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(300)
                }

*/
        }


    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.allCategories.collectLatest { categories ->
                    categories.forEach { category ->
                        val chip = crateChip(category.categoryName, category.id)
                        binding.cgCategoryFilter.addView(chip)
                    }
                }
            }
            launch {
                viewModel.filterPassword.collectLatest {
                    adapter.submitList(it)
                }
            }
        }


    }


    private fun navigateToPasswordDetails(password: Password) {
        val action =
            SearchFragmentDirections.actionNavigationSearchToPasswordDetailsFragment(password.id)
        findNavController().navigate(action)
    }

    private fun crateChip(label: String, _id: Int): Chip {
        val chip = StandaloneChipBinding.inflate(layoutInflater).root
        chip.id = _id
        chip.text = label
        return chip
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}