package com.android.shareride.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.shareride.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final  String EMPTY_FIELD_TOAST = "Please fill all the fields to register.";
    private static final String PASSWORD_INCONSISTENCY_TOAST = "Confirm password is different, please enter again.";
    private static final String REGISTER_FAIL_TOAST = "Sorry, registration failed. Please try another username.";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText txtUsername;
    private EditText txtFullname;
    private EditText txtDriverLicense;
    private EditText txtContact;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnRegister;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        txtUsername = (EditText) view.findViewById(R.id.usernameEditText);
        txtFullname = (EditText) view.findViewById(R.id.fullnameEditText);
        txtDriverLicense = (EditText) view.findViewById(R.id.idEditText);
        txtContact = (EditText) view.findViewById(R.id.contactEditText);
        txtPassword = (EditText) view.findViewById(R.id.passwordEditText);
        txtConfirmPassword = (EditText) view.findViewById(R.id.confirmPasswordEditText);
        btnRegister = (Button) view.findViewById(R.id.registerButton);
        btnRegister.setOnClickListener(handler);
        return view;
    }

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatabaseHelper helper = new DatabaseHelper(getContext());
            String password = txtPassword.getText().toString();
            String confirm = txtConfirmPassword.getText().toString();

            //Toast.makeText(getContext(), "helper created", Toast.LENGTH_SHORT).show();

            if (txtUsername.getText().toString().equals("") ||
                    txtFullname.getText().toString().equals("") ||
                    txtDriverLicense.getText().toString().equals("") ||
                    txtConfirmPassword.getText().toString().equals("") ||
                    txtPassword.getText().toString().equals("") ||
                    txtConfirmPassword.getText().toString().equals("")) {
                Toast.makeText(getContext(), EMPTY_FIELD_TOAST, Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confirm)) {
                Toast.makeText(getContext(), PASSWORD_INCONSISTENCY_TOAST, Toast.LENGTH_SHORT).show();
            }
            else {
                boolean success = helper.insertUserRegistrationData(txtFullname.getText().toString(), txtUsername.getText().toString(), txtDriverLicense.getText().toString(), txtPassword.getText().toString(), txtContact.getText().toString());
                if (success) {
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    intent.putExtra("Username", txtUsername.getText().toString());
                    intent.putExtra("Fullname", txtFullname.getText().toString());
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(), REGISTER_FAIL_TOAST, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
