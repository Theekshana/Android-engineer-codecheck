package jp.co.yumemi.android.code_check.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import jp.co.yumemi.android.code_check.constants.Constants
import jp.co.yumemi.android.code_check.databinding.ErrorDialogFragmentLayoutBinding

/**
 * A DialogFragment used to display an error message.
 */
class ErrorMessageDialogFragment : DialogFragment() {

    // View binding for the layout
    private lateinit var binding: ErrorDialogFragmentLayoutBinding

    companion object {
        /**
         * Creates a new instance of ErrorMessageDialogFragment with the provided error message.
         * @param message The error message to be displayed.
         * @return An instance of ErrorMessageDialogFragment.
         */
        fun newInstance(message: String): ErrorMessageDialogFragment {
            // Create a new instance of ErrorMessageDialogFragment
            val fragment = ErrorMessageDialogFragment()
            // Create arguments bundle and add the error message
            val args = Bundle()
            args.putString(Constants.KEY_MESSAGE, message)
            // Set arguments for the fragment
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ErrorDialogFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the error message from arguments
        val message = requireArguments().getString(Constants.KEY_MESSAGE)

        // Set the error message text and configure button click listener
        binding.apply {
            txtInvalidUserInput.text = message
            btnOk.setOnClickListener { dismiss() }
        }

    }
}