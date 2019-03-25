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
import com.csis3175.walmarket.util.Md5Util;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;


public class AccountInformationFragment extends Fragment {

    UserDbHelper userDbHelper;
    User user;
    MainActivity mainActivity;

  EditText txtlName,txtfName,txtAddress,txtNewPass,txtEmail,txtConfirmPass;
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

        txtfName = mainActivity.findViewById(R.id.etfName);
        txtlName = mainActivity.findViewById(R.id.etlName);
        txtEmail = mainActivity.findViewById(R.id.etEmail);
        txtAddress = mainActivity.findViewById(R.id.etAddress);
        txtNewPass = mainActivity.findViewById(R.id.etNewPassword);
        txtConfirmPass = mainActivity.findViewById(R.id.etConfirmPassword);
        btn = mainActivity.findViewById(R.id.btnApplyChanges);

        User user = userDbHelper.getUserByEmail(SessionUtil.getUser(mainActivity).getEmail());
        txtfName.setText(user.getfName());
        txtlName.setText(user.getlName());
        txtEmail.setText(user.getEmail());
        txtAddress.setText(user.getAddress());




        btn.setOnClickListener(new View.OnClickListener() {
            MainActivity mainActivity = (MainActivity) getActivity();
            User user = userDbHelper.getUserByEmail(SessionUtil.getUser(mainActivity).getEmail());
            @Override
            public void onClick(View v) {


                if(!txtfName.getText().toString().equals(user.getfName())){
                    user.setfName(txtfName.getText().toString());
                    userDbHelper.updateRecord(user);
                    MessageUtil.addMessage("First Name Updated", mainActivity);
                }

                if(!txtlName.getText().toString().equals(user.getlName())){
                    user.setlName(txtlName.getText().toString());
                    userDbHelper.updateRecord(user);
                    MessageUtil.addMessage("Last Name Updated", mainActivity);
                }

                if(!txtEmail.getText().toString().equals(user.getEmail())){
                    user.setEmail(txtEmail.getText().toString());
                    userDbHelper.updateRecord(user);
                    MessageUtil.addMessage("Email Updated", mainActivity);
                }

                if(!txtAddress.getText().toString().equals(user.getAddress())){
                    user.setAddress(txtAddress.getText().toString());
                    userDbHelper.updateRecord(user);
                    MessageUtil.addMessage("Address Updated", mainActivity);
                }

                if(!txtNewPass.getText().toString().isEmpty()){

                    if(txtNewPass.getText().toString().equals(txtConfirmPass.getText().toString())){
                        if(!Md5Util.getMd5(txtNewPass.getText().toString()).equals(user.getPassword())) {
                            user.setPassword(txtNewPass.getText().toString());
                            userDbHelper.updateRecord(user);
                            MessageUtil.addMessage("Password Updated", mainActivity);
                        }
                        else {
                            MessageUtil.addMessage("This is your current password", mainActivity);
                        }

                    }

                    else{
                        MessageUtil.addMessage("Passwords does not match", mainActivity);
                    }
                }
            }
        });



    }


}