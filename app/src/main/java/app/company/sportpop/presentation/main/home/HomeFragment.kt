package app.company.sportpop.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import app.company.sportpop.core.extensions.hide
import app.company.sportpop.core.extensions.show
import app.company.sportpop.core.platform.BaseFragment
import app.company.sportpop.core.platform.ConsumingObserver
import app.company.sportpop.databinding.FragmentHomeBinding
import app.company.sportpop.presentation.main.home.HomeViewModel.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel by activityViewModels<HomeViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.isLoggedIn()) { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment()) }
        configureView()
        showNavigation()
        showToolbar()
        if (savedInstanceState == null && viewModel.listState == null) {
            viewModel.getProducts()
        } else {
            viewModel.reloadLocalData()
        }

        viewModel.event.observe(viewLifecycleOwner, ConsumingObserver(::updateUi))
        viewModel.failure.observe(viewLifecycleOwner, Observer(::handleFailure))
    }

    private fun configureView() {
        adapter = HomeAdapter(viewModel::onProductClicked)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun updateUi(event: Event) {
        if (event is Event.Loading) binding.progress.show() else binding.progress.hide()
        when (event) {
            Event.Detail -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment())
            is Event.Content -> {
                adapter.submitList(event.products)
                onRestoreStateRecycler()
            }
        }
    }

    private fun onRestoreStateRecycler() {
        if (viewModel.listState != null) {
            binding.recyclerView.layoutManager?.onRestoreInstanceState(viewModel.listState)
            viewModel.listState = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.listState = binding.recyclerView.layoutManager?.onSaveInstanceState()
        _binding = null
    }
}
