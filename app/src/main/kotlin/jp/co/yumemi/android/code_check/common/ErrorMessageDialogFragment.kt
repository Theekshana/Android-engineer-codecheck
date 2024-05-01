package jp.co.yumemi.android.code_check.common

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.Constants
import jp.co.yumemi.android.code_check.constants.Constants.ARG_IS_ERROR
import jp.co.yumemi.android.code_check.constants.Constants.DELAY_TIME
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
        fun newInstance(message: String, isError: Boolean): ErrorMessageDialogFragment {
            // Create a new instance of ErrorMessageDialogFragment
            val fragment = ErrorMessageDialogFragment()
            // Create arguments bundle and add the error message
            val args = Bundle()
            args.putString(Constants.ARG_MESSAGE, message)
            args.putBoolean(ARG_IS_ERROR, isError)
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

        setupDialog()
    }

    /**
     * Sets up the dialog based on the provided arguments.
     * If [ARG_IS_ERROR] is true, shows an error message with an error icon.
     * Otherwise, shows a success message with a success icon and dismisses the dialog after 2 seconds.
     */
    private fun setupDialog() {

        // Get the message from arguments
        val message = requireArguments().getString(Constants.ARG_MESSAGE)

        // Determine if it's an error dialog
        val isError = requireArguments().getBoolean(ARG_IS_ERROR)

        // Configure UI based on the type of dialog
        when {
            isError -> {
                // Error dialog setup
                with(binding) {
                    imgSuccessOrError.apply {
                        setImageResource(R.drawable.ic_error)
                        txtInvalidUserInput.text = message
                        btnOk.setOnClickListener { dismiss() }
                    }

                }
            }

            else -> {
                // Success dialog setup
                with(binding) {
                    imgSuccessOrError.apply {
                        setImageResource(R.drawable.ic_success)
                        txtInvalidUserInput.text = message
                        btnOk.visibility = View.GONE
                    }
                    // Dismiss the dialog after 2 seconds
                    Handler(Looper.getMainLooper()).postDelayed({
                        dismiss()
                    }, DELAY_TIME)
                }
            }
        }
    }
}