package app.company.sportpop.core.platform

import androidx.fragment.app.Fragment
import app.company.sportpop.R
import app.company.sportpop.core.error_handling.Failure
import app.company.sportpop.core.error_handling.Failure.DBError
import app.company.sportpop.core.error_handling.Failure.NetworkError.*
import app.company.sportpop.core.extensions.alert

abstract class BaseFragment : Fragment() {

    open fun handleFailure(failure: Failure?) {
        when (failure) {
            is Fatal -> alertError(R.string.failure_fatal_error)
            is Recoverable -> alertError(R.string.failure_recoverable_error)
            is AuthError -> alertError(failure.resourceId)
            NoConnection -> alertError(R.string.failure_no_connection)
            DBError -> alertError(R.string.info_no_data_available)
        }
    }

    private fun alertError(message: Int) {
        requireActivity().alert(getString(R.string.error), getString(message), R.color.yellow)
    }

    fun showToolbar() = (requireActivity() as MainActivity).showToolbar()

    fun hideToolbar() = (requireActivity() as MainActivity).hideToolbar()


}
