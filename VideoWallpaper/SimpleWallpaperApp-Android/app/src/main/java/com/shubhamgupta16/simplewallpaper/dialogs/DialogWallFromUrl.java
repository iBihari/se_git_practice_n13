package com.shubhamgupta16.simplewallpaper.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.shubhamgupta16.simplewallpaper.R;
import com.shubhamgupta16.simplewallpaper.activities.WallpaperActivity;
import com.shubhamgupta16.simplewallpaper.models.WallsPOJO;

public class DialogWallFromUrl extends AppCompatDialogFragment{

    private EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_wall_url, null);

        editText = view.findViewById(R.id.editText);
        editText.setText("https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80");
        builder.setView(view)
                .setTitle("Wallpaper from url")
                .setPositiveButton("Done", (dialog, which) -> {
                    String value = editText.getText().toString();
                    Intent i = new Intent(getActivity(), WallpaperActivity.class);
                    i.putExtra("pojo", new WallsPOJO(
                            value, "ImageUrl", value, "ImageUrl", false
                    ));
                    getActivity().startActivity(i);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Đóng dialog khi nhấn nút Hủy
                    dialog.dismiss();
                });

        return builder.create();
    }

}
