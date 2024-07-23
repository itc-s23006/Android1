package jp.ac.it_college.std.s23006.listviewsample2

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
class OrderConfirmDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val dialog = builder.run {
            setTitle(R.string.dialog_title)
            setMessage(R.string.dialog_msg)

            setPositiveButton(R.string.dialog_btn_ok) { dialog, which ->
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.dialog_ok_toast),
                    Toast.LENGTH_LONG
                ).show()
            }

            setNegativeButton(R.string.dialog_btn_ng) { dialog, which ->
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.dialog_ng_toast),
                    Toast.LENGTH_LONG
                ).show()
            }

            setNeutralButton(R.string.dialog_btn_nu) { dialog, which ->
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.dialog_nu_toast),
                    Toast.LENGTH_LONG
                ).show()
            }
            create()
        }
        return dialog
    }
}

