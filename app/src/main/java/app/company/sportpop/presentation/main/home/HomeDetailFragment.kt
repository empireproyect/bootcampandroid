package app.company.sportpop.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import app.company.sportpop.core.extensions.loadFromUrl
import app.company.sportpop.core.platform.BaseFragment
import app.company.sportpop.databinding.FragmentHomeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailFragment : BaseFragment() {

    private val viewModel by activityViewModels<HomeViewModel>()

    private var _binding: FragmentHomeDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideNavigation()
        viewModel.product?.let {
            with(it) {
                binding.ivProduct.loadFromUrl(photoUrl)
                binding.txtDescription.text = description
                binding.txtPrice.text = "$price €"
                binding.txtTitle.text = title
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
