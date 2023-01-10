package com.guruprasad.notesuplaoder.ui.Notification;

import static com.guruprasad.notesuplaoder.Constants.TOPIC;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.guruprasad.notesuplaoder.API.API_Utilities;
import com.guruprasad.notesuplaoder.R;
import com.guruprasad.notesuplaoder.databinding.FragmentPithosNotificationBinding;
import com.guruprasad.notesuplaoder.model.Notification_Data;
import com.guruprasad.notesuplaoder.model.Push_Notification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Pithos_Notification extends Fragment {
    private FragmentPithosNotificationBinding  binding ;
    private EditText title , message ;
    private Button send ;
    private TextInputLayout title_layout , message_layout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPithosNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        title = root.findViewById(R.id.notification_title);
        message = root.findViewById(R.id.notification_body);
        send = root.findViewById(R.id.send_notification);
        title_layout = root.findViewById(R.id.notification_title_layout);
        message_layout = root.findViewById(R.id.notification_body_layout);

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String notification_title = title.getText().toString();
                String notification_message  = message.getText().toString();

                if ( !notification_title.isEmpty() && !notification_message.isEmpty())
                {
                    Push_Notification push_notification = new Push_Notification(new Notification_Data(notification_title , notification_message),TOPIC);
                    Send_Notification(push_notification);
                }
                else
                {
                    title.setError("Enter the Title");
                    message.setError("Enter the Message");
                }

            }
        });
        return root ;
    }

    private void Send_Notification(Push_Notification push_notification) {

        API_Utilities.getClient().send_notification(push_notification).enqueue(new Callback<Push_Notification>() {
            @Override
            public void onResponse(Call<Push_Notification> call, Response<Push_Notification> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(getContext(), "Notification Send Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Error : Notification Can't Send ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Push_Notification> call, Throwable t) {
                Toast.makeText(getContext(),"Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}