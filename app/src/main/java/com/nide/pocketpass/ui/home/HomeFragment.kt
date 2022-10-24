package com.nide.pocketpass.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.nide.pocketpass.R
import com.nide.pocketpass.data.truple.PasswordTuple
import com.nide.pocketpass.databinding.FragmentHomeBinding
import com.nide.pocketpass.util.ItemSectionDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class HomeFragment : Fragment(), PasswordAdapter.PasswordAdapterListener {
    private val TAG = javaClass.simpleName

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private val adapter by lazy { PasswordAdapter(requireContext(), this) }

    private lateinit var itemSectionDecoration: ItemSectionDecoration



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*  postponeEnterTransition()
          view.doOnPreDraw { startPostponedEnterTransition() }
  */
        binding.btnAddPassword.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_home_to_newRecordFragment
            )

        }

        binding.btnProfile.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToProfileFragment()
            findNavController().navigate(action)
        }

        initRV()
    }

    private fun initRV() {
        itemSectionDecoration = ItemSectionDecoration(requireContext()) {
            Log.i(TAG, "initRV: ${adapter.currentList.size}")
            adapter.currentList }
        binding.rvPassword.apply {
            adapter = this@HomeFragment.adapter
            addItemDecoration(itemSectionDecoration)
            setHasFixedSize(true)
        }
        observeFlow()
    }


    private fun observeFlow() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.passwords
                .map { list ->
                    list.sortedBy { it.categoryId }
                }
                .collectLatest {
                    binding.llNoPassword.isVisible = it.isEmpty()
                    binding.rvPassword.isVisible = it.isNotEmpty()
                    adapter.submitList(it)
                }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPasswordClick(root: View, password: PasswordTuple) {
        val action =
            HomeFragmentDirections.actionNavigationHomeToPasswordDetailsFragment(password.id)
        findNavController().navigate(action)

    }
}