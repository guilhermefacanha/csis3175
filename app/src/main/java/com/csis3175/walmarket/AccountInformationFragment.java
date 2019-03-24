package com.csis3175.walmarket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.csis3175.walmarket.database.UserDbHelper;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.SessionUtil;


public class AccountInformationFragment extends Fragment {

    UserDbHelper userDbHelper;
    User user;
    MainActivity mainActivity;

  EditText txtlName, txtfName,txtAdrress,txtPass,txtEmail;
  Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_information, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

        userDbHelper = new UserDbHelper(mainActivity);

        txtlName = mainActivity.findViewById(R.id.etlName);
        txtfName = mainActivity.findViewById(R.id.etfName);
        txtAdrress = mainActivity.findViewById(R.id.etAddress);
        txtPass = mainActivity.findViewById(R.id.etPassword);
        txtEmail = mainActivity.findViewById(R.id.etEmail);

        User user = userDbHelper.getUserByEmail(SessionUtil.getUser(mainActivity).getEmail());
        txtlName.setText(user.getlName());
        txtfName.setText(user.getfName());


    }


}