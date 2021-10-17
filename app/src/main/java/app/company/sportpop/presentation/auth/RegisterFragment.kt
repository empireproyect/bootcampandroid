package app.company.sportpop.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.company.sportpop.R
import app.company.sportpop.core.extensions.hide
import app.company.sportpop.core.extensions.hideKeyboard
import app.company.sportpop.core.extensions.show
import app.company.sportpop.core.platform.BaseFragment
import app.company.sportpop.databinding.FragmentRegisterBinding
import app.company.sportpop.presentation.auth.AuthViewModel.Event
import app.company.sportpop.presentation.auth.AuthViewModel.Event.FieldError
import app.company.sportpop.presentation.auth.AuthViewModel.Event.RedirectHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    private val viewModel by viewModels<AuthViewModel>()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtLogin.setOnClickListener { findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()) }

        viewModel.event.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.failure.observe(viewLifecycleOwner, Observer(::handleFailure))

        binding.btnRegister.setOnClickListener {
            hideKeyboard(requireActivity())
            viewModel.register(
                binding.txtEmail.text.toString().trim(),
                binding.txtName.text.toString().trim(),
                binding.txtPassword.text.toString().trim()
            )
        }

    }

    private fun updateUi(event: Event) {
        if (event is Event.Loading) binding.progress.show() else binding.progress.hide()
        when (event) {
            is FieldError -> showErrorValidation(event)
            RedirectHome -> { findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()) }
        }
    }

    private fun showErrorValidation(event: FieldError) {
        if (!event.isEmailValid) {
            binding.txtEmail.error = getString(R.string.email_not_valid)
        }
        if (!event.isPasswordValid) {
            binding.txtPassword.error = getString(R.string.password_not_valid)
        }

        if (!event.isDisplayName!!) {
            binding.txtName.error = getString(R.string.display_name_not_valid)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
