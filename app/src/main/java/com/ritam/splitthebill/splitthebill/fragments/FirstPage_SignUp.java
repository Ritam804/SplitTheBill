package com.ritam.splitthebill.splitthebill.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstPage_SignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstPage_SignUp extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout Button_Save;

    EditText Name, Handle, Email;

    private OnFragmentInteractionListener mListener;

    public FirstPage_SignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment FirstPage_SignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstPage_SignUp newInstance() {
        AppData.SignUpPageNumber = 1;
        FirstPage_SignUp fragment = new FirstPage_SignUp();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_first_page__sign_up, container, false);

        TextView selectPageIndicator = (TextView) getActivity().findViewById(R.id.tv_second);
        selectPageIndicator.setBackgroundColor(Color.parseColor("#696969"));

        Name = (EditText) view.findViewById(R.id.et_name);
        Handle = (EditText) view.findViewById(R.id.et_handle);
        Email = (EditText) view.findViewById(R.id.et_email);
        Button_Save = (LinearLayout) view.findViewById(R.id.ll_next);

        if (AppData.SignUpName.length() > 0){

            Name.setText(AppData.SignUpName);

        }

        if (AppData.SignUpUserName.length() > 0){

            Handle.setText(AppData.SignUpUserName);

        }

        if (AppData.SignUpEmail.length() > 0){

            Email.setText(AppData.SignUpEmail);

        }

        Intent intent=getActivity().getIntent();

        if(intent.getExtras().getString("from","").equals("facebook")){
            Name.setText(intent.getExtras().getString("name",""));
            Name.setEnabled(false);

            Email.setText(intent.getExtras().getString("email",""));
            Email.setEnabled(false);

        }

        if(intent.getExtras().getString("from","").equals("twitter")){
            Name.setText(intent.getExtras().getString("tw_name",""));
            Name.setEnabled(false);

        }


        return view;
    }


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Name.getText().toString().trim().length() > 0 && !(Name.getText().toString().isEmpty())) {

                    if (Handle.getText().toString().trim().length() > 0 && !(Handle.getText().toString().isEmpty())) {

                        if (Email.getText().toString().trim().length() > 0 && !(Email.getText().toString().isEmpty())) {

                            if (AppData.isValidEmail(Email.getText().toString())) {


                                AppData.SignUpName = Name.getText().toString();
                                AppData.SignUpUserName = Handle.getText().toString();
                                AppData.SignUpEmail = Email.getText().toString();

                                Bundle bundle = new Bundle();
                                bundle.putString("Name",Name.getText().toString());
                                bundle.putString("Handle",Handle.getText().toString());
                                bundle.putString("Email",Email.getText().toString());

                                SecondPage_SignUp secondPage_signUp = SecondPage_SignUp.newInstance();
                                secondPage_signUp.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                                fragmentTransaction.replace(R.id.fl_signup, secondPage_signUp);
                                fragmentTransaction.commit();

                            } else {

                                Toast.makeText(getActivity(), "Please enter a valid email id", Toast.LENGTH_SHORT).show();

                                Email.requestFocus();

                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(Email, InputMethodManager.SHOW_IMPLICIT);

                            }

                        } else {

                            Toast.makeText(getActivity(), "Please enter your email id", Toast.LENGTH_SHORT).show();

                            Email.requestFocus();

                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(Email, InputMethodManager.SHOW_IMPLICIT);
                        }

                    } else {

                        Toast.makeText(getActivity(), "Please enter your username", Toast.LENGTH_SHORT).show();

                        Handle.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(Handle, InputMethodManager.SHOW_IMPLICIT);
                    }

                } else {

                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();

                    Name.requestFocus();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(Name, InputMethodManager.SHOW_IMPLICIT);

                }


            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
