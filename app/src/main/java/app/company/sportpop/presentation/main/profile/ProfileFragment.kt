package app.company.sportpop.presentation.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.company.sportpop.core.platform.BaseFragment
import app.company.sportpop.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
