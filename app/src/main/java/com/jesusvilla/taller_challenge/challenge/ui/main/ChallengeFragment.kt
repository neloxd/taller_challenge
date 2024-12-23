package com.jesusvilla.taller_challenge.challenge.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jesus.villa.taller.challenge.databinding.FragmentChallengeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeFragment : Fragment() {

    private lateinit var binding: FragmentChallengeBinding
    private val viewModel: MainChallengeViewModel by viewModels()

    companion object {
        fun newInstance() = ChallengeFragment()
        const val PREFERENCES = "#PREFERENCES"
        const val KEY_USERNAME = "#KEY_USERNAME"
        const val KEY_PASSWORD = "#KEY_PASSWORD"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupText()
        setupObservers();
    }

    private fun setupObservers() {
        viewModel.onData().observe(viewLifecycleOwner) {
            if(!it.hasBeenHandled()) {
                Toast.makeText(requireContext(), "EXITO", Toast.LENGTH_LONG).show()
                val data = it.contentIfNotHandled!!
                saveData(data.firstObject, data.secondObject)
            }
        }
        viewModel.onError().observe(viewLifecycleOwner) {
            if(!it.hasBeenHandled()) {
                Toast.makeText(requireContext(), it.contentIfNotHandled!!, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.onLoading().observe(viewLifecycleOwner) {
            if(!it.hasBeenHandled()) {
                showLoading(it.contentIfNotHandled!!)
            }
        }
    }

    @SuppressLint("CommitPrefEdits", "ApplySharedPref")
    private fun saveData(username: String, password: String) {
        val preferences = requireActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.clear()
        edit.putString(KEY_USERNAME, username)
        edit.putString(KEY_PASSWORD, password)
        edit.commit()
    }

    //region UI
    private fun setupUI() {
        with(binding) {
            tilUserName.editText?.doAfterTextChanged {
                setEnableLogin(getUsername().isNotEmpty() && getPassword().isNotEmpty())
            }
            tilPassword.editText?.doAfterTextChanged {
                setEnableLogin(getUsername().isNotEmpty() && getPassword().isNotEmpty())
            }
            btnLogin.setOnClickListener {
                viewModel.login(getUsername(), getPassword())
            }
        }
    }

    private fun setupText() {
        val preferences = requireActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val userName = preferences.getString(KEY_USERNAME, "")
        val password = preferences.getString(KEY_PASSWORD, "")

        with(binding) {
            tilUserName.editText?.setText(userName)
            tilPassword.editText?.setText(password)
            setEnableLogin(userName!!.isNotEmpty() && password!!.isNotEmpty())
        }
    }

    private fun setEnableLogin(isEnable: Boolean) {
        with(binding) {
            btnLogin.isEnabled = isEnable
        }
    }

    private fun showLoading(show: Boolean) {
        binding.llProgressBar.root.isVisible = show
    }

    private fun getUsername() = binding.tilUserName.editText?.text.toString()
    private fun getPassword() = binding.tilPassword.editText?.text.toString()
    //endregion

}