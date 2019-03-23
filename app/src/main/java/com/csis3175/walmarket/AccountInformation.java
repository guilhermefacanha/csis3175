package com.csis3175.walmarket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.csis3175.walmarket.database.UserDbHelper;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.SessionUtil;


public class AccountInformation extends Fragment {

    UserDbHelper userDbHelper;
  User user;
  MainActivity mainActivity;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = SessionUtil.getUser(getActivity());
        final EditText etfname= mainActivity.findViewById(R.id.etfName);
        etfname.setText(user.getfName());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_information, container, false);

    }



    public void applychange(View view)
    {

    }

}