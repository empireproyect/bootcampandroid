package app.company.sportpop.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.company.sportpop.core.platform.BaseFragment
import app.company.sportpop.databinding.FragmentHomeBinding
import app.company.sportpop.presentation.main.home.HomeViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoggedIn()
        showToolbar()

        viewModel.event.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(event: Event) {
        when(event) {
            Event.RedirectLogin -> { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment()) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
